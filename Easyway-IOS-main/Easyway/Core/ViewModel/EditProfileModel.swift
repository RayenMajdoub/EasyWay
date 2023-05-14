//
//  EditProfileModel.swift
//  Easyway
//
//  Created by Rayen Majdoub on 13/4/2023.
//

import SwiftUI


class EditProfileModel: ObservableObject {
    
    @Published var username: String = ""
    @Published var password: String = ""
    @Published var verifpassword: String = ""
    @Published var errorMessage: String?
    
    func updateProfile(completion: @escaping (Result<String, Error>) -> Void)
    {
        if(verifpassword == password)
        {
            UserController.shared.updateProfile(username: username, password:password )
            { [weak self] result in
                DispatchQueue.main.async {
                    switch result {
                    case .success(let message):
                        
                        completion(.success(message))
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
  
