//
//  SeatFormation.swift
//  Easyway
//
//  Created by Rayen Majdoub on 28/3/2023.
//

import Foundation

struct SeatFormation:Codable {
    let id: String
    let businessClassSeats: Int
    let firstClassSeats: Int
    let economyClassSeats: Int
    let fullRows: Int
    let fullLines: Int
    let emptyRows: [Int]
    let emptyLines: [Int]
    
    
    
    
    enum CodingKeys: String, CodingKey {
        case id = "_id"
        case businessClassSeats = "BusinessClassSeats"
        case firstClassSeats = "FirstClassSeats"
        case economyClassSeats = "EconomyClassSeats"
        case fullRows = "fullrows"
        case fullLines = "FullLines"
        case emptyRows = "EmptyRows"
        case emptyLines = "EmptyLines"
    }
    init(id: String = "", businessClassSeats: Int = 0, firstClassSeats: Int = 0, economyClassSeats: Int = 0, fullRows: Int = 0, fullLines: Int = 0, emptyRows: [Int] = [0], emptyLines: [Int] = [0])  {
         self.id = id
         self.businessClassSeats = businessClassSeats
         self.firstClassSeats = firstClassSeats
         self.economyClassSeats = economyClassSeats
         self.fullRows = fullRows
         self.fullLines = fullLines
         self.emptyRows = emptyRows
         self.emptyLines = emptyLines
     }

}
