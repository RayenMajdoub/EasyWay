package com.example.easyway.Utils

import com.example.easyway.Model.User
import com.example.easyway.ResponseUtils.LoginRequest
import com.example.easyway.ResponseUtils.LoginResponse
import com.example.easyway.ResponseUtils.SignUpRequest
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiUser {
    @POST("auth/login")
  suspend  fun executeLogin(@Body loginRequest: LoginRequest): Response<LoginResponse>

    @POST("auth/register")
    suspend  fun executeSignup(@Body SignUpRequest: SignUpRequest): Call<User>

companion object{
    fun create():ApiUser{
        val RetrofitHelper:Retrofit = RetrofitHelper.getInstance()
        return RetrofitHelper.create(ApiUser::class.java)
    }
}
}