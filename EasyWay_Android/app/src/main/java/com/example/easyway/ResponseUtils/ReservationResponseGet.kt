package com.example.easyway.ResponseUtils

import com.google.gson.annotations.SerializedName

data class ReservationResponseGet(@SerializedName("_id") val Reservation_id : String,
                                  @SerializedName("user") val user : userR,
                                  @SerializedName("Seatnumbers") val seatnumbers : MutableList<Int>,
                                  @SerializedName("voyage") val voyage : VoyagesListResponse,
                                  @SerializedName("qr") val qr : String,
                                  @SerializedName("totaleprice") val totaleprice : Number,
)

{
    data class userR(
        @SerializedName("_id") val id : String,
        @SerializedName("username") val username : String,
        @SerializedName("email") val email : String,
        @SerializedName("password") val password : String,
    )
}
