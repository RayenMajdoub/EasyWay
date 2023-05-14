package com.example.easyway.RecycleViews

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.easyway.Model.SearchHistory
import com.example.easyway.R
import com.example.easyway.View.fragment_searched_options


class SearchHistoryAdapter(val items: MutableList<SearchHistory>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return SearchHistoryViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.search_history_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder as SearchHistoryViewHolder
        holder.adress.setText(items[position].address)
        holder.place.setText(items[position].place)
        val bundle = Bundle()
        bundle.putString("adress",items[position].address)
        bundle.putString("place", items[position].place)
        bundle.putInt("id",items[position].id)
        bundle.putDouble("lat", items[position].lat)
        bundle.putDouble("lng", items[position].lng)
        val fragmentSearchedOptions= fragment_searched_options.newInstance(bundle)
        val activity = holder.itemView.rootView.context as FragmentActivity
        holder.itemView.setOnClickListener {
            activity.supportFragmentManager.beginTransaction().apply {
                replace(R.id.fragment_container, fragmentSearchedOptions).addToBackStack(null)
                commit()
            }

        }
    }

    override fun getItemCount() = items.size
}