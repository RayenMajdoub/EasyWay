//
//  TicketDetailTopView.swift
//  Easyway
//
//  Created by Rayen Majdoub on 16/4/2023.
//

import SwiftUI


struct TicketDetailTopView: View {
    
    let ticketInfo: Voyage
    
    var body: some View {
        HStack {
            VStack(alignment: .leading) {
                Text(ticketInfo.departurePoint)
                    .font(.custom("Futura-Medium", size: 20.0, relativeTo: .title3))
                Text(ticketInfo.departureDate)
                    .font(.caption2)
                    .fontWeight(.semibold)
                    .foregroundColor(.gray)
            }
            Spacer()
            VStack {
                HStack(spacing: .zero) {
                    Circle()
                        .stroke(lineWidth: 2.0)
                        .fill(Color.teal)
                        .frame(width: 6.0, height: 6.0)
                    Rectangle()
                        .fill(Color.teal)
                        .frame(height: 1.0)
                    Image(systemName: ticketInfo.vehicle.type)
                        .resizable()
                        .scaledToFit()
                        .frame(width: 24.0)
                        .foregroundColor(Color(Colors.AccentDarkPink))
                        .padding(.horizontal, 8.0)
                    Rectangle()
                        .fill(Color.teal)
                        .frame(height: 1.0)
                    Circle()
                        .stroke(lineWidth: 2.0)
                        .fill(Color.teal)
                        .frame(width: 6.0, height: 6.0)
                }
                Text(String(ticketInfo.distance))
                    .font(.caption)
                    .fontWeight(.semibold)
            }
            .offset(x: .zero, y: 4.0)
            Spacer()
            VStack(alignment: .trailing) {
                Text(ticketInfo.arrivalPoint)
                    .font(.custom("Futura-Medium", size: 20.0, relativeTo: .title3))
                Text(ticketInfo.arrivalDate)
                    .font(.caption2)
                    .fontWeight(.semibold)
                    .foregroundColor(.gray)
            }
        }
        .padding(.horizontal, 20.0)
        .padding(.top, 20.0)
        .padding(.bottom, 8.0)
        .frame(maxWidth: .infinity)
        .background(Color(UIColor.tertiarySystemBackground))
        .cornerRadius(12.0, corners: [.topLeft, .topRight])
    }
}

struct TicketDetailTopView_Previews: PreviewProvider {
    static var previews: some View {
        TicketDetailTopView(ticketInfo: sampleTicketData[0])
            .frame(height: 80.0)
            .previewLayout(PreviewLayout.sizeThatFits)
    }
}
