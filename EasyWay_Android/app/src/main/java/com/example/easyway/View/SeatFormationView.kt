package com.example.easyway.View

import android.app.AlarmManager
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_MUTABLE
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.MutableLiveData
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.example.easyway.BroadcastReceiver.AlarmBroadcastReceiver
import com.example.easyway.Model.SeatClass
import com.example.easyway.Model.SeatFormation
import com.example.easyway.R
import com.example.easyway.RecycleViews.SeatsAdapter
import com.example.easyway.Repository.SessionManager
import com.example.easyway.ResponseUtils.*
import com.example.easyway.ViewModel.PaymentViewModel
import com.example.easyway.ViewModel.ReservationViewModel
import com.example.easyway.ViewModel.SeatFormationViewModel
import com.stripe.android.PaymentConfiguration
import com.stripe.android.paymentsheet.PaymentSheet
import com.stripe.android.paymentsheet.PaymentSheetResult
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit


class SeatFormationView : AppCompatActivity() {
    private lateinit var loading: LottieAnimationView

    //seat formation view model
    private val viewModel by viewModels<SeatFormationViewModel>()

    private val viewModel2 by viewModels<ReservationViewModel>()
    private lateinit var alarmManager: AlarmManager

    //paymentview model
    private val viewModel1 by viewModels<PaymentViewModel>()
    lateinit var paymentSheet: PaymentSheet
    lateinit var customerConfig: PaymentSheet.CustomerConfiguration
    lateinit var paymentIntentClientSecret: String
    lateinit var ReservationRequest: ReservationRequest
    var LIVE_PAYMENT_STATE: MutableLiveData<Boolean> = MutableLiveData(false)
    var count = 0

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_seat_formation)

        paymentSheet = PaymentSheet(this, ::onPaymentSheetResult)
        val button: Button = findViewById(R.id.button)
        val Cancel: Button = findViewById(R.id.Cancel)
        val vehiculeN: TextView = findViewById(R.id.vehicule_n)
        val dateDep: TextView = findViewById(R.id.date_dep)
        val depart: TextView = findViewById(R.id.depart)
        val arrival: TextView = findViewById(R.id.arrival)
        val VehiculeIc: ImageView = findViewById(R.id.Vehicule_ic)
        loading = findViewById<LottieAnimationView?>(R.id.LoadingSpinner1)
        //test_payment sheet
// Seatss
        alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager

        val intent: Intent = getIntent()
        Log.w("Grid ID", intent.getStringExtra("idGrid")!!)
        viewModel.SeatFormationResultData(intent.getStringExtra("idGrid")!!)
        //set data into the  top card  view

     vehiculeN.text = intent.getStringExtra("Vehicule_name")
    dateDep.text = intent.getStringExtra("Departure_Date")
      depart.text = intent.getStringExtra("DeparturePoint")
arrival.text = intent.getStringExtra("ArrivalPoint")
        when (intent.getStringExtra("Vehicule_type")) {
            "bus" ->
            VehiculeIc.setImageResource(R.drawable.ic_bus)
            "train" ->
             VehiculeIc.setImageResource(R.drawable.ic_train)
            "taxi" ->
            VehiculeIc.setImageResource(R.drawable.ic_taxi)
            "bike" ->
                VehiculeIc.setImageResource(R.drawable.ic_bike)
        }


        var RV: RecyclerView = findViewById(R.id.recyclerView)
        var SF: SeatFormation

//seat formation observer
        viewModel.seatFormationResult.observe(this) {
            when (it) {
                is BaseResponse.Loading -> {
                    showLoading()
                }

                is BaseResponse.Success -> {
                    stopLoading()

                    SF = CreateSeatFormation(
                        it.data!!,
                        intent.getBooleanArrayExtra("availableseats")!!
                    )

                    Log.w("View SF", SF.toString())

                    RV.layoutManager = GridLayoutManager(
                        this,
                        SF.gridFormation!!.FullRows + SF.gridFormation!!.EmptyRows.size
                    )
                    RV.adapter = SeatsAdapter(SF)

                }

                is BaseResponse.Error -> {
                    processError(it.msg)
                }
                else -> {
                    stopLoading()
                }
            }
        }
        //payment observer
        viewModel1.paymentResult.observe(this) {
            when (it) {
                is BaseResponse.Loading -> {
                    showLoading()
                }

                is BaseResponse.Success -> {
                    stopLoading()
                    processPayment(it.data)
                    presentPaymentSheet()
                }

                is BaseResponse.Error -> {
                    processError(it.msg)
                }
                else -> {
                    stopLoading()
                }
            }
        }
        //save reservation and result
        viewModel2.ReservationSaveResult.observe(this) {
            when (it) {
                is BaseResponse.Loading -> {
                }

                is BaseResponse.Success -> {
                    val response = it.data!!
                    val prefs = PreferenceManager.getDefaultSharedPreferences(this)
                    val notif = prefs.getBoolean("notifications_header", true)
                    if (notif) {
                        createAnAlarm(
                            intent.getStringExtra("ArrivalPoint")!!,
                            intent.getStringExtra("DepartureDate")!!
                        )
                    }
                    val intentt = Intent(this, Reservation_Ticket_View::class.java)
                    intentt.putExtra("reservation_id", response.Reservation_id)
                    startActivity(intentt)

                }

                is BaseResponse.Error -> {
                    processError(it.msg)
                }
                else -> {
                }
            }
        }
        LIVE_PAYMENT_STATE.observe(this) {
            when (it) {
                true -> {
                    val adapter = RV.adapter as SeatsAdapter
                    val prix_tot = calcul_prix_total(adapter)
                    val s = SessionManager.getUser_Id(this)
                    ReservationRequest = ReservationRequest(
                        s!!,
                        intent.getStringExtra("idVoyage")!!,
                        prix_tot,
                        adapter.reserved
                    )
                    Log.w("IM IN PAYMENT state", ReservationRequest.toString())
                    viewModel2.ReservationSave(ReservationRequest)
                }
                false -> {

                }
            }
        }
        //booknow button
        button.setOnClickListener {
            val adapter = RV.adapter as SeatsAdapter
            val prix_tot = calcul_prix_total(adapter)
            val paymentRequest: PaymentRequest = PaymentRequest(prix_tot)
            viewModel1.getPaymentVM(paymentRequest)
        }

        Cancel.setOnClickListener {
            val intentt = Intent(this, Tickets_list::class.java)
            startActivity(intentt)
        }
    }

    //instantiation d'un seat formation
    fun CreateSeatFormation(SF: SeatFormationResponse, SeatsState: BooleanArray): SeatFormation {
        var nb = 0
        Log.w("Inside Create", SF.toString())
        val emptyrows: MutableList<Int> = SF.EmptyRows
        val emptylines: MutableList<Int> = SF.EmptyLines
        Log.w("Inside Create", emptylines.toString())
        val girdformation: SeatFormation.GridFormation =
            SeatFormation.GridFormation(
                SF.id,
                SF.fullrows,
                SF.FullLines,
                emptyrows,
                emptylines
            )
        val totalseatformation: Map<SeatClass, Int> =
            mapOf(
                SeatClass.ECONOMY_CLASS to SF.EconomyClass,
                SeatClass.FIRST_CLASS to SF.FirstClass,
                SeatClass.BUSINESS_CLASS to SF.BusinessClasss
            )
        val singleseats: MutableList<SeatFormation.SingleSeat> = mutableListOf()
        for ((c, n) in totalseatformation) {

            for (i in 1..n) {
                nb++
                val singleseat: SeatFormation.SingleSeat =
                    SeatFormation.SingleSeat(nb, c, SeatsState[nb])
                singleseats.add(singleseat)
                Log.w("STEAT", "" + singleseat)
            }

        }
        val SFf: SeatFormation =
            SeatFormation(0, totalseatformation, gridFormation = girdformation, singleseats)
        Log.w("SeatFormationComplete", "" + SF)

        return SFf
    }

    fun processError(msg: String?) {
        showToast("Something went wrong")
    }

    fun showToast(msg: String) {
        Log.w("Loading the Seats  ", msg)
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    fun showLoading() {
        loading.isVisible = true
    }

    fun stopLoading() {
        loading.isVisible = false
    }

    fun onPaymentSheetResult(paymentSheetResult: PaymentSheetResult) {
        when (paymentSheetResult) {
            is PaymentSheetResult.Canceled -> {
                print("Canceled")
            }
            is PaymentSheetResult.Failed -> {
                print("Error: ${paymentSheetResult.error}")
            }
            is PaymentSheetResult.Completed -> {
                LIVE_PAYMENT_STATE.value = true

                print("Completed")
                showToastt("Payment Completed")
            }
        }
    }

    fun presentPaymentSheet() {
        paymentSheet.presentWithPaymentIntent(
            paymentIntentClientSecret,
            PaymentSheet.Configuration(
                merchantDisplayName = "My merchant name",
                customer = customerConfig,
                // Set `allowsDelayedPaymentMethods` to true if your business
                // can handle payment methods that complete payment after a delay, like SEPA Debit and Sofort.
                allowsDelayedPaymentMethods = true
            )
        )
    }

    fun processPayment(data: PaymentResponse?) {
        showToastt("Payment method loaded")
        if (data != null) {

            this.paymentIntentClientSecret = data.paymentIntentClientSecret
            this.customerConfig = PaymentSheet.CustomerConfiguration(
                data.customer,
                data.ephemeralKey
            )
            val publishableKey = data.publishableKey
            PaymentConfiguration.init(this, publishableKey)
        }

    }

    fun showToastt(msg: String) {
        Log.w("Payment Result ", msg)
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    fun calcul_prix_total(adapter: SeatsAdapter): Number {
        var prixtot = 0f
        val reserved: MutableList<Int> = adapter.reserved
        val intent: Intent = getIntent()
        val Fprice = intent.getFloatExtra("FirstClass_Price", 0f)
        val Bprice = intent.getFloatExtra("BuisClass_Price", 0f)
        val Eprice = intent.getFloatExtra("EcoClass_Price", 0f)
        for (i in reserved) {
            for (j in adapter.item.SingleSeats) {
                if (i == j.number) {

                    when {
                        j.Class == SeatClass.FIRST_CLASS -> {
                            prixtot += Fprice
                            Log.w("Im in First ", "Fprice:" + Fprice + "PrixTot" + prixtot)
                        }

                        j.Class == SeatClass.BUSINESS_CLASS -> {
                            prixtot += Bprice
                            Log.w("Im in Buisness ", "Bprice:" + Bprice + "PrixTot" + prixtot)
                        }
                        j.Class == SeatClass.ECONOMY_CLASS -> {
                            prixtot += Eprice
                            Log.w("Im in ECONOMY ", "Eprice:" + Eprice + "PrixTot" + prixtot)
                        }
                    }

                }

            }

        }
        return prixtot
    }

    @RequiresApi(Build.VERSION_CODES.S)
    fun createAnAlarm(destination: String, date: String) {

        val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
        val date: Date = sdf.parse(date)
        val day_format = SimpleDateFormat("dd")
        val month_format = SimpleDateFormat("MM")
        val year_format = SimpleDateFormat("yy")
        val hour_format = SimpleDateFormat("HH")
        val min_format = SimpleDateFormat("mm")
        val date_format = SimpleDateFormat("yy-MM-dd")
        val dd = day_format.format(date)
        val month = month_format.format(date)
        val year = year_format.format(date)
        val hour = hour_format.format(date)
        val min = min_format.format(date)
        val cur_cal: Calendar = GregorianCalendar()
        cur_cal.timeInMillis = System.currentTimeMillis()
        val cal: Calendar = GregorianCalendar()
        cal.set(Calendar.HOUR_OF_DAY, hour.toInt())
        cal.set(Calendar.MINUTE, min.toInt())
        cal.set(Calendar.SECOND, 0)
        cal.set(Calendar.MILLISECOND, 0)
        cal.set(Calendar.DATE, dd.toInt())
        Log.w("calendar", cal.toString())
        val alarmIntent = Intent(this, AlarmBroadcastReceiver::class.java)
        alarmIntent.putExtra("TITLE", "Reminder")
        alarmIntent.putExtra("DESC", "It's almost time for your trip to $destination")
        alarmIntent.putExtra("DATE", date_format.format(date))
        alarmIntent.putExtra("TIME", "$hour:$min")
        val pendingIntent = PendingIntent.getBroadcast(
            this,
            count,
            alarmIntent,
            FLAG_MUTABLE
        )

        val prefs = PreferenceManager.getDefaultSharedPreferences(this)
        val reminder = prefs.getString("reminders_header", "")
        var reminders_milis: Long = 1
        when (reminder) {
            "1" -> reminders_milis = 1
            "5" -> reminders_milis = 5
            "15" -> reminders_milis = 15
            "30" -> reminders_milis = 30
            "60" -> reminders_milis = 60
            "180" -> reminders_milis = 180
            "360" -> reminders_milis = 360
        }
        Log.w("reminder_exact", "" + cal.timeInMillis)
        Log.w(
            "reminder_with settings",
            "" + (cal.timeInMillis - (TimeUnit.MINUTES.toMillis(reminders_milis)))
        )
        alarmManager.setExact(
            AlarmManager.RTC_WAKEUP,
            cal.timeInMillis - (TimeUnit.MINUTES.toMillis(reminders_milis)),
            pendingIntent
        )

    }
}
