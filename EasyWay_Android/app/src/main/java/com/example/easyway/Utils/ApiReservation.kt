package com.example.easyway.Utils

import com.example.easyway.ResponseUtils.ReservationRequest
import com.example.easyway.ResponseUtils.ReservationResponse
import com.example.easyway.ResponseUtils.ReservationResponseGet
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiReservation {
    @GET("reservation/allreservations/{user_id}")
    suspend fun GetUserReservations(@Path("user_id")user_id: String): Response<MutableList<ReservationResponseGet>>
    @GET("reservation/find/{id}")
    suspend fun getReservation(@Path("id")reservation_id: String): Response<ReservationResponseGet>
    @POST("reservation/")
    suspend fun SaveReservation(@Body req:ReservationRequest): Response<ReservationResponse>



    companion object {
        fun create(): ApiReservation {
            val RetrofitHelper: Retrofit = RetrofitHelper.getInstance()
            return RetrofitHelper.create(ApiReservation::class.java)
        }
    }
}