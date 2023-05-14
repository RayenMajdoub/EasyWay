package com.example.easyway.RecycleViews

import android.view.View
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.easyway.Model.SeatFormation
import com.example.easyway.R

class SeatsViewHolder(itemView: View ):RecyclerView.ViewHolder(itemView) {

    val textView:TextView = itemView.findViewById(R.id.SeatNumber)
    val button: CardView = itemView.findViewById(R.id.Im_button)


}