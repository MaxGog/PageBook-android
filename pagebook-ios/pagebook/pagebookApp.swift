//
//  pagebookApp.swift
//  pagebook
//
//  Created by Максим Гоглов on 13.07.2025.
//

import SwiftUI
import SwiftData

@main
struct pagebookApp: App {
    var body: some Scene {
        WindowGroup {
            MainView()
        }
        #if os(macOS)
        .windowStyle(.titleBar)
        .windowToolbarStyle(.unified)
        #endif
    }
}
