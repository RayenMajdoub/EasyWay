//
//  GetStarted.swift
//  Easyway
//
//  Created by Rayen Majdoub on 15/3/2023.
//

import SwiftUI

struct GetStarted: View {
    @State private var getStartedState : Bool = false
    @Environment(\.dismiss) var dismiss

    var body: some View {
        NavigationView{
            VStack{
                Image("logo")
                    .resizable()
                    .frame(width: 320, height: 320)
                    .offset(y:-30)
                Image("getStarted")
                    .resizable()
                    .frame(width: 320, height: 320)
                    .offset(x:-40,y:-120)
                
                Button(
                    action: {
                        getStartedState = true 
                        dismiss()
                    }, label:
                {
                    Text("Get started")
                        .font(.custom(Fonts.Font1,size:18))
                        .fontWeight(.semibold)
                        .padding(.vertical,20)
                        .frame(maxWidth: .infinity)
                        .shadow(color: .white.opacity(0.2), radius: 5,x:5,y:5)
                        .background( Color(Colors.ColorSecondary))
                        .foregroundColor( .white)
                        .cornerRadius(10)
                    
                })
                .fullScreenCover(isPresented: $getStartedState, content: {
                    LoginPage()
                        })
                
                .padding(.horizontal,30)
                .offset(y:-60)
                
                .frame(maxWidth: .infinity , maxHeight: .infinity )
                .background(
                    Color(.white)
                )
                
            }
        }
    }
    
    
    struct GetStarted_Previews: PreviewProvider {
        static var previews: some View {
            GetStarted()
        }
    }
}
