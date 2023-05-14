//
//  TicketListView.swift
//  FlightTicketBrowser
//
//  Created by Takuya Aso on 2021/12/26.
//

import SwiftUI

struct TicketListPage: View {
    @Binding var from:String
    @Binding var to:String
    @StateObject public var viewModel = VoyageModel()
    @Environment(\.dismiss) var dismiss

        func dateTransformation(voyage: Voyage) -> Voyage {
            var v = voyage
            let sdf = DateFormatter()
            sdf.dateFormat = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"
            if let date = sdf.date(from: voyage.departureDate) {
                let g = DateFormatter()
                g.dateFormat = "dd MMM"
                v.departureDate = g.string(from: date)
            }
            return v
        }



    var body: some View {
        NavigationView {
            VStack{
                HStack{
                    Button(action:{
                        dismiss()
                    }, label: {
                        Image(systemName: "chevron.left")
                            .foregroundColor(Color(Colors.ColorSecondary)).frame(width: 30,height: 30,alignment: .leading)
                     
                    })       .padding(.leading,30)
                        .padding(.top , 20)
                    Spacer()
                }
              
                .frame(maxWidth: .infinity)
                .frame(alignment: .leading)
                MyHorizontalScrollView(from: from, to: to, viewModel: viewModel)
                List {
                    Section {
                        ForEach(viewModel.ticketList.indices, id: \.self) { index in
                            NavigationLink( destination: TicketDetailView(ticketInfo: viewModel.ticketList[index])){
                                TicketListRowView(ticketInfo: dateTransformation(voyage:viewModel.ticketList[index]) )
                                
                            }
                        }
                    }
                    
                }
                .onAppear {
                    viewModel.GetVoyages(type: "all", departurePoint: from, arrivalPoint: to)
                    { result in
                        switch result {
                        case .success(_):
                            //showingDrawerView = true
                            print(result)
                        case .failure(let error):
                            print("Error: \(error)")
                        }
                        
                    }
                }
            }
            
        }
    }
}

struct TicketListRowView: View {
    
    let ticketInfo: Voyage
    
    var body: some View {
        HStack {
            VStack(alignment: .leading) {
                Text(ticketInfo.departureDate)
                    .font(.custom("Futura-Medium", size: 15.0, relativeTo: .subheadline))
                Text("DATE")
                    .font(.caption2)
                    .fontWeight(.semibold)
                    .foregroundColor(.gray)
            }
            .frame(width: 40.0, alignment: .leading)
            Divider()
            VStack(alignment: .leading) {
                Text(ticketInfo.departurePoint)
                    .font(.custom("Futura-Medium", size: 20.0, relativeTo: .title3))
                Text("Departure")
                    .font(.caption2)
                    .fontWeight(.semibold)
                    .foregroundColor(.gray)
            }
            .padding(.leading, 8.0)
            Spacer()
            Image(systemName: ticketInfo.vehicle.type)
                .resizable()
                .scaledToFit()
                .frame(width: 20.0)
                .foregroundColor(Color(Colors.Blue))
            Spacer()
            VStack(alignment: .trailing) {
                Text(ticketInfo.arrivalPoint)
                    .font(.custom("Futura-Medium", size: 20.0, relativeTo: .title3))
                Text("Arrival")
                    .font(.caption2)
                    .fontWeight(.semibold)
                    .foregroundColor(.gray)
            }
        }
        .frame(maxWidth: .infinity)
        .padding(.vertical, 8.0)
        .onTapGesture {
          
        }
    }
    
}
struct ButtonWithIcon: View {
    let imageName: String
    let isSelected: Bool
    
    var body: some View {
        Image(systemName: imageName)
            .font(.system(size: 24, weight: .medium))
            .foregroundColor(isSelected ? .white : Color(Colors.ColorPrimary))
            .frame(width: 50, height: 50)
            .background(isSelected ? Color(Colors.ColorPrimary): Color.white)
    }
}

struct MyHorizontalScrollView: View {
    let from :String
    let to:String
    let viewModel : VoyageModel
    let icons = ["square.grid.2x2", "bus", "tram.fill", "car", "bicycle",]
    let types = ["all","bus","train","taxi","bike"]
    @State var selectedIndex = 0
    
    var body: some View {
        ScrollView(.horizontal, showsIndicators: false) {
            HStack(spacing: 8) {
                ForEach(0..<icons.count) { index in
                    Button(action: {
                        selectedIndex = index
                        viewModel.GetVoyages(type: types[index], departurePoint: from, arrivalPoint: to)
                        { result in
                            switch result {
                            case .success(_):
                                //showingDrawerView = true
                                print(result)
                            case .failure(let error):
                                print("Error: \(error)")
                            }
                            
                        }
                    }) {
                        ButtonWithIcon(imageName: icons[index], isSelected: selectedIndex == index)
                    }.cornerRadius(10)
                        .padding()
                }
            }
        }
        .padding(10)
    }
}





