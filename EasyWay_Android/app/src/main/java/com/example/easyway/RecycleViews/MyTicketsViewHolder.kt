package com.example.easyway.RecycleViews

import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.easyway.R

class MyTicketsViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    val depart_time: TextView = itemView.findViewById(R.id.depart_time)
    val arrival_time: TextView = itemView.findViewById(R.id.arrival_time)
    val depart_point: TextView = itemView.findViewById(R.id.depart_point)
    val arrival_point: TextView = itemView.findViewById(R.id.arrival_point)
    val full_date:TextView = itemView.findViewById(R.id.full_date)
    val seats:TextView = itemView.findViewById(R.id.seats)
    val tot_price:TextView = itemView.findViewById(R.id.tot_price)



}