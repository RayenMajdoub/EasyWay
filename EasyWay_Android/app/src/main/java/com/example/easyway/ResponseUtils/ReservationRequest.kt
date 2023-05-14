package com.example.easyway.ResponseUtils

import com.google.gson.annotations.SerializedName

data class ReservationRequest (
   @SerializedName("user")
    var user_id: String,
    @SerializedName("voyage")
    var voyage_id: String,
    @SerializedName("totaleprice")
    var totaleprice: Number,
    @SerializedName("Seatnumbers")
    var Seatnumbers: MutableList<Int>
//qr
)

