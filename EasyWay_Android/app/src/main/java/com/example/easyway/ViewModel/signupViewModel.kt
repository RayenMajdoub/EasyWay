package com.example.easyway.ViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.easyway.Model.User
import com.example.easyway.ResponseUtils.SignUpRequest
import com.example.easyway.Utils.ApiUser
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class signupViewModel : ViewModel() {

        val SignuResult= MutableLiveData<Int>()

        fun userSignup(fullname: String, email: String, password: String) {
            SignuResult.postValue(4)
            val apiInterface = ApiUser.create()
            val SignUpRequest: SignUpRequest = SignUpRequest(email, fullname, password)

            viewModelScope.launch {
                apiInterface.executeSignup(SignUpRequest).enqueue(object : Callback<User> {

                    override fun onResponse(call: Call<User>, response: Response<User>) {

                        val user = response.body()

                        if (user != null) {
                            SignuResult.postValue(1)
                        } else {
                            SignuResult.postValue(2)
                        }


                    }

                    override fun onFailure(call: Call<User>, t: Throwable) {
                        SignuResult.postValue(3)
                    }

                })

            }
        }
    }
