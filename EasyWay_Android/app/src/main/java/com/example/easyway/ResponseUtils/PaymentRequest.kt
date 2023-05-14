package com.example.easyway.ResponseUtils

import com.google.gson.annotations.SerializedName

data class PaymentRequest(
    @SerializedName("amount")
    val  amount:Number,
   /* @SerializedName("currency")
    val  customer:String,*/
)