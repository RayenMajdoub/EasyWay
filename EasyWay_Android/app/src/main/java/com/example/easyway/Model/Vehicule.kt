package com.example.easyway.Model

data class Vehicule(
    val id: Int,
    val Type: VehiculeType,
    //val Photo:,
    val seatFormation: SeatFormation,
    val voyage: Voyage
) {

}