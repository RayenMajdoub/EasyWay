package com.example.easyway.View

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.easyway.R
import com.example.easyway.RecycleViews.MyTicketsAdapter
import com.example.easyway.RecycleViews.TicketsAdapter
import com.example.easyway.Repository.SessionManager
import com.example.easyway.ResponseUtils.BaseResponse
import com.example.easyway.ResponseUtils.ReservationResponseGet
import com.example.easyway.ResponseUtils.VoyagesListResponse
import com.example.easyway.ViewModel.InternalDbViewModel
import com.example.easyway.ViewModel.ReservationViewModel
import com.example.easyway.databinding.ActivityMyTicketsBinding
import com.example.easyway.databinding.ActivityTurnByTurnExperienceBinding


class MyTickets : AppCompatActivity() {
    private lateinit var binding:ActivityMyTicketsBinding
    private val viewModel by viewModels<ReservationViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyTicketsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        var items:MutableList<ReservationResponseGet> = mutableListOf()
        binding.ticketsRv.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL ,false)
        viewModel.MyReservations( SessionManager.getUser_Id(this)!!)
        viewModel.myReservationsResult.observe(this){
                when (it) {
                    is BaseResponse.Loading -> {
                    //    showLoading()
                    }

                    is BaseResponse.Success -> {
                    //    stopLoading()
                        items=it.data!!
                        val adapter: MyTicketsAdapter = MyTicketsAdapter(items)
                        adapter.notifyDataSetChanged()
                        binding.ticketsRv.adapter = adapter

                        Log.w("ADAPTER LIST SIZE", adapter.itemCount.toString())
                    }

                    is BaseResponse.Error -> {
             //  processError("hello")
                    }
                    else -> {
                       // stopLoading()
                    }
                }
            }
        val back_arrow = findViewById<ImageView>(R.id.back_button)
        back_arrow.setOnClickListener {
            val intentt = Intent(this, Home::class.java)
            startActivity(intentt)

        }
        }

    }
