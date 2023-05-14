//
//  Voyage.swift
//  Easyway
//
//  Created by Rayen Majdoub on 28/3/2023.
//

import Foundation

struct Voyage:  Codable {
    var id :String
    var vehicle: Vehicle
    var departurePoint: String
    var arrivalPoint: String
    var departureDate: String
    var arrivalDate: String
    var distance: Double
    var firstClassSeatPrice: Double
    var economySeatPrice: Double
    var businessSeatPrice: Double
    var available: [Bool]
    
    enum CodingKeys: String, CodingKey {
        case id = "_id"
        case vehicle
        case departurePoint = "DeparturePoint"
        case arrivalPoint = "ArrivalPoint"
        case departureDate = "DepartureDate"
        case arrivalDate = "ArrivalDate"
        case distance = "Distance"
        case firstClassSeatPrice = "firstclassseatprice"
        case economySeatPrice = "economyseatprice"
        case businessSeatPrice = "businessseatprice"
        case available
    }
    
    
    init(id: String, vehicle: Vehicle, departurePoint: String, arrivalPoint: String, departureDate: String, arrivalDate: String, distance: Double, firstClassSeatPrice: Double, economySeatPrice: Double, businessSeatPrice: Double, available: [Bool]) {
        self.id = id
        self.vehicle = vehicle
        self.departurePoint = departurePoint
        self.arrivalPoint = arrivalPoint
        self.departureDate = departureDate
        self.arrivalDate = arrivalDate
        self.distance = distance
        self.firstClassSeatPrice = firstClassSeatPrice
        self.economySeatPrice = economySeatPrice
        self.businessSeatPrice = businessSeatPrice
        self.available = available
    }
    
}
let sampleTicketData: [Voyage] = [
    Voyage(id: "qdsdsq", vehicle: Vehicle(id: "String", name: "String", type: "bus", gridformationid: "aaaa"), departurePoint: "ariana", arrivalPoint: "Tunis", departureDate: "aa", arrivalDate: "String", distance: 10.12, firstClassSeatPrice: 10, economySeatPrice: 10, businessSeatPrice: 10, available: [true,false,true,true,true])
]


