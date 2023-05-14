//
//  ReservationController.swift
//  Easyway
//
//  Created by Rayen Majdoub on 28/3/2023.
//

import Foundation
import Alamofire
class ReservationController {
    static let shared = ReservationController()
    let  userdefaults = UserDefaults.standard

    private let baseUrl = "\(Shared.sharedBaseUrl)/reservation/" // Replace with your server URL
    func reserver(id_voyage:String,selectedSeats : Set<Int>,amount:Int, completion: @escaping (Result<String, Error>) -> Void) {
        print(selectedSeats)
        print(amount)
        let  user = userdefaults.string(forKey: "email")!
        print(user)
        
        AF.request(baseUrl,
                   method: .post,
                   parameters: ["Seatnumbers": Array(selectedSeats) ,"user":user,"voyage":id_voyage,"totaleprice":amount],
                   encoding: JSONEncoding.default)
            .responseJSON { response in
                print(response)
                switch response.result {
                case .success(let message):
                    if let dict = message as? [String: Any], let message = dict["id"] as? String {
                        print ("im in success")
                        print(message)
                        completion(.success(message))
                    } else {
                        print("im in error ")
                        let error = NSError(domain: "", code: 0, userInfo: [NSLocalizedDescriptionKey: "Response serialization failed"])
                        completion(.failure(error))


                    }
                case .failure(let error):
                    completion(.failure(error))
                }
            }
    }
    func getReservation(idRes:String ,completion: @escaping (Result<Reservation, Error>) -> Void)
    {
        let url = "\(baseUrl)find/\(idRes)"
        AF.request(url,
                   method: .get,
                   encoding: JSONEncoding.default)
        .validate()
        .responseDecodable(of:Reservation.self) { response in
            print(response)
            switch response.result {
            case .success(let reservation):
                completion(.success(reservation))
            case .failure(let error):
                completion(.failure(error))
            }
        }
    }
    
    func getMyTickets( completion: @escaping (Result<[Reservation], Error>) -> Void)
    {
        let user_email: String = userdefaults.string(forKey: "email")!
        let url = "\(baseUrl)allreservations/\(user_email)"
        AF.request(url,
                   method: .get,
                   encoding: JSONEncoding.default)
        .validate()
        .responseDecodable(of:[Reservation].self) { response in
            print(response)
            switch response.result {
            case .success(let reservations):
                completion(.success(reservations))
            case .failure(let error):
                completion(.failure(error))
            }
        }
    }
    
    private init() {}
}
