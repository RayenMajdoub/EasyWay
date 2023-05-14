package com.example.easyway.ResponseUtils

import com.google.gson.annotations.SerializedName

class VehiculeResponse(
    @SerializedName("code")
    var code: Int, @SerializedName("data")
    var `data`: Data,
    @SerializedName("id")
    var id: String,
    @SerializedName("message")
    var message: String
    )
    {
        data class Data(
        @SerializedName("type")
        var type: String,
        @SerializedName("id")
        var id: String,
        @SerializedName("name")
        var name: String,
        @SerializedName("available")
        var available: MutableList<Boolean>,
        @SerializedName("gridformationid")
        var gridformationid:String)
    }

