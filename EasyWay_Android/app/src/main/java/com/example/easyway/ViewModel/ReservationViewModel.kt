package com.example.easyway.ViewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.easyway.Repository.ReservationRepository
import com.example.easyway.Repository.SessionManager
import com.example.easyway.ResponseUtils.*
import kotlinx.coroutines.launch

class ReservationViewModel(application: Application) : AndroidViewModel(application) {
    val ReservationSaveResult: MutableLiveData<BaseResponse<ReservationResponse>> = MutableLiveData()
    val reservation_ticket_result: MutableLiveData<BaseResponse<ReservationResponseGet>> = MutableLiveData()
    val myReservationsResult: MutableLiveData<BaseResponse<MutableList<ReservationResponseGet>>> = MutableLiveData()
    fun ReservationSave( Reservation:ReservationRequest)
    {
      val  ReservationRepository:ReservationRepository= ReservationRepository()
        ReservationSaveResult.value = BaseResponse.Loading()
        viewModelScope.launch {
            try {


                Log.w("Reserved success","hi IM IN ")

                val response = ReservationRepository.SaveReservation(Reservation)


                if (response.code() == 200) {
                    Log.w("Reserved success","hi IM IN 200 ")
                    ReservationSaveResult.value = BaseResponse.Success(response.body())
                } else {
                    ReservationSaveResult.value = BaseResponse.Error(response.message())
                }

            } catch (ex: Exception) {
                ReservationSaveResult.value = BaseResponse.Error(ex.message)
            }
        }

    }
    fun getReservation(id_res:String)
    {
        val  ReservationRepository:ReservationRepository= ReservationRepository()
        reservation_ticket_result.value = BaseResponse.Loading()
        viewModelScope.launch {
            try {


                Log.w("getReservationTicket","hi IM IN ")

                val response = ReservationRepository.getReservation(id_res)


                if (response.code() == 200) {
                    Log.w("getReservationTicket","hi IM IN 200 ")
                    reservation_ticket_result.value = BaseResponse.Success(response.body())
                } else {
                    reservation_ticket_result.value = BaseResponse.Error(response.message())
                }

            } catch (ex: Exception) {
                reservation_ticket_result.value = BaseResponse.Error(ex.message)
            }
        }
    }
    fun MyReservations(id_user:String)
    {
        val  ReservationRepository:ReservationRepository= ReservationRepository()
        myReservationsResult.value = BaseResponse.Loading()
        viewModelScope.launch {
            try {


                Log.w("getReservationTicket","hi IM IN ")

                val response = ReservationRepository.GetMyReservations(id_user)


                if (response.code() == 200) {
                    Log.w("getReservationTicket","hi IM IN 200 ")
                    myReservationsResult.value = BaseResponse.Success(response.body())
                } else {
                    myReservationsResult.value = BaseResponse.Error(response.message())
                }

            } catch (ex: Exception) {
                myReservationsResult.value = BaseResponse.Error(ex.message)
            }
        }
    }
}