//
//  TodoTaskRow.swift
//  pagebook
//
//  Created by Максим Гоглов on 21.07.2025.
//


import SwiftUI

struct TodoTaskRow: View {
    let task: TodoTask
    let toggleCompletion: (TodoTask) -> Void
    
    var body: some View {
        HStack {
            Button {
                toggleCompletion(task)
            } label: {
                Image(systemName: task.isCompleted ? "checkmark.circle.fill" : "circle")
                    .foregroundColor(task.priority.color)
            }
            .buttonStyle(.plain)
            
            VStack(alignment: .leading, spacing: 4) {
                Text(task.title)
                    .strikethrough(task.isCompleted)
                
                if let dueDate = task.dueDate {
                    Text(formatDate(dueDate))
                        .font(.caption)
                        .foregroundColor(.secondary)
                }
            }
            
            Spacer()
            
            Text(task.priority.description)
                .font(.caption)
                .foregroundColor(task.priority.color)
        }
    }
    
    private func formatDate(_ date: Date) -> String {
        let formatter = DateFormatter()
        formatter.dateStyle = .medium
        formatter.timeStyle = .none
        return formatter.string(from: date)
    }
}
