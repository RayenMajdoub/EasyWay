//
//  BookingPage.swift
//  Easyway
//
//  Created by Rayen Majdoub on 17/4/2023.
//

import SwiftUI
import StripePaymentSheet

struct BookingPage: View {
    var gridformation :SeatFormation
    var voyage : Voyage
    let seats :[Bool]
    let totalcolumns :Int
    @Environment(\.dismiss) var dismiss
@State var showpaymentsheet = false
    init(gridformation: SeatFormation  ,voyage : Voyage) {
        self.gridformation = gridformation
        self.voyage = voyage
        self.seats =   createMatriceViewItemTypes(seatFormation: gridformation)
        self.totalcolumns = gridformation.emptyRows.count+gridformation.fullRows
        print(self.totalcolumns)
        
    }
    @State var selectedSeats = Set<Int>()

    var body: some View {
        VStack{
            Spacer()
            HStack{
                Button(action:{
                    dismiss()
                }, label: {
                    Image(systemName: "chevron.left")
                        .foregroundColor(Color(Colors.ColorSecondary)).frame(width: 30,height: 30,alignment: .leading)
                 
                }).padding(.leading,30)
                
                    
                
                Text ("Select Seats")
                    .font(.title)
                    .fontWeight(.semibold)
                    .foregroundColor(.black)
                Spacer()
            }
       
            //Curv
                let columns = Array (repeating: GridItem(.fixed(35) , spacing: 10), count: totalcolumns)
            ScrollView(.vertical,showsIndicators: false){
                LazyVGrid(columns: columns, spacing: 13, content: {
             
                    ForEach(seats.indices, id: \.self) { index in
                        SeatView(
                            index: index,
                            opacity: seats[index],
                            selected: self.selectedSeats.contains(index),
                            available: voyage.available[index]
                        )
                        .onTapGesture {
                             print("im in tap")
                            if self.selectedSeats.contains(index) {
                                self.selectedSeats.remove(index)
                            } else {
                                if voyage.available[index]
                                {
                                    self.selectedSeats.insert(index)
                                }
                               else
                                {
                                   print("already booked")
                               }
                            }
                        }
                    }

                }).padding()
                
            }
                .frame(height: 400)
                .background(.white)
                .cornerRadius(30)
         
        
       
            HStack
            {        Spacer()
                SeatView(index:10,opacity: true ,selected: false, available: true)
                    .padding(10)
                Text("Available")
                    .font(.custom("Futura-Medium", size: 10, relativeTo: .headline))
                Spacer()

                SeatView(index:10,opacity: true,selected: true, available: true).padding(10)
                Text("Selected")
                    .font(.custom("Futura-Medium", size: 10, relativeTo: .headline))
                Spacer()
                SeatView(index:10,opacity: true ,selected: false,available:  false)
                    .padding(10)
                Text("Booked")
                    .font(.custom("Futura-Medium", size: 10, relativeTo: .headline))
                Spacer()
            }
           Divider()
            HStack{
                VStack{
                    Text("Total Price:")
                        .font(.custom("Futura-Medium", size: 14, relativeTo: .headline)).padding(10)
                    Text("\(String(format: "%.2f", (Double(selectedSeats.count) * voyage.economySeatPrice))) TND")
                        .font(.custom("Futura-Medium", size: 16, relativeTo: .headline)).padding(10)
                }
                Spacer()
                VStack{
                Text("Number of seats:")
                    .font(.custom("Futura-Medium", size: 14, relativeTo: .headline)).padding(10)
                Text("\(selectedSeats.description)")
                    .font(.custom("Futura-Medium", size: 16, relativeTo: .headline)).padding(10) }
            }
            .padding()
         
           if(selectedSeats.count * Int(voyage.economySeatPrice) > 1)
            {
        
               CheckoutView(voyage: voyage.id  ,selectedSeats: $selectedSeats,seatprice:Int(voyage.economySeatPrice))

           }
     
            

            
            Spacer()
        }.background(.white)
        }
    
    struct CheckoutView: View {
      @StateObject var model = MyBackendModel()
         var voyage:String
        @Binding var selectedSeats : Set<Int>
        @State  var selecting = true
      @State  var reservationid:String = ""
        @State var showDetailReservation = false
        let seatprice :Int
        var body: some View {
            VStack {
                if selecting
                {
                    Button(action: {
             
                        selecting = false
                        model.preparePaymentSheet(amount: (selectedSeats.count * seatprice),selectedSeats:selectedSeats,id_voyage:voyage)
                   
                    }, label: {
                        
                        Text("Select")
                            .font(.custom(Fonts.Font1, size: 18))
                            .frame(maxWidth: .infinity)
                            .padding(.vertical, 16)
                            .foregroundColor(.white)
                            .background(Color(Colors.AccentDarkPink ))
                            .cornerRadius(40)
                            .padding(30)
                    })
                           }
                           
                else
                {
                    if let paymentSheet = model.paymentSheet {
                        PaymentSheet.PaymentButton(
                            paymentSheet: paymentSheet,
                            onCompletion: model.onPaymentCompletion
                        ) {
                            Text("Purchase")
                                .font(.custom(Fonts.Font1, size: 18))
                                .frame(maxWidth: .infinity)
                                .padding(.vertical, 16)
                                .foregroundColor(.white)
                                .background(Color(Colors.AccentDarkPink ))
                                .cornerRadius(40)
                                .padding(30)
                            
                        }
                        
                        
                    }
                    else {
                        Text("Loading…")
                            .font(.custom(Fonts.Font1, size: 18))
                            .frame(maxWidth: .infinity)
                            .padding(.vertical, 16)
                            .foregroundColor(.white)
                            .background(Color(Colors.AccentDarkPink ))
                            .cornerRadius(40)
                            .padding(30)
       
                    }
             
              
                }
                
         
            

            }.onChange(of: model.reservationId, perform: {new in
                showDetailReservation = true
            })
            .fullScreenCover(isPresented: $showDetailReservation, content: {
                DetailsReservation(reservationid: model.reservationId)
            })
       
      }
    }

}



struct SeatView: View {
    var index: Int
    var opacity : Bool
    var selected : Bool
    var available : Bool
    var body: some View{
        ZStack{
            RoundedRectangle (cornerRadius: 10)
                .stroke(available == false ? Color (Colors.ColorPrimary):selected == true ? Color (Colors.AccentDarkPink) : Color (Colors.ColorSecondary), lineWidth: 2)
                .frame (height: opacity == true ? 35 : 10)
                .opacity(opacity == true ? 1 : 0	)
            Text(String(index))
                .opacity(opacity == true ? 1 : 0    )
                .foregroundColor(Color.secondary)
                .font(.custom(Fonts.Font1, size: 12))
        }
        
    }}

func createMatriceViewItemTypes(seatFormation: SeatFormation) -> [Bool] {
    // ALL COLLUMS AND ALL ROWS
    let cols = seatFormation.fullRows + seatFormation.emptyRows.count
    let rows = seatFormation.fullLines + seatFormation.emptyLines.count
    // CREATION D UNE MATRICE SELON LES BESOIN
    var matriceViewType = [[Bool]](repeating: [Bool](repeating: false, count: cols), count: rows)
    for i in 0..<rows {
        var bools = [Bool]()
        for j in 0..<cols {
            let result = seatFormation.emptyLines.contains(i)
            let result1 = seatFormation.emptyRows.contains(j)
            if result || result1 {
                bools.append(false)
            } else {
                bools.append(true)
            }
        }
        matriceViewType[i] = bools
    }
    matriceViewType.removeFirst()
    let lastResult = matriceViewType.flatMap { $0 }
    print(lastResult.count)
    print(lastResult)
    return lastResult
    
}



