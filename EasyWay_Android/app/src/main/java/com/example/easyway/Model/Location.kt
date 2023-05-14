package com.example.easyway.Model
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(tableName = "Location")
data class Location (
    @PrimaryKey(autoGenerate = true)
   val id:Int,
    @ColumnInfo(name = "lat")
    var lat: Double = 0.0,

    @ColumnInfo(name = "lng")
    var lng: Double = 0.0,
    val  address:String,
    val place : String,
    val  tag:String

){
}