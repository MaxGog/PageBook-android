//
//  ToolbarFilterItem.swift
//  pagebook
//
//  Created by Максим Гоглов on 21.07.2025.
//


import SwiftUI

struct ToolbarFilterItem: View {
    @Binding var showCompleted: Bool
    
    var body: some View {
        Toggle(isOn: $showCompleted) {
            Label("Показать выполненные", systemImage: "eye")
        }
    }
}
