package com.example.easyway.Utils

import com.example.easyway.ResponseUtils.VoyagesListResponse
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.GET

import retrofit2.http.Path

interface ApiVoyage {
    @GET("voyage/allvoyages/{type}/{DeparturePoint}/{ArrivalPoint}")
    suspend fun GetVoyages(@Path("type")Type: String,@Path("DeparturePoint")DeparturePoint: String,@Path("ArrivalPoint")ArrivalPoint: String,): Response<MutableList<VoyagesListResponse>>



    companion object {
        fun create(): ApiVoyage {
            val RetrofitHelper: Retrofit = RetrofitHelper.getInstance()
            return RetrofitHelper.create(ApiVoyage::class.java)
        }
    }
}