package com.example.easyway.RecycleViews
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View.NOT_FOCUSABLE
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.easyway.Model.SeatFormation
import com.example.easyway.R


const val ITEM_TYPE_NORMAL: Int = 1
const val ITEM_TYPE_EMPTY: Int = 0
class SeatsAdapter( val item: SeatFormation) : RecyclerView.Adapter<ViewHolder>(){
    private val matriceViewType = Create_Matrice_View_Item_Type(item)
    var reserved:MutableList<Int> = mutableListOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        when (viewType) {
            ITEM_TYPE_NORMAL -> return SeatsViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.seat_button_card, parent, false)
            )

            else ->
                return EmptySeatViewHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.empty_button_card, parent, false)
                )
        }
    }

    @RequiresApi(Build.VERSION_CODES.S)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

      when (getItemViewType(position)) {
            ITEM_TYPE_NORMAL -> {
                //  val SeatsViewHolder:SeatsViewHolder= holder as SeatsViewHolder
                holder as SeatsViewHolder

                holder.textView.text = item.SingleSeats[position].number.toString()
                if (item.SingleSeats[position].Reserved)
                {

                    holder.itemView.isEnabled= false
//                    holder.itemView.setAllowClickWhenDisabled(false)
                    holder.button.setCardBackgroundColor(ContextCompat.getColor(holder.button.context,R.color.colorPrimary))

                }else{
                    holder.button.setCardBackgroundColor(ContextCompat.getColor(holder.button.context,R.color.colorSecondary))
                }

                holder.itemView.setOnClickListener{
                    holder.button.setCardBackgroundColor(ContextCompat.getColor(holder.button.context,R.color.AccentPinkDark))
                    reserved.add(position)
                    Log.i("Reserved Seat tab",reserved.toString())
                    holder.itemView.isClickable=false
                }
            }
            ITEM_TYPE_EMPTY -> {
                holder as EmptySeatViewHolder
            }
        }
    }

    override fun getItemCount(): Int {
        return item.SingleSeats.size
    }


    private fun Create_Matrice_View_Item_Type(SeatFormation: SeatFormation): MutableList<Int> {
        //ALL COLLUMS AND ALL ROWS
        val cols: Int =
            SeatFormation.gridFormation!!.FullRows + SeatFormation.gridFormation!!.EmptyRows.size
        val rows: Int =
            SeatFormation.gridFormation!!.FullLines + SeatFormation.gridFormation!!.EmptyLines.size
        //CREATION D UNE MATRICE SELON LES BESOIN
        val MatriceViewType: MutableList<MutableList<Int>> = mutableListOf(mutableListOf())
        for (i in 0 until rows) {
            val bools: MutableList<Int> = mutableListOf();
            Log.w("IminEmpty", ITEM_TYPE_EMPTY.toString())
            for (j in 0 until cols) {

                val result =
                    SeatFormation.gridFormation!!.EmptyLines.find { result: Int -> result == i }
                val result1 =
                    SeatFormation.gridFormation!!.EmptyRows.find { result1: Int -> result1 == j }
                Log.w("I", "" + result)
                Log.w("J", "" + result1)
                if (result != null || result1 != null) {
                    bools.add(ITEM_TYPE_EMPTY)
                    Log.w("Im in Empty", "Im in Empty")
                } else {
                    Log.w("Im in Normal", "Im in Normal")
                    bools.add(ITEM_TYPE_NORMAL)
                }
            }
            MatriceViewType.add(bools)
        }

        Log.w("MatriceResult", MatriceViewType.toString())
        MatriceViewType.removeAt(0)
        Log.w("MatriceResult", MatriceViewType.toString())
        val LastResult: MutableList<Int> = mutableListOf();
        for (i in 0 until rows) {
            LastResult.addAll(MatriceViewType[i])
        }
        Log.w("MatriceResulttttt", LastResult.toString())
        return LastResult
    }

    override fun getItemViewType(position: Int): Int {
        return matriceViewType[position]
    }


}