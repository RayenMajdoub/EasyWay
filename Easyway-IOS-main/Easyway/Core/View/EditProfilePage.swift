//
//  EditProfilePage.swift
//  Easyway
//
//  Created by Rayen Majdoub on 13/4/2023.
//

import SwiftUI

struct EditProfilePage: View {
    @Environment(\.dismiss) var dismiss
    @StateObject var editData: EditProfileModel = EditProfileModel()

    @State var showAlert = false
    @State var showlogin = false
    @State var showEdit = false
    @State var profilepic = "profilepic"
    @State var usernametext = ""
    @State private var index = 0
    let avatar_list = ["profilepic","profilepic1","profilepic2","profilepic3","profilepic4","profilepic5"]
    
    private let userDeafaults = UserDefaults.standard
    var body: some View {
        NavigationView{
            VStack  
            {
                ZStack
                {
                    
                    VStack
                    {
                        Text("Edit profile")
                            .font(.custom(Fonts.Font1, size: 50))
                            .frame(maxWidth: .infinity ,alignment: .center)
                            .foregroundColor(.white)
                            .padding(30)
                        Spacer()
                    }.frame(height: 220)
                        .frame(maxWidth: .infinity)
                        .background(Color(Colors.ColorPrimary))
         
                    Button(action:{
                        
                        if( index < 5)
                        {
                            index += 1
                        }else
                        {
                            index = 0
                        }
                       
                           profilepic = avatar_list[index]

                    }
                           ,label:{
                        
                        
                        
                        Image(profilepic)
                            .resizable()
                            .aspectRatio(contentMode: .fit)
                            .frame(height: 200)
                            .padding(.bottom,10)
                            .offset(y:70)
                        Text("Tap on the avatar to change it !")
                            .font(.custom(Fonts.Font1, size: 16))
                            .frame(maxWidth: .infinity ,alignment: .center)
                            .foregroundColor(.white)
                            .offset(y:70)
                    })
                }
                HStack{
                    Spacer()
                    Button(action:{
                        showAlert = true
                    },label: {
                        HStack{
                            Text("Save")
                                .foregroundColor(Color(Colors.ColorSecondary))
                            
                            Image(systemName: "checkmark")
                                .foregroundColor(Color(Colors.ColorSecondary)).frame(width: 35,height: 35,alignment: .leading)
                            
                        }
                    }).padding(.top,30)
                        .padding(.trailing,20)
                    
                }
                
                Text("Edit your information")
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
                    TextField(userDeafaults.string(forKey: "username")!,text:$editData.username)
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
                    SecureField("*********" ,text:$editData.password)
                        .font(.custom(Fonts.Font1, size: 18))
                        .frame(maxWidth: .infinity ,alignment: .leading)
                        .foregroundColor(.gray)
                    
                    
                }.padding(15)
                HStack
                {
                    Text("Confirm Password")
                        .font(.custom(Fonts.Font1, size: 18))
                        .frame(maxWidth: .infinity ,alignment: .leading)
                        .foregroundColor(Color(Colors.ColorSecondary))
                        .padding(.leading,30)
                    Spacer()
                    SecureField("*********" ,text:$editData.verifpassword)
                        .font(.custom(Fonts.Font1, size: 18))
                        .frame(maxWidth: .infinity ,alignment: .leading)
                        .foregroundColor(.gray)
                    
                    
                }.padding(15)
                HStack{
            Button(action:{
                dismiss()
            }, label: {
                Text("Back to profile")
                    .font(.custom(Fonts.Font1, size: 20))
                    .frame(maxWidth: .infinity ,alignment: .center)
                    .foregroundColor(Color(Colors.AccentDarkPink))
                  
            })
                .padding(.top , 30)
      
            Spacer()
        }.frame(maxWidth: .infinity)
                
                Spacer ()
            }  .alert(isPresented: $showAlert) {
                Alert(title: Text("Save changes "), message: Text("You are about to save new informations !"), primaryButton: .destructive(Text("OK").foregroundColor(.red))
                      {
                    UserDefaults.standard.set(profilepic, forKey: "profilepic")
                    editData.updateProfile(){ result in
                        switch result {
                        case .success(_):
                            showEdit = true
                        case .failure(let error):
                            
                            print("Error: \(error)")
                        }
                    }
                },secondaryButton: .cancel(Text("Cancel")))
            } .fullScreenCover(isPresented: $showEdit, content: {
                ProfilePage()
                     })
        }
    }
}
