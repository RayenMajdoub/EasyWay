//
//  Vehicle.swift
//  Easyway
//
//  Created by Rayen Majdoub on 28/3/2023.
//

import Foundation

struct Vehicle:  Codable {
    let id : String
    let name: String
    let type: String
    let gridFormationId: String
    
    enum CodingKeys: String, CodingKey {
        case id = "_id"
        case name
        case type
        case gridFormationId = "gridformationid"
    }
    

    init(id: String, name: String, type: String, gridformationid: String) {
         self.id = id
         self.name = name
         self.type = type
         self.gridFormationId = gridformationid
     }


}
public enum VehicleType:String, CodingKey
{
    case ALL = "all"
    case BUS = "bus"
    case TRAIN = "train"
    case BIKE = "bike"
    case TAXI = "taxi"
       
       
}
