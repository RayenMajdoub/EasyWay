package com.example.easyway.Repository

import android.util.Log

import com.example.easyway.ResponseUtils.SeatFormationResponse
import com.example.easyway.Utils.ApiSeatFormation

import retrofit2.Response

class VehiculeRepository {
    suspend fun GetSeatFormationData(id: String): Response<SeatFormationResponse>? {
        val Api: ApiSeatFormation = ApiSeatFormation.create()
        val response: Response<SeatFormationResponse>? = Api.GetSeatFormation(id = id)
        Log.w("GotSeatFormation", response?.body()!!.fullrows.toString())
        return response
    }



}