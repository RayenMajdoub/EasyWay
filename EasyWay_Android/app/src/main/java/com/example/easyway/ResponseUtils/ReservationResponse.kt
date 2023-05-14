package com.example.easyway.ResponseUtils

import com.google.gson.annotations.SerializedName

data class ReservationResponse (
        @SerializedName("_id")
        var Reservation_id : String,
        @SerializedName("user")
        var user : String,
        @SerializedName("Seatnumbers")
        var seatnumbers : MutableList<Int>,
        @SerializedName("voyage")
        var voyage : String,
      //  @SerializedName("qr") val qr : String,
        @SerializedName("totaleprice")
        var totaleprice : Number,
        )

{

}
