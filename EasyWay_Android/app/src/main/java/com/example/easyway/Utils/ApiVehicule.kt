package com.example.easyway.Utils
import com.example.easyway.ResponseUtils.VehiculeResponse
import com.example.easyway.ResponseUtils.TicketsListRequest
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.Body
import retrofit2.http.GET

interface ApiVehicule {
    @GET("vehicules/find/")
    suspend fun GetVehicules(@Body VehiculeRequest: TicketsListRequest): Response<MutableList<VehiculeResponse>>



    companion object {
        fun create(): ApiVehicule {
            val RetrofitHelper: Retrofit = RetrofitHelper.getInstance()
            return RetrofitHelper.create(ApiVehicule::class.java)
        }
    }
}