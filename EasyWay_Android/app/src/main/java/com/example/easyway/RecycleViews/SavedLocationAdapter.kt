package com.example.easyway.RecycleViews

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.example.easyway.Model.Location
import com.example.easyway.Model.SearchHistory
import com.example.easyway.R
import com.example.easyway.Utils.AppDataBase
import com.example.easyway.Utils.DataBaseHelper
import com.example.easyway.Utils.DataBaseHelperImpl
import com.example.easyway.View.fragment_searched_options
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.*
import okhttp3.internal.wait

class SavedLocationAdapter(val items: MutableList<Location>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return SavedLocationViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.saved_location_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder as SavedLocationViewHolder
         val DataBaseHelper: DataBaseHelper =
            DataBaseHelperImpl(AppDataBase.getDatabase(holder.itemView.rootView.context))
        holder.place.setText(items[position].place)

        when (items[position].tag) {
            "HOME" -> {
                holder.ic_rightt.setImageResource(R.drawable.ic_home)

            }
            "WORK" -> {
                holder.ic_rightt.setImageResource(R.drawable.ic_work)
            }
            else -> {
                holder.ic_rightt.setImageResource(R.drawable.ic_favorite)
            }
        }



        val bundle = Bundle()
        bundle.putString("adress",items[position].address)
        bundle.putString("place", items[position].place)
        bundle.putInt("id",items[position].id)
        bundle.putDouble("lat", items[position].lat)
        bundle.putDouble("lng", items[position].lng)
        val fragmentSearchedOptions= fragment_searched_options.newInstance(bundle)
        val activity = holder.itemView.rootView.context as FragmentActivity
        holder.itemView.setOnClickListener {
            MaterialAlertDialogBuilder(holder.itemView.context)
                .setTitle(items[position].place)
                .setMessage("Do you want to delete "+items[position].place)
                .setNegativeButton("Cancel") { dialog, which ->
                    dialog.dismiss()
                }
                .setPositiveButton("Delete") {
                        dialog, which ->

                    runBlocking {
                        val job = launch {  DataBaseHelper.deleteFavoriteLocation(items[position]) }
                        job.join()
                        print("done")
                        if(job.isCompleted)
                            notifyItemRemoved(position)
                    }
                }
                .show()
            }

        }


    override fun getItemCount()= items.size
}