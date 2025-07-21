//
//  TodoTask.swift
//  pagebook
//
//  Created by Максим Гоглов on 13.07.2025.
//
import Foundation
import SwiftUI

struct TodoTask: Identifiable, Codable {
    let id: UUID
    var title: String
    var isCompleted: Bool
    var dueDate: Date?
    var priority: Priority
    
    enum Priority: Int, Codable, CaseIterable {
        case low, medium, high
        
        var description: String {
            switch self {
            case .low: return "Низкий"
            case .medium: return "Средний"
            case .high: return "Высокий"
            }
        }
        
        var color: Color {
            switch self {
            case .low: return .green
            case .medium: return .orange
            case .high: return .red
            }
        }
        
        var iconName: String {
            switch self {
            case .low: return "arrow.down"
            case .medium: return "equal"
            case .high: return "exclamationmark"
            }
        }
    }
}
