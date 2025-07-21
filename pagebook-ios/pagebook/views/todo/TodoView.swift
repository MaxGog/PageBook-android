//
//  TodoView.swift
//  pagebook
//
//  Created by Максим Гоглов on 13.07.2025.
//
import SwiftUI

struct TodoView: View {
    @StateObject private var store = TodoStore()
    @State private var showingAddTask = false
    @State private var newTaskTitle = ""
    @State private var newTaskDueDate = Date()
    @State private var newTaskPriority: TodoTask.Priority = .medium
    @State private var showCompleted = true
    
    var body: some View {
        NavigationView {
            List {
                Section {
                    ForEach(filteredTasks) { task in
                        TodoTaskRow(task: task, toggleCompletion: toggleTaskCompletion)
                    }
                    .onDelete(perform: deleteTask)
                }
            }
            .navigationTitle("Задачи")
            .toolbar {
                ToolbarItemGroup(placement: .primaryAction) {
                    Button {
                        showingAddTask = true
                    } label: {
                        Label("Добавить", systemImage: "plus")
                    }
                    
                    ToolbarFilterItem(showCompleted: $showCompleted)
                }
            }
            .sheet(isPresented: $showingAddTask) {
                AddTaskView(
                    isPresented: $showingAddTask,
                    title: $newTaskTitle,
                    dueDate: $newTaskDueDate,
                    priority: $newTaskPriority,
                    onAdd: addNewTask
                )
            }
        }
    }
    
    private var filteredTasks: [TodoTask] {
        store.tasks.filter { showCompleted || !$0.isCompleted }
                   .sorted { $0.priority.rawValue > $1.priority.rawValue }
    }
    
    private func toggleTaskCompletion(_ task: TodoTask) {
        if let index = store.tasks.firstIndex(where: { $0.id == task.id }) {
            store.tasks[index].isCompleted.toggle()
            store.saveTasks()
        }
    }
    
    private func addNewTask() {
        let task = TodoTask(
            id: UUID(),
            title: newTaskTitle,
            isCompleted: false,
            dueDate: newTaskDueDate,
            priority: newTaskPriority
        )
        store.tasks.append(task)
        store.saveTasks()
        showingAddTask = false
        resetForm()
    }
    
    private func deleteTask(at offsets: IndexSet) {
        store.tasks.remove(atOffsets: offsets)
        store.saveTasks()
    }
    
    private func resetForm() {
        newTaskTitle = ""
        newTaskDueDate = Date()
        newTaskPriority = .medium
    }
}
