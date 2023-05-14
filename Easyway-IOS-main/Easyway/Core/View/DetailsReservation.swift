//
//  DetailsReservation.swift
//  Easyway
//
//  Created by Rayen Majdoub on 6/5/2023.
//

import SwiftUI
import CoreImage.CIFilter
import CoreImage.CIFilterBuiltins
struct DetailsReservation: View {
    @StateObject var resModel = ReservationModel()
    @State var reservation: Reservation?
    var reservationid: String
    let context = CIContext()
    let filter = CIFilter.qrCodeGenerator()
    @State var showHome = false
    @State var showMytickets = false
    

    var body: some View {
        VStack{
            Spacer()
            Text("My Ticket")
             .font(.custom(Fonts.Font1, size: 50))
             .frame(maxWidth: .infinity ,alignment: .center)
             .foregroundColor(Color(Colors.ColorSecondary))
             .padding(30)
            VStack(spacing: 20) {
            if let reservation = reservation {
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
                
                VStack {
                    Text("QR Code:")
                        .font(.headline)
                    Image(uiImage: generateQRCode(from: reservation.id))
                        .interpolation(.none)
                        .resizable()
                        .scaledToFit()
                        .frame(width: 180, height: 180)
                }
             
            } else {
                Text("Loading...")
            }
        }
        .padding()
        .background(.white)
        .cornerRadius(30)
        .shadow(radius: 10)
        .onAppear{
            resModel.getReservation(idRes: reservationid){
                result in
                switch result {
                case .success(let res):
                    self.reservation = res
                case .failure(let error):
                    self.reservation = nil
                    print(error)
                }
            }
            
        }
            HStack{  Button(action: {
                showHome = true
            }, label: {Text("Home").font(.custom(Fonts.Font1, size: 16))
                    .frame(maxWidth: .infinity)
                    .padding(.vertical, 16)
                    .foregroundColor(.white)
                    .background(Color(Colors.AccentDarkPink ))
                    .cornerRadius(40)
                    .padding(30)})
                Button(action: {
                    showMytickets = true
                }, label: {Text("My tickets").font(.custom(Fonts.Font1, size: 16))
                        .frame(maxWidth: .infinity)
                        .padding(.vertical, 16)
                        .foregroundColor(.white)
                        .background(Color(Colors.ColorSecondary ))
                        .cornerRadius(40)
                        .padding(30)})      }  }
        .padding(30)
        Spacer()
        
        .background(Color(Colors.ColorLightGrey))
        .edgesIgnoringSafeArea(.all)
        .fullScreenCover(isPresented: $showHome, content: {DrawerView()})
        .fullScreenCover(isPresented: $showMytickets, content: {MyTicketsPage()})

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

    private func formatDate(_ dateString: String) -> String {
        let formatter = DateFormatter()
        formatter.dateFormat = "yyyy-MM-dd'T'HH:mm:ss.SSSZ"
        let date = formatter.date(from: dateString)!
        formatter.dateFormat = "MMM d, yyyy h:mm a"
        return formatter.string(from: date)
    }

    private func generateQRCode(from string: String) -> UIImage {
            let data = Data(string.utf8)
            filter.setValue(data, forKey: "inputMessage")


            if let qrCodeImage = filter.outputImage {
                if let qrCodeCGImage = context.createCGImage(qrCodeImage, from: qrCodeImage.extent) {
                    return UIImage(cgImage: qrCodeCGImage)
                }
            }


            return UIImage(systemName: "xmark") ?? UIImage()
        }
}









