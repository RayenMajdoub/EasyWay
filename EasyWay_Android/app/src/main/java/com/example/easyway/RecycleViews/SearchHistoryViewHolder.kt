package com.example.easyway.RecycleViews

import android.view.View
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.easyway.R
import com.google.android.material.card.MaterialCardView

class SearchHistoryViewHolder (itemView: View): RecyclerView.ViewHolder(itemView) {
    val CardView: MaterialCardView =  itemView.findViewById(R.id.card_history_search)
    val place: TextView =  itemView.findViewById(R.id.place)
    val adress: TextView =  itemView.findViewById(R.id.adress)
}