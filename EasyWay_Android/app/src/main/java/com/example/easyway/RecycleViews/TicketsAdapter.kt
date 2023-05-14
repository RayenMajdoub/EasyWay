package com.example.easyway.RecycleViews

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.easyway.R
import com.example.easyway.ResponseUtils.VoyagesListResponse
import com.example.easyway.View.SeatFormationView
import java.text.ParseException
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.util.*

class TicketsAdapter(private val items: MutableList<VoyagesListResponse>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return TicketsViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.ticket_card, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder as TicketsViewHolder
        val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'" )
        val date: Date = sdf.parse(items[position].DepartureDate)
        val date1: Date = sdf.parse(items[position].ArrivalDate)
        val g= SimpleDateFormat("HH:mm" )
        val normaldateformat= SimpleDateFormat("yyyy-MM-dd" )
        val d = g.format(date)
        val a = g.format(date1)
        val depdate = normaldateformat.format(date)
        println("date"+d)



        Log.w("Date",""+date)
        holder.distance.text = items[position].Distance.toString()+"km"
        holder.depart_point.text = items[position].DeparturePoint
        holder.arrival_point.text = items[position].ArrivalPoint
        holder.depart_time.text = d
        holder.arrival_time.text = a


        holder.itemView.setOnClickListener{
            val intent = Intent(holder.itemView.context, SeatFormationView::class.java)
            intent.putExtra("idGrid",items[position].vehicule.gridformationid)
            intent.putExtra("availableseats",items[position].available.toBooleanArray())
            intent.putExtra("FirstClass_Price",items[position].FirstPrice)
            intent.putExtra("EcoClass_Price",items[position].EcoPrice)
            intent.putExtra("BuisClass_Price",items[position].BuisPrice)
            intent.putExtra("idVehicule",items[position].vehicule.idVehicule)
            intent.putExtra("idVoyage",items[position].idVoyage)
            intent.putExtra("ArrivalPoint",items[position].ArrivalPoint)
            intent.putExtra("DeparturePoint",items[position].DeparturePoint)
            intent.putExtra("DepartureDate",items[position].DepartureDate)
            intent.putExtra("Departure_Date",depdate)
            intent.putExtra("Vehicule_name",items[position].vehicule.name)
            intent.putExtra("Vehicule_type",items[position].vehicule.type)


            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }
}