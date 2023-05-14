//
//  TicketDetailMainView.swift
//  Easyway
//
//  Created by Rayen Majdoub on 16/4/2023.
//

import SwiftUI


struct TicketDetailMainView: View {
    
    let ticketInfo: Voyage
    
    var body: some View {
        VStack(spacing: 16.0) {
            HStack(spacing: .zero) {
                BlockView(key: "Vehicle", value: ticketInfo.vehicle.name, rows: 3)
                Divider()
                BlockView(key: "Distance", value: String(ticketInfo.distance), rows: 3)
                Divider()
                BlockView(key: "Type", value: ticketInfo.vehicle.type, rows: 3)
            }
            Divider()
            HStack(spacing: .zero) {
                BlockView(key: "DATE", value: ticketInfo.departureDate, rows: 3)
                Divider()
                BlockView(key: "DEPARTURE TIME", value: ticketInfo.departureDate, rows: 3)
                Divider()
                BlockView(key: "Arrival Point", value: ticketInfo.arrivalPoint, rows: 3)
            }
            Divider()
            HStack(spacing: .zero) {
                BlockView(key: "", value: "Economy Price", rows: 2)
                Divider()
                BlockView(key: "PRICE", value: String(ticketInfo.economySeatPrice), rows: 2)
            }
            Divider()
            HStack(spacing: .zero) {
                BlockView(key: "", value: "Business Price", rows: 2)
                Divider()
                BlockView(key: "PRICE", value: String(ticketInfo.businessSeatPrice), rows: 2)
            }
            Divider()
            HStack(spacing: .zero) {
                BlockView(key: "", value: "First class Price", rows: 2)
                Divider()
                BlockView(key: "PRICE", value: String(ticketInfo.firstClassSeatPrice), rows: 2)
            }
            // Pattern 1
            //Image(uiImage: UIImage(data: BarcodeGenerator.generate(barcodeStr: ticketInfo.eTicketNumber + ticketInfo.bookingCode)!)!)
            // Pattern 2 (IMO: Better way)
          /*  Image(uiImage: BarcodeGenerator.generateNew(barcodeStr: ticketInfo.eTicketNumber + ticketInfo.bookingCode)!)
                .resizable()
                .interpolation(.none)
                .scaledToFill()
                .frame(height: 24.0)
                .padding(.vertical, 20.0)*/
        }
        .padding(.horizontal, 20.0)
        .padding(.top, 12.0)
        .padding(.bottom, 20.0)
        .frame(maxWidth: .infinity)
        .background(Color(UIColor.tertiarySystemBackground))
        .cornerRadius(12.0, corners: [.bottomLeft, .bottomRight])
    }
}

struct TicketDetailMainView_Previews: PreviewProvider {
    static var previews: some View {
        TicketDetailMainView(ticketInfo: sampleTicketData[0])
    }
}
