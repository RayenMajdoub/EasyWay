//
//  LoginPage.swift
//  Easyway
//
//  Created by Rayen Majdoub on 15/3/2023.
//

import SwiftUI
import ExytePopupView
struct LoginPage: View {
    @StateObject var loginData: LoginModel = LoginModel()
    @State private var showingDrawerView = false
    @State var showingPopup = false
    @State var closeRetry = false
    @State var emailError = ""
    @State var passwordError = ""
    @State var choosePopupView :Int = 2
    @Environment(\.dismiss) var dismiss

    var body: some View {
        NavigationView{
 VStack{
            VStack{
                Text("Welcome \nback")
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
             Text("Login")
                 .font(.custom(Fonts.Font1, size: 22))
                 .frame(maxWidth:  .infinity )
                 .padding(.top,20.0)
             HStack{
                 
                 TextField(
                    "Enter your Email...",
                    text: $loginData.email
                 ).padding()
                     .background()
                     .cornerRadius(30)
                     .padding(.top,20)
             }
             HStack{
                 
                 SecureField(
                    "Enter your Password...",
                    text: $loginData.password
                 ).padding()
                     .background()
                     .cornerRadius(30)
                     .padding(.top,20)
                 
                 
             }
             HStack
             {
                 
                 NavigationLink(destination: ForgetPage())
                 {
                     Text("Forgot password?")
                         .font( .custom(Fonts.Font2, size: 14))
                         .foregroundColor(Color(Colors.ColorSecondary))
                     
                 }.padding(.top,8)
                     .frame(maxWidth: .infinity,alignment: .leading)
                 NavigationLink(destination: RegisterPage())
                 {
                     Text("Create an account")
                         .font( .custom(Fonts.Font2, size: 14))
                         .foregroundColor(Color(Colors.ColorSecondary))
                     
                 }.padding(.top,8)
                     .frame(maxWidth: .infinity,alignment: .trailing)
             }
             Button(
                action: {
                 
                    if verify(){
           
                        loginData.login() { result in
                            switch result {
                            case .success(_):
                                choosePopupView = 1
                                showingPopup = true
                            case .failure(let error):
                                showingPopup = true
                                emailError = "Email adress not valid"
                                passwordError = "Or wrong password"
                                print("Error: \(error)")
                            }
                        }
                    }
                    else{
                        passwordError = "Please enter your password"

                        showingPopup = true
                    }
                },label:
                 {
                     Text("Login")
                         .font(.custom(Fonts.Font1, size: 20))
                         .frame(maxWidth: .infinity)
                         .padding(.vertical, 18)
                         .foregroundColor(.white)
                         .background(Color(Colors.ColorPrimary))
                         .cornerRadius(20)
                         .padding()
                 } )
             
         }
         .fullScreenCover(isPresented: $showingDrawerView, content: {
             DrawerView()
                 })

         .padding(20)
         
         
     }
     .frame(maxWidth: .infinity,maxHeight: .infinity)
     .background(Color(Colors.ColorLightGrey))
     .cornerRadius(30)
       
 }.frame(maxWidth: .infinity ,maxHeight: .infinity)
            .background(Color(Colors.ColorSecondary))
            .popup(isPresented: $showingPopup ) {
                if choosePopupView == 1 {
                    
                    VStack{
                        Image("airplainimage")
                            .resizable()
                            .aspectRatio(contentMode: .fill)
                            .padding(20)
                            .frame(width: 250, height: 200)
                            .cornerRadius(30)
                        Text("Welcome to Easyway")
                            .font(.custom(Fonts.Font1, size: 22))
                            .frame(maxWidth:  .infinity )
                            .foregroundColor(Color(Colors.ColorSecondary))
                        Text("Book rides, track transportation and manage bookings all in one place \n\nEnjoy the convenience and simplicity. \nHappy travels!").padding()
                            .font(.custom(Fonts.Font1, size: 14))
                            .frame(maxWidth:  .infinity )
                            .multilineTextAlignment(.center)
                        Button( action:{
                            showingDrawerView = true
                            self.showingPopup = false
                       
                        },label:{
                            Text("Continue")
                                .font(.custom(Fonts.Font1, size: 20))
                                .frame(maxWidth: .infinity)
                                .padding(.vertical, 18)
                                .foregroundColor(.white)
                                .background(Color(Colors.AccentDarkPink))
                                .cornerRadius(20)
                                .padding()
                        })
                        
                    } .frame(width: 300, height: 500)
                        .background(.white)
                        .cornerRadius(30.0)
                        
                }
                else if choosePopupView == 2
                {
                    VStack{
                        Image("warning")
                            .resizable()
                            .aspectRatio(contentMode: .fill)
                            .padding(20)
                            .frame(width: 250, height: 200)
                            .cornerRadius(30)
                        Text("Failed to Login")
                            .font(.custom(Fonts.Font1, size: 22))
                            .frame(maxWidth:  .infinity )
                            .foregroundColor(Color(Colors.ColorSecondary))
                        Text("\(emailError)\n\(passwordError)")
                            .padding()
                            .font(.custom(Fonts.Font1, size: 14))
                            .frame(maxWidth:  .infinity )
                            .multilineTextAlignment(.center)
                        Button( action:{
                            self.showingPopup = false
                        },label:{
                            Text("Retry")
                                .font(.custom(Fonts.Font1, size: 20))
                                .frame(maxWidth: .infinity)
                                .padding(.vertical, 18)
                                .foregroundColor(.white)
                                .background(Color(Colors.AccentDarkPink))
                                .cornerRadius(20)
                                .padding()
                        })
                        
                    } .frame(width: 300, height: 500)
                        .background(.white)
                        .cornerRadius(30.0)
                }
                
        } customize: {
            $0
                  .type(.floater())
                  .position(.bottom)
                  .animation(.spring())
                  .closeOnTapOutside(true)
                  .backgroundColor(.black.opacity(0.5))
      
            
            
        }
    }
    }
  
    private func verify() -> Bool {
        var isValid = true
        
        if loginData.email == ""{
            emailError = "Please enter your email"
            isValid = false
           return isValid
        }
        else {
            emailError = ""
        }
        if loginData.password == ""  {
            passwordError = "Please enter your password"
            isValid = false
          return  isValid
        } else {
            passwordError = ""
        }
        return isValid
    }
}



struct LoginPage_Previews: PreviewProvider {
    static var previews: some View {
        LoginPage()
    }
}

