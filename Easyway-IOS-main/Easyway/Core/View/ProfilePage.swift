//
//  ProfilePage.swift
//  Easyway
//
//  Created by Rayen Majdoub on 6/4/2023.
//

import SwiftUI

struct ProfilePage: View {
    @Environment(\.dismiss) var dismiss
    @State var showAlert = false
    @State var showlogin = false
    @State var showeditProfile = false

    private let userDeafaults = UserDefaults.standard
    var body: some View {
        NavigationView{
      VStack
            {
                ZStack
                {
              
                    VStack
                    {
                           Text("My profile")
                            .font(.custom(Fonts.Font1, size: 50))
                            .frame(maxWidth: .infinity ,alignment: .center)
                            .foregroundColor(.white)
                            .padding(30)
                        Spacer()
                                
                    }.frame(height: 220)
                        .frame(maxWidth: .infinity)
                        .background(Color(Colors.ColorPrimary))
                    
                    Image(userDeafaults.string(forKey: "profilepic")!)
                              .resizable()
                              .aspectRatio(contentMode: .fit)
                              .frame(height: 200)
                              
                              .padding(.bottom,10)
                              .offset(y:70)
                    
                }
                HStack{
                    Spacer()
                    Button(action:{
                            showeditProfile = true
                    },label: {
                        HStack{
                            Text("Edit")
                                .foregroundColor(Color(Colors.ColorSecondary))
                            
                            Image(systemName: "pencil")
                                .foregroundColor(Color(Colors.ColorSecondary)).frame(width: 35,height: 35,alignment: .leading)
                            
                        }
                    }).padding(.top,30)
                        .padding(.trailing,20)
                }.fullScreenCover(isPresented: $showeditProfile, content: {
                    EditProfilePage()
                         })
       
                    Text("Your information")
                    .font(.custom(Fonts.Font1, size: 26))
                    .frame(maxWidth: .infinity ,alignment: .leading)
                    .foregroundColor(Color(Colors.ColorSecondary))
                    .padding(30)
                HStack
                {
                    Text("Username")
                    .font(.custom(Fonts.Font1, size: 18))
                    .frame(maxWidth: .infinity ,alignment: .leading)
                    .foregroundColor(Color(Colors.ColorSecondary))
                    .padding(.leading,30)

                    Spacer()
                    Text(userDeafaults.string(forKey: "username")!)
                    .font(.custom(Fonts.Font1, size: 16))
                    .frame(maxWidth: .infinity ,alignment: .leading)
                    .foregroundColor(.gray)
            
                
                }.padding(15)
            
                HStack
                {
                    Text("Email")
                    .font(.custom(Fonts.Font1, size: 18))
                    .frame(maxWidth: .infinity ,alignment: .leading)
                    .foregroundColor(Color(Colors.ColorSecondary))
                    .padding(.leading,30)
                    Spacer()
                    Text(userDeafaults.string(forKey: "email")!)
                    .font(.custom(Fonts.Font1, size: 16))
                    .frame(maxWidth: .infinity ,alignment: .leading)
                    .foregroundColor(.gray)
                
                }.padding(15)
  
                HStack
                {
                    Text("Password")
                    .font(.custom(Fonts.Font1, size: 18))
                    .frame(maxWidth: .infinity ,alignment: .leading)
                    .foregroundColor(Color(Colors.ColorSecondary))
                    .padding(.leading,30)
                    Spacer()
                    Text("*********")
                    .font(.custom(Fonts.Font1, size: 18))
                    .frame(maxWidth: .infinity ,alignment: .leading)
                    .foregroundColor(.gray)
      
                
                }.padding(15)
                Divider()
                Button(action:{
                    
                },label: {
                    HStack{
                        Image(systemName: "questionmark.circle")
                            .foregroundColor(Color(Colors.ColorSecondary)).frame(width: 35,height: 35,alignment: .leading)
                        
                        Text("Support")
                            .foregroundColor(Color(Colors.ColorSecondary))
                        
                 Spacer()
                    }
                }).padding(.top,30)
                    .padding(.leading,20)
                Button(action:{
                    showAlert = true
    
                },label: {
                    HStack{
                        Image(systemName: "power")
                            .foregroundColor(Color(Colors.AccentDarkPink)).frame(width: 35,height: 35,alignment: .leading)
                        
                        Text("Logout")
                            .foregroundColor(Color(Colors.AccentDarkPink))
                        Spacer()
                 
                    }
                }).padding(.top,10)
                .padding(.leading,20)
                       Spacer()
            } .alert(isPresented: $showAlert) {
                Alert(title: Text("Logout"), message: Text("You are about to log out !!"), primaryButton: .destructive(Text("OK").foregroundColor(.red))    
                      {
                    let defaults = UserDefaults.standard
                    defaults.removeObject(forKey: "jwtToken")
                    showlogin = true
                },secondaryButton: .cancel(Text("Cancel")))
            } .fullScreenCover(isPresented: $showlogin, content: {
                LoginPage()
                     })
        }}
}


