package com.example.easyway.ResponseUtils

import com.google.gson.annotations.SerializedName
import com.stripe.android.paymentsheet.PaymentSheet

data class PaymentResponse (
    @SerializedName("paymentIntent")
  val  paymentIntentClientSecret:String,
    @SerializedName("customer")
  val  customer:String,
    @SerializedName("ephemeralKey")
  val  ephemeralKey:String,
    @SerializedName("publishableKey")
  val  publishableKey:String,

        )



