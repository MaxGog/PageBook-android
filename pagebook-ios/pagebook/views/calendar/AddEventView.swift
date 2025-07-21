//
//  AddEventView.swift
//  pagebook
//
//  Created by Максим Гоглов on 21.07.2025.
//
import SwiftUI

struct AddEventView: View {
    @Binding var isPresented: Bool
    @Binding var title: String
    @Binding var description: String
    @Binding var date: Date
    @Binding var duration: Double
    let onAdd: () -> Void
    let onCancel: () -> Void
    
    var body: some View {
        NavigationView {
            Form {
                TextField("Название события", text: $title)
                
                DatePicker("Дата и время", selection: $date)
                
                Stepper(
                    "Продолжительность: \(Int(duration)) ч",
                    value: $duration,
                    in: 0.5...8,
                    step: 0.5
                )
                
                TextField("Описание (необязательно)", text: $description)
            }
            .navigationTitle("Новое событие")
            .toolbar {
                ToolbarItem(placement: .cancellationAction) {
                    Button("Отмена") {
                        isPresented = false
                        onCancel()
                    }
                }
                ToolbarItem(placement: .confirmationAction) {
                    Button("Добавить") {
                        onAdd()
                    }
                    .disabled(title.isEmpty)
                }
            }
        }
        #if os(macOS)
        .frame(width: 400, height: 300)
        #endif
    }
}
