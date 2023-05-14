//
//  EasywayApp.swift
//  Easyway
//
//  Created by Rayen Majdoub on 15/3/2023.
//

import SwiftUI

@main
struct EasywayApp: App {
    let persistenceController = PersistenceController.shared
    @StateObject var searchlocationModel = LocationSearchModel()

    private let defaults = UserDefaults.standard
    var body: some Scene {
        WindowGroup {
     
            if(defaults.string(forKey: "jwtToken") == nil)
            {
                GetStarted()

            }else
            {
                DrawerView()
                    .environmentObject(searchlocationModel)
            }
        }
    }
}
