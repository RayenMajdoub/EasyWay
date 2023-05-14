//
//  Reservation.swift
//  Easyway
//
//  Created by Rayen Majdoub on 28/3/2023.
//

import Foundation

struct Reservation: Codable {
    var id : String
    var user: User
    var seatNumbers: [Int]
    var voyage: Voyage
    var qr: String
    var totalPrice: Int
    
    enum CodingKeys: String, CodingKey {
        case id = "_id"
        case user = "user"
        case seatNumbers = "Seatnumbers"
        case voyage
        case qr
        case totalPrice = "totaleprice"
    }
    

}
