//
//  TicketDetailView.swift
//  Easyway
//
//  Created by Rayen Majdoub on 16/4/2023.
//

import SwiftUI


struct TicketDetailView: View {
    
    let ticketInfo: Voyage
    @State private var isShowModal = false
    @State private var showBookingpage = false
   // @State private var gridformation = SeatFormation()
    @StateObject public var viewModel = VoyageModel()

    
    var body: some View {
        
        ScrollView {
            VStack(spacing: .zero) {
                Spacer()
                TicketDetailTopView(ticketInfo: ticketInfo)
                ZStack {
                    LREdgeCutShapeView()
                        .fill(Color(UIColor.tertiarySystemBackground), style: FillStyle(eoFill: false, antialiased: false))
                        .frame(maxWidth: .infinity)
                        .frame(height: 24.0)
                    LineShape()
                        .stroke(Color(uiColor: .lightGray), style: StrokeStyle(lineWidth: 1.0, dash: [5]))
                        .frame(height: 1.0)
                        .padding(.horizontal, 20.0)
                }
                TicketDetailMainView(ticketInfo: ticketInfo)
                Button(action:{
                    viewModel.getGridFormation(id: ticketInfo.vehicle.gridFormationId)
                    { result in
                            switch result {
                            case .success(_):
                           // gridformation = try result.get()
                                    showBookingpage = true
                                print(viewModel.gridformation)
                                print(result)
                            case .failure(let error):
                                print("Error: \(error)")
                            }
                            
                        
                    }
                },label: {Text("Book Now!")
                        .font(.custom(Fonts.Font1, size: 20))
                        .frame(maxWidth: .infinity)
                        .padding(.vertical, 18)
                        .foregroundColor(.white)
                        .background(Color(Colors.AccentDarkPink ))
                        .cornerRadius(40)
                        .padding(30)
                })
            }
            .padding(.all, 20.0)
        }
        .background(Color(UIColor.systemGroupedBackground))
        .navigationBarTitleDisplayMode(.inline)
        .navigationTitle("Ticket Info")
        .toolbar {
            ToolbarItem(placement: .navigationBarTrailing) {
                Button {
                    isShowModal.toggle()
                } label: {
                    VStack(spacing: .zero) {
                        Image(systemName: "qrcode")
                            .resizable()
                            .scaledToFit()
                        Text("PASS")
                            .font(.system(size: 10.0))
                            .fontWeight(.semibold)
                    }
                    .foregroundColor(.primary)
                }
            }
        }
        .fullScreenCover(isPresented: $isShowModal) {
           // BoardingPassView(ticketInfo: ticketInfo)
        }
        .fullScreenCover(isPresented: $showBookingpage) {
    
            BookingPage(gridformation: viewModel.gridformation , voyage: ticketInfo)
        }
    }
}

struct TicketDetailView_Previews: PreviewProvider {
    static var previews: some View {
        TicketDetailView(ticketInfo: sampleTicketData[0])
    }
}
