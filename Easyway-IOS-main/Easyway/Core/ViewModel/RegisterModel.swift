//
//  RegisterModel.swift
//  Easyway
//
//  Created by Rayen Majdoub on 15/3/2023.
//

import SwiftUI

class RegisterModel: ObservableObject {

    @Published var email: String = ""
    @Published var password: String = ""
    @Published var verifpassword: String = ""
    @Published var username: String = ""
    @Published var errorMessage: String?
    @Published var showpassword: Bool = false
    @Published var reEnterPassword :String = ""
    @Published var showRenterPassword : Bool = false
    @Published var registred = false

    
    func Register(completion: @escaping (Result<String, Error>) -> Void)
    {
        UserController.shared.signup(user: User(id:"0",username:username,email:email,password:password , role: "USER_ROLE")){ [weak self] result in
        DispatchQueue.main.async {
            switch result {
            case .success(let message):
                print(message)
                self?.registred = true
                completion(.success(message))
            case .failure(let error):
                self?.errorMessage = error.localizedDescription
                completion(.failure(error))
            }
        }
    }
}
        
    }
  
    

