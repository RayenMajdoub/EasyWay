package com.example.easyway.Utils

import com.example.easyway.ResponseUtils.PaymentResponse
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiPayement {

    @GET("payement/payment-sheet/{amount}")
    suspend fun getPayment(@Path("amount") Float: Number): Response<PaymentResponse>


    companion object {
        fun create(): ApiPayement {
            val RetrofitHelper: Retrofit = RetrofitHelper.getInstance()
            return RetrofitHelper.create(ApiPayement::class.java)
        }
}
}