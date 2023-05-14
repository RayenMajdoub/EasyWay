//
//  User.swift
//  Easyway
//
//  Created by Rayen Majdoub on 23/3/2023.
//

import Foundation
struct User: Codable
{
    
    let _id: String
    let username: String
    let email: String
    let password: String
    let role:String
    //  let image: Image?
 
    enum CodingKeys: String, CodingKey {
        case _id = "_id"
        case username  = "username"
        case email = "email"
        case password = "password"
        //   case image
        case role = "role"
    }
    
    init( id: String, username: String, email: String, password: String , role:String){
        self.username = username
        self._id = id
        self.email = email
        self.password = password
        self.role = role
    }

    
    
}
