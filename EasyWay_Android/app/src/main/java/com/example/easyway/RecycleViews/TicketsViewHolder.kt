package com.example.easyway.RecycleViews

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.easyway.R

class TicketsViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    val depart_time:TextView = itemView.findViewById(R.id.depart_time)
    val arrival_time:TextView = itemView.findViewById(R.id.arrival_time)
    val depart_point: TextView = itemView.findViewById(R.id.depart_point)
    val arrival_point:TextView = itemView.findViewById(R.id.arrival_point)
    val distance:TextView = itemView.findViewById(R.id.distance)

}