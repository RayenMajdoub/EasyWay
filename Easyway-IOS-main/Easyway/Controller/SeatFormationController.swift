//
//  SeatFormationController.swift
//  Easyway
//
//  Created by Rayen Majdoub on 28/3/2023.
//

import Foundation
import Alamofire

class SeatFormationController {
    static let shared = SeatFormationController()
    
    private let baseUrl = "\(Shared.sharedBaseUrl)/seatformation" // Replace with your server URL
    
    
    func getSeatFormation(id: String, completion: @escaping (Result<SeatFormation, Error>) -> Void) {
        let url = "\(baseUrl)/\(id)"
        AF.request(url,method: .get ,encoding: JSONEncoding.default)
            .validate()
            .responseDecodable(of: SeatFormation.self) { response in
                print(response)
                switch response.result {
                case .success(let formation):
                    completion(.success(formation))
                case .failure(let error):
                    completion(.failure(error))
                }
            }
    }
    private init() {}
}

