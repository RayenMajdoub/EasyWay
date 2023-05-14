package com.example.easyway.ResponseUtils

import com.google.gson.annotations.SerializedName

class VoyagesListResponse (
    @SerializedName("_id")
    var idVoyage: String,
    @SerializedName("vehicle")
    var vehicule: vehiculeJson,
    @SerializedName("DeparturePoint")
    var DeparturePoint: String,
    @SerializedName("ArrivalPoint")
    var ArrivalPoint: String,
    @SerializedName("DepartureDate")
    var DepartureDate: String,
    @SerializedName("ArrivalDate")
    var ArrivalDate: String ,
    @SerializedName("Distance")
    var Distance: Float,
    @SerializedName("available")
    var available: MutableList<Boolean>,
    @SerializedName("firstclassseatprice")
    var FirstPrice:Float,
    @SerializedName("economyseatprice")
    var EcoPrice:Float,
    @SerializedName("businessseatprice")
    var BuisPrice:Float

){
data class vehiculeJson(
    @SerializedName("_id")
    var idVehicule: String,
    @SerializedName("name")
    var name: String,
    @SerializedName("type")
    var type: String,
    @SerializedName("gridformationid")
    var gridformationid: String,
)
            /*  "_id": "638b578c97893a517489c480",
        "vehicle": [
        {
            "_id": "638b55be97893a517489c47c",
            "name": "metroAriana",
            "type": "train",
            "availabl(
          e": [],
            "gridformationid": "638b55b397893a517489c47a",
            "__v": 0
        }
        ]*/

    }