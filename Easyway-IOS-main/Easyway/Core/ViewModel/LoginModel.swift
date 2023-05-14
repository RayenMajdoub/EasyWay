//
//  LoginModel.swift
//  Easyway
//
//  Created by Rayen Majdoub on 15/3/2023.
//

import SwiftUI
import Combine
class LoginModel: ObservableObject {

    @Published var email: String = ""
    @Published var password: String = ""
    @Published var showpassword: Bool = false
    @Published var reEnterPassword :String = ""
    @Published var showRenterPassword : Bool = false
    @Published var isLoggingIn = false
    @Published var errorMessage: String?
    @Published var loggedIn = false 
    
    func login(completion: @escaping (Result<String, Error>) -> Void) {
        UserController.shared.login(email: email, password: password) { [weak self] result in
            DispatchQueue.main.async {
                switch result {
                case .success(let token):
                    // Save token to UserDefaults or Keychain
                    self?.loggedIn = true
                    completion(.success(token))
                case .failure(let error):
                    self?.errorMessage = error.localizedDescription
                    completion(.failure(error))
                }
            }
        }
    }
    
}


