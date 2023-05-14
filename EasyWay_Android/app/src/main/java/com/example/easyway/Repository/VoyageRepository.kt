package com.example.easyway.Repository

import android.util.Log
import com.example.easyway.ResponseUtils.TicketsListRequest
import com.example.easyway.ResponseUtils.VoyagesListResponse
import com.example.easyway.Utils.ApiVoyage
import retrofit2.Response

class VoyageRepository {
    suspend fun GetVoyages(
       t: TicketsListRequest
    ): Response<MutableList<VoyagesListResponse>>
    {
        val Api: ApiVoyage = ApiVoyage.create()
        val response : Response<MutableList<VoyagesListResponse>> = Api.GetVoyages(t.Type,t.DeparturePoint,t.ArrivalPoint)
        Log.w("GotSeatFormation", response.body().toString())
        return response
    }
}