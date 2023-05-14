package com.example.easyway.Model

import android.util.Log
import com.example.easyway.ResponseUtils.SeatFormationResponse
import com.example.easyway.ResponseUtils.VehiculeResponse

data class SeatFormation(
    var id: Int,
    var TotalSeatsFormation: Map<SeatClass, Int>,// map pour les Seat total : chaque class possede combien de seat
    var gridFormation: GridFormation?,
    var SingleSeats: MutableList<SingleSeat>

) {
    data class GridFormation(
        val id: String,
        val FullRows: Int,
        val FullLines: Int,
        val EmptyRows: MutableList<Int>,//list de position de chaque empty row
        val EmptyLines: MutableList<Int>//list de position de chaque empty Line
    )

    data class SingleSeat(
        val number: Int,
        val Class: SeatClass,
        val Reserved: Boolean = false
    )

    public fun initGridFormation(SeatFormationResponse: SeatFormationResponse) {
        this.gridFormation = GridFormation(
            SeatFormationResponse.id,
            SeatFormationResponse.fullrows,
            SeatFormationResponse.FullLines,
            SeatFormationResponse.EmptyRows,
            SeatFormationResponse.EmptyLines
        )

        this.TotalSeatsFormation =
            mapOf(
                SeatClass.ECONOMY_CLASS to SeatFormationResponse.EconomyClass,
                SeatClass.FIRST_CLASS to SeatFormationResponse.FirstClass,
                SeatClass.BUSINESS_CLASS to SeatFormationResponse.BusinessClasss
            )


    }

    public fun initSeatList(VehiculeResponse: VehiculeResponse) {
        var nb = 0
        for ((c, n) in this.TotalSeatsFormation) {

            for (i in 1..n) {
                nb++
                val singleseat: SeatFormation.SingleSeat =
                    SeatFormation.SingleSeat(nb, c, VehiculeResponse.data.available[i])
                this.SingleSeats.add(singleseat)
                Log.w("STEAT", "" + singleseat)
            }

        }


    }
}


