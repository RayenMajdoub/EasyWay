package com.example.easyway.Utils


import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitHelper {

  //  val baseUrl = "http://192.168.56.1:3000/api"
 //val baseUrl = "http://192.168.56.1:8800/api/"
  // val baseUrl = "http://192.168.100.132:8800/api/"
  val baseUrl = "https://easywayback-production.up.railway.app/api/"

    fun getInstance(): Retrofit {
        return Retrofit
            .Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}