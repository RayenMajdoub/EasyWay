//
//  NewPasswordPage.swift
//  Easyway
//
//  Created by Rayen Majdoub on 16/3/2023.
//

import SwiftUI

struct NewPasswordPage: View {
    @StateObject var forgetData: ForgetPasswordModel = ForgetPasswordModel()
    @State private var showingNewPassword = false

    @Binding var email:String

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
                    Text("Reset Password")
                        .font(.custom(Fonts.Font1, size: 22))
                        .frame(maxWidth:  .infinity )
                        .padding(.top,20.0)
                    HStack{
              
                      TextField(
                        "Enter new password...",
                        text: $forgetData.password
                      ).padding()
                            .background()
                            .cornerRadius(30)
                            .padding(.top,20)
                    }
                    HStack{
              
                      TextField(
                        "Verify new password...",
                        text: $forgetData.verifpassword
                      ).padding()
                            .background()
                            .cornerRadius(30)
                            .padding(.top,20)
                    }
                   
                    Button(
                        action:{
                            forgetData.changePassword(email: email)
                            { result in
                                switch result {
                                case .success(_):
                                    showingNewPassword = true
                                case .failure(let error):
                                    print("Error: \(error)")
                                }
                            }
                    },label:
                            {
                        Text("Save")
                            .font(.custom(Fonts.Font1, size: 20))
                            .frame(maxWidth:  .infinity )
                            .padding(.vertical,18)
                            .foregroundColor(.white)
                            .background(Color(Colors.ColorPrimary))
                            .cornerRadius(20)
                            .padding()
                    })
                }
                .padding(20)
                .fullScreenCover(isPresented: $showingNewPassword, content: {
                    LoginPage()
                        })
                
                
            }
            .frame(maxWidth: .infinity,maxHeight: .infinity)
            .background(Color(Colors.ColorLightGrey))
            .cornerRadius(30)
            
              
        }.frame(maxWidth: .infinity ,maxHeight: .infinity)
                   .background(Color(Colors.ColorSecondary))
           }
}


