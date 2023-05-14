//
//  ForgetPage.swift
//  Easyway
//
//  Created by Rayen Majdoub on 16/3/2023.
//

import SwiftUI

struct ForgetPage: View {
    @StateObject var forgetData: ForgetPasswordModel = ForgetPasswordModel()
    @State private var showingNewPassword = false
    @State private var Code :String = ""
    var body: some View {
        VStack{
     
            VStack{
                Text("Forget \nPassword")
                    .font(.custom(Fonts.Font1, size: 55))
                    .fontWeight(.bold)
                    .foregroundColor(.white)
                    .frame(maxWidth: .infinity , alignment: .leading)
                    .frame(height: 220)
                    .padding(30)
                
            }
            ScrollView(.vertical,showsIndicators: false)
            {
                VStack(spacing: 15)
                {
                    Text("Send Code")
                        .font(.custom(Fonts.Font1, size: 22))
                        .frame(maxWidth:  .infinity )
                        .padding(.top,20.0)
                    HStack{
                        
                        TextField(
                            "Enter your Email...",
                            text: $forgetData.email
                        ).padding()
                            .background()
                            .cornerRadius(30)
                            .padding(.top,20)
                    }
                    
                    Button(action:{
                        
                        forgetData.sendCode() { result in
                            switch result {
                            case .success(_):
                                
                                Code = try!result.get()
                                showingNewPassword = true
                                
                            case .failure(let error):
                                print("Error: \(error)")
                            }
                            
                        }
                        
                    },label:
                            {
                        Text("Send")
                            .font(.custom(Fonts.Font1, size: 20))
                            .frame(maxWidth:  .infinity )
                            .padding(.vertical,18)
                            .foregroundColor(.white)
                            .background(Color(Colors.ColorPrimary))
                            .cornerRadius(20)
                            .padding()
                        
                    })
                    .padding(20)
              
                    
                    
                }
                .frame(maxWidth: .infinity,maxHeight: .infinity)
                .background(Color(Colors.ColorLightGrey))
                .cornerRadius(30)
                
            }.frame(maxWidth: .infinity ,maxHeight: .infinity)
    
                .fullScreenCover(isPresented: $showingNewPassword, content: {
                    CodeVerifPage(code: $Code,email:$forgetData.email)
                })
        }            .background(Color(Colors.ColorSecondary))
    }
}
    
    

