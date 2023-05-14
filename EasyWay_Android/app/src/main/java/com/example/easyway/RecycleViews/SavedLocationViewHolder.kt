package com.example.easyway.RecycleViews

import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.easyway.R

class SavedLocationViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    val CardView: FrameLayout =  itemView.findViewById(R.id.card_saved)
    val place: TextView =  itemView.findViewById(R.id.place)
val ic_rightt: ImageView = itemView.findViewById(R.id.ic_rightt)
}