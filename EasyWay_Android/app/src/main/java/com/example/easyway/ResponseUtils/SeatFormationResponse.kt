package com.example.easyway.ResponseUtils

import com.google.gson.annotations.SerializedName

class SeatFormationResponse (
    @SerializedName("code")
    var code: Int,
    @SerializedName("_id")
    var id: String,
    @SerializedName("EconomyClassSeats")
    var EconomyClass:Int,
    @SerializedName("FirstClassSeats")
    var FirstClass: Int,
    @SerializedName("BusinessClassSeats")
    var BusinessClasss: Int,
    @SerializedName("fullrows")
    var fullrows: Int,
    @SerializedName("FullLines")
    var FullLines: Int,
    @SerializedName("EmptyRows")
    var EmptyRows: MutableList<Int>,
    @SerializedName("EmptyLines")
    var EmptyLines: MutableList<Int>,
    @SerializedName("message")
    var message: String
)
{


}