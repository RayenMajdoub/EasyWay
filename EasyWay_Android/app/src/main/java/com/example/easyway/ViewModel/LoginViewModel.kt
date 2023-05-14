package com.example.easyway.ViewModel

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.easyway.Model.User
import com.example.easyway.Repository.UserRepository
import com.example.easyway.ResponseUtils.BaseResponse
import com.example.easyway.ResponseUtils.LoginRequest
import com.example.easyway.ResponseUtils.LoginResponse

import kotlinx.coroutines.launch
import retrofit2.Call

class LoginViewModel(application: Application) : AndroidViewModel(application) {
    val loginResult: MutableLiveData<BaseResponse<LoginResponse>> = MutableLiveData()
    fun Login( email: String, password: String)  {
        val UserRepository: UserRepository = UserRepository()
        loginResult.value = BaseResponse.Loading()
        viewModelScope.launch {
            try {

                val loginRequest = LoginRequest(
                    password = password,
                    email = email
                )
                Log.w("Login success","hi IM IN ")

                val response = UserRepository.LoginData(loginRequest = loginRequest)
                Log.w("Login success ",response?.body().toString())
                Log.w("Login success ",response?.code().toString())

                if (response?.code() == 200) {

                    Log.w("Login success ",response.body().toString())
                    loginResult.value = BaseResponse.Success(response.body())
                } else {
                    loginResult.value = BaseResponse.Error(response?.message())
                }

            } catch (ex: Exception) {
                loginResult.value = BaseResponse.Error(ex.message)
            }
        }
    }


    fun LoginGoogle() {}
    fun LoginFacebook() {}
}