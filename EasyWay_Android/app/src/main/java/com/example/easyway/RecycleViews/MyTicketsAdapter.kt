package com.example.easyway.RecycleViews

import android.R.attr.radius
import android.annotation.SuppressLint
import android.app.Application
import android.graphics.*
import android.graphics.drawable.Drawable
import android.os.AsyncTask
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.recyclerview.widget.RecyclerView
import com.example.easyway.R
import com.example.easyway.ResponseUtils.ReservationResponseGet
import com.example.easyway.databinding.DialogTicketBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.qrcode.QRCodeWriter

import java.text.SimpleDateFormat
import java.util.*


class MyTicketsAdapter(private val items: MutableList<ReservationResponseGet>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MyTicketsViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.my_tickets_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder as MyTicketsViewHolder
        val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
        val date: Date = sdf.parse(items[position].voyage.DepartureDate)
        val date1: Date = sdf.parse(items[position].voyage.ArrivalDate)
        val g = SimpleDateFormat("HH:mm")
        val d = g.format(date)
        val a = g.format(date1)
        println("date" + d)



        Log.w("Date", "" + date)
        holder.depart_point.setText(items[position].voyage.DeparturePoint)
        holder.arrival_point.setText(items[position].voyage.ArrivalPoint)
        holder.depart_time.setText(d)
        holder.arrival_time.setText(a)
        holder.full_date.setText(date.toString())
        holder.seats.setText(items[position].seatnumbers.toString())
        holder.tot_price.setText(items[position].totaleprice.toString())



        holder.itemView.setOnClickListener {
            Log.w("QR STRING", items[position].qr)

            val binding: DialogTicketBinding
            val view: View = View.inflate(holder.itemView.context, R.layout.dialog_ticket, null)
            binding = DialogTicketBinding.bind(view)
            val bitmap = getQrCodeBitmap(items[position].qr)
            binding.QR.setImageBitmap(bitmap)
            MaterialAlertDialogBuilder(holder.itemView.context)
                .setTitle("My ticket")
                .setNegativeButton("Cancel") { dialog, which ->
                    dialog.dismiss()
                }
                .setView(binding.root)
                .show()

        }
    }


    override fun getItemCount(): Int {
        return items.size
    }


    private fun getQrCodeBitmap(data: String): Bitmap {

        val size = 150 //pixels
        val qrCodeContent =data
        val hints = hashMapOf<EncodeHintType, Int>().also {
            it[EncodeHintType.MARGIN] = 1
        } // Make the QR code buffer border narrower
        val bits = QRCodeWriter().encode( qrCodeContent,
            BarcodeFormat.QR_CODE,
            size,
            size,
            hints
        )
        return Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888).also {
            for (x in 0 until size) {
                for (y in 0 until size) {
                    it.setPixel(x, y, if (bits[x, y]) Color.BLACK else Color.WHITE)
                }
            }
        }

    }
}