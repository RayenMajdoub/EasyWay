package com.example.easyway.View

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.core.view.isVisible
import com.airbnb.lottie.LottieAnimationView
import com.example.easyway.R
import com.example.easyway.ResponseUtils.BaseResponse
import com.example.easyway.ViewModel.ReservationViewModel
import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.qrcode.QRCodeWriter
import java.text.SimpleDateFormat
import java.util.*

class Reservation_Ticket_View : AppCompatActivity() {
    private lateinit var loading: LottieAnimationView
    private lateinit var depart_time:TextView
    private lateinit var  arrival_time:TextView
    private lateinit var start_point:TextView
    private lateinit var end_point:TextView
    private lateinit var Depart_Date:TextView
    private lateinit var seat_numbers: TextView
    private lateinit var vehicule_name: TextView
    private lateinit var vehicule_type: TextView
    private lateinit var total_price: TextView
    private lateinit var qr_viewer:ImageView
    private lateinit var homebtn: FrameLayout
    //seat formation view model
    private val viewModel by viewModels<ReservationViewModel>()
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reservation_ticket_view)

        val reservation_id= intent.getStringExtra("reservation_id")!!
        Log.i("reserved_seats",reservation_id)
        loading = findViewById<LottieAnimationView?>(R.id.LoadingSpinner4)
        depart_time = findViewById(R.id.depart_time)
        arrival_time = findViewById(R.id.arrival_time)
        start_point = findViewById(R.id.start_point)
        end_point = findViewById(R.id.end_point)
        Depart_Date = findViewById(R.id.Depart_Date)
        seat_numbers = findViewById(R.id.seat_numbers)
        vehicule_name = findViewById(R.id.vehicule_name)
        vehicule_type = findViewById(R.id.vehicule_type)
        total_price = findViewById(R.id.total_price)
        qr_viewer = findViewById(R.id.qr_viewer)
        homebtn = findViewById(R.id.home_btn)
        viewModel.getReservation(reservation_id)

        viewModel.reservation_ticket_result.observe(this) {
            when (it) {
                is BaseResponse.Loading -> {
                    showLoading()

                }

                is BaseResponse.Success -> {
                    stopLoading()
                    val date_pattern = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'" )
                    val DepartureDateformated: Date = date_pattern.parse(it.data!!.voyage.DepartureDate)
                    val ArrivalDateformated: Date = date_pattern.parse(it.data.voyage.ArrivalDate)
                    val timepattern= SimpleDateFormat("HH:mm" )
                     val datepatternn=SimpleDateFormat("yy-MM-dd" )
                    depart_time.text=timepattern.format(DepartureDateformated)
                    arrival_time.text=timepattern.format(ArrivalDateformated)
                    start_point.text=it.data.voyage.DeparturePoint
                    end_point.text=it.data.voyage.ArrivalPoint
                    Depart_Date.text=datepatternn.format(DepartureDateformated)
                    seat_numbers.text=it.data.seatnumbers.toString()
                    vehicule_name.text=it.data.voyage.vehicule.name
                    vehicule_type.text=it.data.voyage.vehicule.type
                    qr_viewer.setImageBitmap(getQrCodeBitmap(it.data.qr))
                    total_price.text=it.data.totaleprice.toString()+"$"

                }

                is BaseResponse.Error -> {
                    processError(it.msg)
                }
                else -> {
                    stopLoading()
                }
            }
        }
        homebtn.setOnClickListener{
            val intent = Intent(this, Home::class.java)
            startActivity(intent)
        }


    }
    fun processError(msg: String?) {
        showToast("Something went wrong" )
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
    private fun getQrCodeBitmap(data: String): Bitmap {

        val size = 150 //pixels
        val qrCodeContent =data
        val hints = hashMapOf<EncodeHintType, Int>().also {
            it[EncodeHintType.MARGIN] = 1
        } // Make the QR code buffer border narrower
        val bits = QRCodeWriter().encode( qrCodeContent,
            BarcodeFormat.QR_CODE,
            size,
            size,
            hints
        )
        return Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888).also {
            for (x in 0 until size) {
                for (y in 0 until size) {
                    it.setPixel(x, y, if (bits[x, y]) Color.BLACK else Color.WHITE)
                }
            }
        }

    }
}