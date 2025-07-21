//
//  AddTaskView.swift
//  pagebook
//
//  Created by Максим Гоглов on 21.07.2025.
//


import SwiftUI

struct AddTaskView: View {
    @Binding var isPresented: Bool
    @Binding var title: String
    @Binding var dueDate: Date
    @Binding var priority: TodoTask.Priority
    let onAdd: () -> Void
    
    var body: some View {
        NavigationView {
            VStack(spacing: 0) {
                VStack {
                    Text("Новая задача")
                        .font(.title2)
                        .fontWeight(.semibold)
                        .padding(.top, 20)
                    
                    Divider()
                }
                
                Form {
                    Section {
                        TextField("Название задачи", text: $title)
                            .font(.headline)
                            .submitLabel(.done)
                    }
                    
                    Section(header: Text("Приоритет").font(.headline)) {
                        PriorityPicker(selection: $priority)
                            .padding(.vertical, 8)
                    }
                    
                    Section(header: Text("Срок выполнения").font(.headline)) {
                        DatePicker("Выберите дату",
                                   selection: $dueDate,
                                   in: Date()...,
                                   displayedComponents: .date)
                            .datePickerStyle(.graphical)
                            .padding(.vertical, 4)
                    }
                }
            }
            .toolbar {
                ToolbarItem(placement: .cancellationAction) {
                    Button("Отмена") {
                        isPresented = false
                    }
                    .foregroundColor(.red)
                }
                
                ToolbarItem(placement: .confirmationAction) {
                    Button(action: onAdd) {
                        Text("Добавить")
                            .bold()
                    }
                    .disabled(title.isEmpty)
                    .foregroundColor(title.isEmpty ? .gray : .blue)
                }
            }
        }
        #if os(macOS)
        .frame(width: 450, height: 600)
        #endif
    }
}
