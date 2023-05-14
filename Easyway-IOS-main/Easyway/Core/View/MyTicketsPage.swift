//
//  MyTicketsPage.swift
//  Easyway
//
//  Created by Rayen Majdoub on 6/5/2023.
//

import SwiftUI
import CoreImage.CIFilter
import CoreImage.CIFilterBuiltins
struct MyTicketsPage: View {
    @StateObject public var reservationModel = ReservationModel()
    @Environment(\.dismiss) var dismiss
    @State var showdetails = false
    @State var tapedindex :Int?
    @State var ticketList: [Reservation]?

        func dateTransformation(reservation: Reservation) -> Reservation {
            var v = reservation
            let sdf = DateFormatter()
            sdf.dateFormat = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"
            if let date = sdf.date(from: reservation.voyage.departureDate) {
                let g = DateFormatter()
                g.dateFormat = "dd MMM"
                v.voyage.departureDate = g.string(from: date)
            }
            return v
        }

    var body: some View {
        NavigationView{
            VStack{
                Text("My Tickets")
                 .font(.custom(Fonts.Font1, size: 50))
                 .frame(maxWidth: .infinity ,alignment: .center)
                 .foregroundColor(Color(Colors.ColorSecondary))
                 .padding(30)
                if let reservation = ticketList{ List {
                    Section {
                        ForEach(ticketList!.indices, id: \.self) { index in
                            
                            NavigationLink( destination:  DetailsReservation(reservationid:  ticketList![index].id ))
                            {
                                TicketListRowView2(ticketInfo: dateTransformation(reservation:ticketList![index]) )
                            }
                            }
                            
                        }
                    }
                }
                else {
                    Text("Loading...")
                }
                Spacer()
            }
            .onAppear {
                reservationModel.getMyTickets()
                { result in
                    switch result {
                    case .success(_):
                        //showingDrawerView = true
                        self.ticketList = try!result.get()
                        print(result)
                    case .failure(let error):
                        self
                            .ticketList = nil
                        print("Error: \(error)")
                    }
                    
                }
            }
        }
            
            
        }
    struct TicketListRowView2: View {
        
        let ticketInfo: Reservation
        
        var body: some View {
            HStack {
                VStack(alignment: .leading) {
                    Text(ticketInfo.voyage.departureDate)
                        .font(.custom("Futura-Medium", size: 15.0, relativeTo: .subheadline))
                    Text("DATE")
                        .font(.caption2)
                        .fontWeight(.semibold)
                        .foregroundColor(.gray)
                }
                .frame(width: 40.0, alignment: .leading)
                Divider()
                VStack(alignment: .leading) {
                    Text(ticketInfo.voyage.departurePoint)
                        .font(.custom("Futura-Medium", size: 20.0, relativeTo: .title3))
                    Text("Departure")
                        .font(.caption2)
                        .fontWeight(.semibold)
                        .foregroundColor(.gray)
                }
                .padding(.leading, 8.0)
                Spacer()
                Image(systemName: ticketInfo.voyage.vehicle.type)
                    .resizable()
                    .scaledToFit()
                    .frame(width: 20.0)
                    .foregroundColor(Color(Colors.Blue))
                Spacer()
                VStack(alignment: .trailing) {
                    Text(ticketInfo.voyage.arrivalPoint)
                        .font(.custom("Futura-Medium", size: 20.0, relativeTo: .title3))
                    Text("Arrival")
                        .font(.caption2)
                        .fontWeight(.semibold)
                        .foregroundColor(.gray)
                    
                }
            }
            .frame(maxWidth: .infinity)
            .padding(.vertical, 8.0)
       
        }
    }


    
}


