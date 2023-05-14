package com.example.easyway.RecycleViews

import android.view.View
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.easyway.R

class EmptySeatViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    val CardView:CardView =  itemView.findViewById(R.id.card)


}