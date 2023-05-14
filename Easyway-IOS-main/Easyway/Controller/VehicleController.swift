//
//  VehicleController.swift
//  Easyway
//
//  Created by Rayen Majdoub on 28/3/2023.
//

import Foundation
import Alamofire

class VehicleController {
    static let shared = VehicleController()

    private let baseUrl = "\(Shared.sharedBaseUrl)/vehicles" // Replace with your server URL
    
    func login(email: String, password: String, completion: @escaping (Result<String, Error>) -> Void) {
        let url = "\(baseUrl)/login"
        let parameters: [String: Any] = ["email": email,"password": password    ]
        print(parameters)

        AF.request(url, method: .post, parameters: parameters, encoding: JSONEncoding.default).responseJSON { response in
            print(response)
            switch response.result {
            case .success(let value):
                if let dict = value as? [String: Any], let token = dict["token"] as? String {
                    // Save the token to UserDefaults
                    print(parameters)
                    UserDefaults.standard.set(token, forKey: "jwtToken")
                    completion(.success(token))
                } else {
                    let error = NSError(domain: "", code: 0, userInfo: [NSLocalizedDescriptionKey: "Response serialization failed"])
                    completion(.failure(error))

                }
            case .failure(let error):
                completion(.failure(error))
            }
        }
    }

    private init() {}
}
