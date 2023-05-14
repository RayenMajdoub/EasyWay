package com.example.easyway.View

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.example.easyway.R
import com.example.easyway.RecycleViews.TicketsAdapter
import com.example.easyway.ResponseUtils.BaseResponse
import com.example.easyway.ResponseUtils.TicketsListRequest
import com.example.easyway.ResponseUtils.VoyagesListResponse
import com.example.easyway.ViewModel.VoyagesViewModel


class Tickets_list : AppCompatActivity() {

    private lateinit var button_all: FrameLayout
    private lateinit var button_bus: FrameLayout
    private lateinit var button_taxi: FrameLayout
    private lateinit var button_train: FrameLayout
    private lateinit var button_bike: FrameLayout
    private val viewModel by viewModels<VoyagesViewModel>()
    private lateinit var loading: LottieAnimationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tickets_list)

        loading = findViewById<LottieAnimationView?>(R.id.LoadingSpinner)
        button_all = findViewById(R.id.button_all)
        button_all.isSelected = true
        button_bus = findViewById(R.id.button_bus)
        button_taxi = findViewById(R.id.button_taxi)
        button_train = findViewById(R.id.button_train)
        button_bike = findViewById(R.id.button_bike)
        val intent: Intent = getIntent()
        val TR = TicketsListRequest(
            intent.getStringExtra("Type")!!,
            intent.getStringExtra("DeparturePoint")!!,
            intent.getStringExtra("ArrivalPoint")!!
        )
        TR.Type = "all"
        viewModel.VoyagesData(TR)
        val RV: RecyclerView = findViewById(R.id.recycler_view_tickets)
        var items: MutableList<VoyagesListResponse> = mutableListOf()
        RV.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)



        viewModel.VoyageListResult.observe(this) {
            when (it) {
                is BaseResponse.Loading -> {
                    showLoading()
                }

                is BaseResponse.Success -> {
                    stopLoading()
                    items = it.data!!
                    val adapter = TicketsAdapter(items)
                    RV.adapter = adapter

                    Log.w("ADAPTER LIST SIZE", adapter.itemCount.toString())
                }

                is BaseResponse.Error -> {
                    processError("hello")
                }
                else -> {
                    stopLoading()
                }
            }
        }
        button_all.setOnClickListener {
            is_selected_manager("all")
            RV.adapter!!.notifyDataSetChanged()
        }
        button_bus.setOnClickListener {
            is_selected_manager("bus")
            RV.adapter!!.notifyDataSetChanged()

        }
        button_taxi.setOnClickListener {
            is_selected_manager("taxi")
            RV.adapter!!.notifyDataSetChanged()

        }
        button_train.setOnClickListener {
            is_selected_manager("train")
            RV.adapter!!.notifyDataSetChanged()

        }
        button_bike.setOnClickListener {
            is_selected_manager("bike")
            RV.adapter!!.notifyDataSetChanged()

        }
        val back_arrow = findViewById<ImageView>(R.id.back_arrow)
        back_arrow.setOnClickListener {
            val intentt = Intent(this, Home::class.java)
            startActivity(intentt)

        }


    }

    fun processError(msg: String?) {
        showToast("No tickets available")
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

    fun is_selected_manager(selector: String) {
        val intent: Intent = getIntent()
        val TR = TicketsListRequest(
            intent.getStringExtra("Type")!!,
            intent.getStringExtra("DeparturePoint")!!,
            intent.getStringExtra("ArrivalPoint")!!
        )
        Log.w("TR", TR.toString())
        when (selector) {
            "all" -> {
                //Animation Des Buttons
                button_all.isSelected = true
                button_bus.isSelected = false
                button_taxi.isSelected = false
                button_train.isSelected = false
                button_bike.isSelected = false
                //Search ICI

                TR.Type = "all"
                viewModel.VoyagesData(TR)
            }

            "bus" -> {
                button_all.isSelected = false
                button_bus.isSelected = true
                button_taxi.isSelected = false
                button_train.isSelected = false
                button_bike.isSelected = false
                TR.Type = "bus"

                viewModel.VoyagesData(TR)
            }
            "taxi" -> {
                button_all.isSelected = false
                button_bus.isSelected = false
                button_taxi.isSelected = true
                button_train.isSelected = false
                button_bike.isSelected = false
                TR.Type = "taxi"
                viewModel.VoyagesData(TR)
            }
            "train" -> {
                button_all.isSelected = false
                button_bus.isSelected = false
                button_taxi.isSelected = false
                button_train.isSelected = true
                button_bike.isSelected = false
                TR.Type = "train"
                viewModel.VoyagesData(TR)
            }
            "bike" -> {
                button_all.isSelected = false
                button_bus.isSelected = false
                button_taxi.isSelected = false
                button_train.isSelected = false
                button_bike.isSelected = true
                TR.Type = "bike"
                viewModel.VoyagesData(TR)
            }
        }
    }
}