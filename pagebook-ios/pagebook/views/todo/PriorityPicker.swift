//
//  PriorityPicker.swift
//  pagebook
//
//  Created by Максим Гоглов on 21.07.2025.
//


import SwiftUI

struct PriorityPicker: View {
    @Binding var selection: TodoTask.Priority
    
    var body: some View {
        HStack(spacing: 12) {
            ForEach(TodoTask.Priority.allCases, id: \.self) { priority in
                PriorityOption(priority: priority, isSelected: priority == selection)
                    .onTapGesture {
                        withAnimation(.spring()) {
                            selection = priority
                        }
                    }
            }
        }
        .padding(.vertical, 4)
    }
}

struct PriorityOption: View {
    let priority: TodoTask.Priority
    let isSelected: Bool
    
    var body: some View {
        VStack(spacing: 6) {
            Image(systemName: priority.iconName)
                .font(.title3)
                .foregroundColor(isSelected ? .white : priority.color)
                .padding(10)
                .background(
                    Circle()
                        .fill(isSelected ? priority.color : Color.clear)
                        .overlay(
                            Circle()
                                .stroke(priority.color, lineWidth: isSelected ? 0 : 1)
                        )
                )
            
            Text(priority.description)
                .font(.caption)
                .foregroundColor(isSelected ? priority.color : .primary)
        }
        .frame(maxWidth: .infinity)
    }
}
