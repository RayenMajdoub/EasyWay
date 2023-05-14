//
//  ForgetPasswordModel.swift
//  Easyway
//
//  Created by Rayen Majdoub on 16/3/2023.
//

import SwiftUI

class ForgetPasswordModel: ObservableObject {
    
    @Published var email: String = ""
    @Published var password: String = ""
    @Published var verifpassword: String = ""
    @Published var verifCode :String = ""
    @Published var sent = false
    @Published var verifed = false
    @Published var resetDone = false
    @Published var errorMessage: String?

    func sendCode(completion: @escaping (Result<String, Error>) -> Void) {
            UserController.shared.sendCode(email: email)
                                           { [weak self] result in
                DispatchQueue.main.async {
                    switch result {
                    case .success(let code):
                        self?.sent = true
                        completion(.success(code))
                    case .failure(let error):
                        self?.errorMessage = error.localizedDescription
                        completion(.failure(error))
                    }
                }
            }
        }
        
        
    
    func verifyCode(code:String)->Bool
    {
        print(verifCode)
        print(code)
        if(verifCode == code)
        {
            
           return true
        }
        else
        {
            print("invalid code ")
            return false
        }
        
    }
    func changePassword(email:String,completion: @escaping (Result<String, Error>) -> Void)
    {
        if(verifpassword == password)
        {
            UserController.shared.resetPassword(email: email, password:password )
                                           { [weak self] result in
                DispatchQueue.main.async {
                    switch result {
                    case .success(let code):
                        self?.resetDone = true
                        completion(.success(code))
                    case .failure(let error):
                        self?.errorMessage = error.localizedDescription
                        completion(.failure(error))
                    }
                }
            }
        }
        else
        {
            print("verify the password ")
        }
     
    }
  
    
}

