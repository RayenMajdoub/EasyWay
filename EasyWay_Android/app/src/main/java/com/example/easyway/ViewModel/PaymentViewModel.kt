package com.example.easyway.ViewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.easyway.Repository.PaymentRepository
import com.example.easyway.ResponseUtils.BaseResponse
import com.example.easyway.ResponseUtils.LoginResponse
import com.example.easyway.ResponseUtils.PaymentRequest
import com.example.easyway.ResponseUtils.PaymentResponse
import kotlinx.coroutines.launch

class PaymentViewModel (application: Application) : AndroidViewModel(application){
    val paymentResult: MutableLiveData<BaseResponse<PaymentResponse>> = MutableLiveData()
    fun getPaymentVM(paymentRequest: PaymentRequest)
    {
        val PaymentRepository:PaymentRepository = PaymentRepository()
        paymentResult.value = BaseResponse.Loading()
        viewModelScope.launch {
            try { Log.w("PaymentSuccess","hi IM IN ")

                val response = PaymentRepository.getPaymentInfo(paymentRequest)
                Log.w("PaymentSuccess ",response?.body().toString())
                Log.w("PaymentSuccess ",response?.code().toString())

                if (response?.code() == 200) {

                    Log.w("PaymentSuccess ",response.body().toString())
                    paymentResult.value = BaseResponse.Success(response.body())
                } else {
                    paymentResult.value = BaseResponse.Error(response?.message())
                }

            } catch (ex: Exception) {
                paymentResult.value = BaseResponse.Error(ex.message)
            }
        }
    }
}