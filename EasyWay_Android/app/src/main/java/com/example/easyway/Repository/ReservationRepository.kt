package com.example.easyway.Repository

import android.util.Log
import com.example.easyway.ResponseUtils.ReservationRequest
import com.example.easyway.ResponseUtils.ReservationResponse
import com.example.easyway.ResponseUtils.ReservationResponseGet
import com.example.easyway.Utils.ApiReservation
import retrofit2.Response

class ReservationRepository {
    suspend fun GetMyReservations(id: String): Response<MutableList<ReservationResponseGet>> {
        val Api: ApiReservation = ApiReservation.create()
        val response: Response<MutableList<ReservationResponseGet>> = Api.GetUserReservations(id)
        Log.w("GotSeatFormation", response.body()!!.toString())
        return response
    }  suspend fun getReservation(id: String): Response<ReservationResponseGet> {
        val Api: ApiReservation = ApiReservation.create()
        val response: Response<ReservationResponseGet> = Api.getReservation(id)
        Log.w("get reservation", response.body()!!.toString())
        return response
    }
    suspend fun SaveReservation(ReservationReq:ReservationRequest): Response<ReservationResponse> {
        val Api: ApiReservation = ApiReservation.create()
        Log.w("ReservationRepository", "Im in Reservation")
        val response: Response<ReservationResponse> = Api.SaveReservation(ReservationReq)
        Log.w("ReservationRepository", "Im in Reservation")

        return response
    }
}