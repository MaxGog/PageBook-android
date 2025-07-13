//
//  TodoStore.swift
//  pagebook
//
//  Created by Максим Гоглов on 13.07.2025.
//
import SwiftUI

class TodoStore: ObservableObject {
    @Published var tasks: [TodoTask] = []
    
    init() {
        loadTasks()
    }
    
    func saveTasks() {
        if let encoded = try? JSONEncoder().encode(tasks) {
            UserDefaults.standard.set(encoded, forKey: "tasks")
        }
    }
    
    func loadTasks() {
        if let data = UserDefaults.standard.data(forKey: "tasks"),
           let decoded = try? JSONDecoder().decode([TodoTask].self, from: data) {
            tasks = decoded
        }
    }
}
