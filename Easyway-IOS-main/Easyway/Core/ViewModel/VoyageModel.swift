//
//  VoyageModel.swift
//  Easyway
//
//  Created by Rayen Majdoub on 28/3/2023.
//

import SwiftUI

class VoyageModel: ObservableObject {
    
    @Published var voyagesloaded = false
    @Published var errorMessage: String?
    @Published var ticketList: [Voyage] = []
    @Published var gridformation = SeatFormation()
    func GetVoyages(type:String ,departurePoint: String,arrivalPoint: String,completion: @escaping (Result<[Voyage], Error>) -> Void)
    {
        
        VoyageController.shared.getVoyages(type:type, departurePoint: departurePoint, arrivalPoint:arrivalPoint ){ [weak self] result in
            DispatchQueue.main.async {
                switch result {
                case .success(let message):
                    print(message)
                    self?.voyagesloaded = true
                    self?.ticketList = message
                    print(   self?.ticketList)
                    completion(.success(message))
                case .failure(let error):
                    self?.errorMessage = error.localizedDescription
                    completion(.failure(error))
                }
            }
        }
        
        
    }
    func getGridFormation(id:String,completion: @escaping (Result<SeatFormation, Error>) -> Void)
    {
        SeatFormationController.shared.getSeatFormation(id:id){ [weak self] result in
            DispatchQueue.main.async {
                switch result {
                case .success(let formation):
                    print(formation)
                    self?.gridformation = formation
                    completion(.success(formation))
                case .failure(let error):
                    self?.errorMessage = error.localizedDescription
                    completion(.failure(error))
                }
            }
        }}
}
