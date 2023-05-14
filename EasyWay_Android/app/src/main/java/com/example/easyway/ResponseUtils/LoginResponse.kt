package com.example.easyway.ResponseUtils
import com.google.gson.annotations.SerializedName
data class LoginResponse(
    @SerializedName("code")
    var code: Int,
    @SerializedName("data")
    var `data`: Data,
    @SerializedName("id")
    var id: String,
    @SerializedName("message")
    var message: String,
    @SerializedName("token")
    var token: String
) {
    data class Data(
        @SerializedName("_id")
        var id: String,
        @SerializedName("email")
        var email: String,
        @SerializedName("username")
        var username: String,
        @SerializedName("password")
        var password: String,


    )
}