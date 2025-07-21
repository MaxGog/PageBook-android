//
//  CalendarDatePicker.swift
//  pagebook
//
//  Created by Максим Гоглов on 21.07.2025.
//
import SwiftUI

struct CalendarDatePicker: View {
    @Binding var selectedDate: Date
    
    var body: some View {
        DatePicker(
            "Выберите дату",
            selection: $selectedDate,
            displayedComponents: .date
        )
        .datePickerStyle(.graphical)
        .padding()
    }
}
