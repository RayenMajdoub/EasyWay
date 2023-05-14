package com.example.easyway.Utils


import com.example.easyway.ResponseUtils.SeatFormationResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiSeatFormation {
    @GET("seatformation/{id}")
    suspend fun GetSeatFormation(@Path ("id") id: String): Response<SeatFormationResponse>


    companion object {
        fun create(): ApiSeatFormation {
            val RetrofitHelper: Retrofit = RetrofitHelper.getInstance()
            return RetrofitHelper.create(ApiSeatFormation::class.java)
        }
    }
}