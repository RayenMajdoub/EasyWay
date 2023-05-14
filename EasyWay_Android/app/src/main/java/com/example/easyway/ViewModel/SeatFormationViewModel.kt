package com.example.easyway.ViewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.easyway.Repository.UserRepository
import com.example.easyway.Repository.VehiculeRepository
import com.example.easyway.ResponseUtils.BaseResponse
import com.example.easyway.ResponseUtils.LoginRequest
import com.example.easyway.ResponseUtils.LoginResponse
import com.example.easyway.ResponseUtils.SeatFormationResponse
import kotlinx.coroutines.launch

class SeatFormationViewModel(application: Application) : AndroidViewModel(application) {
    val seatFormationResult: MutableLiveData<BaseResponse<SeatFormationResponse>> = MutableLiveData()
    var SeatFormationResponse: MutableLiveData<SeatFormationResponse> = MutableLiveData()

    fun SeatFormationResultData(id: String) {
        val VehiculeRepository: VehiculeRepository = VehiculeRepository()

        seatFormationResult.value = BaseResponse.Loading()
        viewModelScope.launch {
            try {


                val response = VehiculeRepository.GetSeatFormationData(id)
                Log.w("SeatFormat", response?.body().toString())
                Log.w("SeatFormation", response?.code().toString())
                if (response?.code() == 200) {
                    SeatFormationResponse.value = response.body()!!
                    Log.w("Login success ", response.body().toString())
                    seatFormationResult.value = BaseResponse.Success(response.body())
                    Log.w("seatFormationResult", SeatFormationResponse.value.toString())

                } else {
                    seatFormationResult.value = BaseResponse.Error(response?.message())
                }

            } catch (ex: Exception) {
                seatFormationResult.value = BaseResponse.Error(ex.message)
            }
        }
    }
}