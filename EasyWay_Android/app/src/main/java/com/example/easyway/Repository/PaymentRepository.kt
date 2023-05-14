package com.example.easyway.Repository

import android.util.Log
import com.example.easyway.ResponseUtils.PaymentRequest
import com.example.easyway.ResponseUtils.PaymentResponse
import com.example.easyway.Utils.ApiPayement
import retrofit2.Response

class PaymentRepository {
    suspend fun getPaymentInfo(paymentRequest: PaymentRequest): Response<PaymentResponse> {
        val Api: ApiPayement = ApiPayement.create()

        val response: Response<PaymentResponse> = Api.getPayment(paymentRequest.amount)
        Log.w("Payment Response : ", response.body()!!.toString())
        return response
    }
}