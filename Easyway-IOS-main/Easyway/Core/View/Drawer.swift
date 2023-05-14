//
//  Drawer.swift
//  Easyway
//
//  Created by Rayen Majdoub on 16/3/2023.
//

import SwiftUI
import SwiftDrawer


struct DrawerView: View {
    
    var body: some View {
                 Drawer()
                         .setSlider(view: SliderView(type: .leftRear))
                         .setMain(view: HomePage())
        
     
    }

}
struct SliderView : View, SliderProtocol {
    let userdefaults = UserDefaults.standard
    @EnvironmentObject public var drawerControl: DrawerControl
    @State var    showlogin = false
    let type: SliderType
    @Environment(\.dismiss) var dismiss
    private let userDeafaults = UserDefaults.standard

    init(type: SliderType) {
        self.type = type 
    }
    var body: some View {
        VStack {
          Image(userDeafaults.string(forKey: "profilepic")!)
                .resizable()
                .aspectRatio(contentMode: .fit)
                .frame(height: 200)
                .padding(.top,40)
                .padding(.bottom,10)
            Text(   userdefaults.string(forKey: "username") ?? "")
                .font(.custom(Fonts.Font1, size: 22))
                .frame(maxWidth: .infinity ,alignment: .center)
                .foregroundColor(Color(Colors.ColorSecondary))
          
     
            VStack {
                SliderCell(imgName: "house.fill", title: "Home").onTapGesture {
                    self.drawerControl.show(type: type, isShow: false)
                    self.drawerControl.setMain(view: HomePage())
                }
                SliderCell(imgName: "person.circle.fill", title: "Profile").onTapGesture {
                    self.drawerControl.show(type: type, isShow: false)
                    self.drawerControl.setMain(view: ProfilePage())
                }
                SliderCell(imgName: "ticket", title: "My tickets").onTapGesture {
                    self.drawerControl.show(type: type, isShow: false)
                    self.drawerControl.setMain(view: MyTicketsPage())
                }
                if userdefaults.string(forKey: "role") == "EMPLOYEE_ROLE"
                {
                    SliderCell(imgName: "qrcode.viewfinder", title: "Scan tickets").onTapGesture {
                    self.drawerControl.show(type: type, isShow: false)
                    self.drawerControl.setMain(view: ScannerPage())
                }
                    
                }
                Spacer().padding(.bottom)
                Divider()
                
                SliderCell(imgName: "gear", title: "Settings").onTapGesture {
     
                }.padding(.bottom,40)
       
            }.padding(10)
            .background(.white)
            .cornerRadius(20)
            Spacer()
        }
        .ignoresSafeArea()
        .background(Color(Colors.ColorLightGrey))
        
        
    }
}

struct SliderCell : View {
    private let img: String
    private let title: String
    var body: some View {
            HStack {
                Image(systemName: self.img)
                    .foregroundColor(Color(Colors.ColorSecondary)).frame(width: 35,height: 35,alignment: .leading)
                Text(self.title).foregroundColor(Color(Colors.ColorSecondary)).multilineTextAlignment( .leading)
               Spacer()
                Image(systemName: "chevron.right")
                    .foregroundColor(Color(Colors.ColorSecondary)).frame(width: 35,height: 35,alignment: .trailing)
                    .padding(.trailing)
            }.padding(3)
        
    }
    
    init(imgName: String, title: String) {
        self.img = imgName
        self.title = title
    }
}

