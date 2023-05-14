//
//  VoyageController.swift
//  Easyway
//
//  Created by Rayen Majdoub on 28/3/2023.
//

import Foundation
import Alamofire

class VoyageController {
    static let shared = VoyageController()
    
    private let baseUrl = "\(Shared.sharedBaseUrl)/voyage" // Replace with your server URL
    
    func getVoyages(type: String, departurePoint: String, arrivalPoint: String, completion: @escaping (Result<[Voyage], Error>) -> Void) {
        let url = "\(baseUrl)/allvoyages/\(type)/\(departurePoint)/\(arrivalPoint)"
        AF.request(url,method: .get ,encoding: JSONEncoding.default)
            .validate()
            .responseDecodable(of: [Voyage].self) { response in
                print(response)
                switch response.result {
                case .success(let voyages):
                    completion(.success(voyages))
                case .failure(let error):
                    completion(.failure(error))
                }
            }
    }

    private init() {}
}
