package com.example.easyway.ViewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.easyway.Repository.VehiculeRepository
import com.example.easyway.Repository.VoyageRepository
import com.example.easyway.ResponseUtils.*
import kotlinx.coroutines.launch

class VoyagesViewModel (application: Application) : AndroidViewModel(application){
    val VoyageListResult: MutableLiveData<BaseResponse<MutableList<VoyagesListResponse>>> = MutableLiveData()
    val getVoyageListResult: MutableLiveData<MutableList<VoyagesListResponse>> = MutableLiveData()

    fun VoyagesData( TR:TicketsListRequest)  {
        val VoyageRepository: VoyageRepository = VoyageRepository()
        VoyageListResult.value = BaseResponse.Loading()
        viewModelScope.launch {
            try {
                val response = VoyageRepository.GetVoyages(TR)
                Log.w("VoyagesLoadedSuccess",response.body()!![0].toString())
                Log.w("VoyagesDatasuccess",response.code().toString())
                if (response.code() == 200) {
                    VoyageListResult.value = BaseResponse.Success(response.body())
               //     getVoyageListResult.value=response.body()
                    Log.w("ListSuccessssssss",VoyageListResult.value.toString())
               //     Log.w("VoyagesLoadedSuccessfully",getVoyageListResult.value[0].vehicule.name)
                } else {
                    VoyageListResult.value = BaseResponse.Error(response.message())
                    Log.w("erreur else",response.message())

                }

            } catch (ex: Exception) {
                VoyageListResult.value = BaseResponse.Error(ex.message)
                Log.w("erreur exception",ex.message.toString())

            }
        }
    }
}