//
//  ReservationModel.swift
//  Easyway
//
//  Created by Rayen Majdoub on 6/5/2023.
//

import SwiftUI

class ReservationModel: ObservableObject {
    @Published var errorMessage: String?


    func getReservation(idRes:String ,completion: @escaping (Result<Reservation, Error>) -> Void)
    {
        
        ReservationController.shared.getReservation(idRes:idRes){ [weak self] result in
            DispatchQueue.main.async {
                switch result {
                case .success(let message):
                    print(message)
               
                    completion(.success(message))
                case .failure(let error):
                    self?.errorMessage = error.localizedDescription
                    completion(.failure(error))
                }
            }
        }
        
        
    }
    
    func getMyTickets(completion: @escaping (Result<[Reservation], Error>) -> Void)
    {
        
        ReservationController.shared.getMyTickets(){ [weak self] result in
            DispatchQueue.main.async {
                switch result {
                case .success(let message):
                    print(message)
     
                    completion(.success(message))
                case .failure(let error):
                    self?.errorMessage = error.localizedDescription
                    completion(.failure(error))
                }
            }
        }
        
        
    }
   
}
