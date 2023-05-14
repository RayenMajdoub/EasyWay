//
//  ScannerPage.swift
//  Easyway
//
//  Created by Rayen Majdoub on 7/5/2023.
//

import SwiftUI
import CodeScanner
struct ScannerPage: View {
    @State  var isPresentingScanner = false
    @State var codedata:String = ""
    @State var reservation:Reservation?
    @State var validticket:String = ""
    @StateObject var viewmodelReservation = ReservationModel()
    private func formatDate(_ dateString: String) -> String {
        let formatter = DateFormatter()
        formatter.dateFormat = "yyyy-MM-dd'T'HH:mm:ss.SSSZ"
        let date = formatter.date(from: dateString)!
        formatter.dateFormat = "MMM d, yyyy h:mm a"
        return formatter.string(from: date)
    }
    private func getVehicleIcon(_ vehicleType: String) -> String {
        switch vehicleType {
        case VehicleType.BUS.rawValue:
            return "bus"
        case VehicleType.TRAIN.rawValue:
            return "tram"
        case VehicleType.BIKE.rawValue:
            return "bicycle"
        case VehicleType.TAXI.rawValue:
            return "taxi"
        default:
            return "car"
        }
    }
    var scannerSheet: some View
    {
        CodeScannerView(codeTypes: [.qr], completion: {
            result in
            if case let .success(code) = result
            {
         
                self.isPresentingScanner = false
                
                viewmodelReservation.getReservation(idRes: code.string){
                    ress in
                    switch ress {
                    case .success(let res):
                        self.validticket = "Valid Ticket"
                        self.reservation = res
                    case .failure(let error):
                        self.validticket = "Ticket is not valid"
                        self.reservation = nil
                        print(error)
                    }
                }
            }
        })
    }
    
    var body: some View {
        NavigationView{
            VStack(spacing: 10)
            {  ZStack
                {
                    
                    VStack
                    {
                        Text("Validate Tickets")
                            .font(.custom(Fonts.Font1, size: 38))
                            .frame(maxWidth: .infinity ,alignment: .center)
                            .foregroundColor(.white)
                            .padding(30)
                        Spacer()
                    }
                    }.frame(height: 120)
                        .frame(maxWidth: .infinity)
                        .background(Color(Colors.ColorPrimary))
                if let reservation = reservation {
                    VStack(spacing: 20){
                        HStack {
                            Text(reservation.voyage.departurePoint)
                                .font(.title)
                            Image(systemName: "arrow.right")
                                .foregroundColor(.gray)
                            Image(systemName: getVehicleIcon(reservation.voyage.vehicle.type))
                                .foregroundColor(.blue)
                            Image(systemName: "arrow.right")
                                .foregroundColor(.gray)
                            Text(reservation.voyage.arrivalPoint)
                                .font(.title)
                        }
                        
                        HStack {
                            Text("Departure Date:")
                                .font(.headline)
                            Spacer()
                            Text(formatDate(reservation.voyage.departureDate))
                                .font(.headline)
                        }
                        
                        HStack {
                            Text("Arrival Date:")
                                .font(.headline)
                            Spacer()
                            Text(formatDate(reservation.voyage.arrivalDate))
                                .font(.headline)
                        }
                        
                        HStack {
                            Text("Vehicle:")
                                .font(.headline)
                            Spacer()
                            Text(reservation.voyage.vehicle.name)
                                .font(.headline)
                        }
                        
                        HStack {
                            Text("Reserved Seats:")
                                .font(.headline)
                            Spacer()
                            Text(reservation.seatNumbers.map(String.init).joined(separator: ", "))
                                .font(.headline)
                        }
                        
                        HStack {
                            Text("Total Price:")
                                .font(.headline)
                            Spacer()
                            Text("$\(reservation.totalPrice)")
                                .font(.headline)
                        }
             
                    }.padding(30)
                     
                    
                }
              else
                {
                  Text(validticket)
                      .font(.custom(Fonts.Font1, size: 22))
                      .frame(maxWidth: .infinity)
                      .padding(.vertical, 16)
                      .foregroundColor(validticket == "Valid Ticket" ? Color(Colors.ColorPrimary): Color(Colors.AccentDarkPink))
                      .padding(30)
                 if validticket == ""
                  {Image( "scanner")
                         .resizable()
                         .frame(width:350 ,height: 350)
                 }
                  Text("Scan the QR Code in the ticket to validate it!")
                  Button ("Scan"){
                      self.isPresentingScanner = true
                  }
                  .font(.custom(Fonts.Font1, size: 18))
                  .frame(maxWidth: .infinity)
                  .padding(.vertical, 16)
                  .foregroundColor(.white)
                  .background(Color(Colors.AccentDarkPink ))
                  .cornerRadius(40)
                  .padding(30)
              }
                 
        
 Spacer()
            }
  
        }.sheet(isPresented: $isPresentingScanner)
        {
            self.scannerSheet
        }
        
    }
}

struct ScannerPage_Previews: PreviewProvider {
    static var previews: some View {
        ScannerPage()
    }
}
