package com.example.easyway.Repository

import android.util.Log
import com.example.easyway.ResponseUtils.LoginRequest
import com.example.easyway.ResponseUtils.LoginResponse
import com.example.easyway.Utils.ApiUser

import retrofit2.Response


class UserRepository() {

 suspend fun LoginData (loginRequest: LoginRequest): Response<LoginResponse>?{
     val apiUser: ApiUser = ApiUser.create()
     val response :Response<LoginResponse>?= apiUser.executeLogin(loginRequest = loginRequest)
     Log.w("Login success ",response?.body().toString())

     return  response
     }
}