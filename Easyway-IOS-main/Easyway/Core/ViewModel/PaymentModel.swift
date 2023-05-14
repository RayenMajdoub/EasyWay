//
//  PaymentModel.swift
//  Easyway
//
//  Created by Rayen Majdoub on 27/4/2023.
//

import Foundation
import StripePaymentSheet
import SwiftUI


class MyBackendModel: ObservableObject {
  @Published var paymentSheet: PaymentSheet?
  @Published var paymentResult: PaymentSheetResult?
  @Published var errorMessage: String?
  @Published var reservationId : String = ""
  var id_voyage: String = ""
  var selectedSeats: Set<Int>=[]
  var amount: Int = 0
    
    
  func preparePaymentSheet( amount :Int , selectedSeats:Set<Int>,id_voyage:String) {
      self.id_voyage = id_voyage
      self.selectedSeats = selectedSeats
      self.amount = amount
      let backendCheckoutUrl = URL(string:"\(Shared.sharedBaseUrl)/payement/payment-sheet/\(amount)")!
    var request = URLRequest(url: backendCheckoutUrl)
    request.httpMethod = "POST"
    let task = URLSession.shared.dataTask(with: request, completionHandler: { [weak self] (data, response, error) in
      guard let data = data,
            let json = try? JSONSerialization.jsonObject(with: data, options: []) as? [String : Any],
            let customerId = json["customer"] as? String,
            let customerEphemeralKeySecret = json["ephemeralKey"] as? String,
            let paymentIntentClientSecret = json["paymentIntent"] as? String,
            let publishableKey = json["publishableKey"] as? String,

            let self = self else {
        // Handle error
          print("im in error  \(error) ")
        return
      }
        
print ("im in error  data \(data) ")
        print("im in error  respose \(response) ")
      STPAPIClient.shared.publishableKey = publishableKey
      var configuration = PaymentSheet.Configuration()
      configuration.merchantDisplayName = "Tickets"
      configuration.customer = .init(id: customerId, ephemeralKeySecret: customerEphemeralKeySecret)
    //  configuration.allowsDelayedPaymentMethods = true
      DispatchQueue.main.async {
        self.paymentSheet = PaymentSheet(paymentIntentClientSecret: paymentIntentClientSecret, configuration: configuration )
        
      }
    })
    task.resume()
  }

  func onPaymentCompletion(result: PaymentSheetResult) {
    self.paymentResult = result
      
      switch paymentResult
      {
      case .completed:
          
          print("im in Completed ")
          reserver()
          { results in
              switch results {
              case .success(let res):
                self.reservationId = res
                  print(self.reservationId)
              case .failure(let error):
                  print(error)
              }
          }

      case .failed(let error):
          print("im in failed :\(error)")
      case .canceled:
          print("im in Cancled ")
      case .none:
          print("im in none")
      }
          
      
            

  }
    
 
    func reserver(completion: @escaping (Result<String, Error>) -> Void)
    {
        ReservationController.shared.reserver(id_voyage: self.id_voyage,selectedSeats: self.selectedSeats ,amount: self.amount){ [weak self] result in
            DispatchQueue.main.async {
                switch result {
                case .success(let message):
                    print(message)
                    completion(.success(message))
                case .failure(let error):
                    self?.errorMessage = error.localizedDescription
                    print(error)
                    completion(.failure(error))
                    
                }
            }
        }
    }
    
}

