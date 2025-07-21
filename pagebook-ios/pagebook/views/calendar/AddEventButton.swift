//
//  AddEventButton.swift
//  pagebook
//
//  Created by Максим Гоглов on 21.07.2025.
//
import SwiftUI

struct AddEventButton: View {
    @Binding var showingAddEvent: Bool
    @Binding var selectedDate: Date
    @Binding var newEventDate: Date
    
    var body: some View {
        Button {
            showingAddEvent = true
            newEventDate = selectedDate
        } label: {
            Label("Добавить", systemImage: "plus")
        }
    }
}
