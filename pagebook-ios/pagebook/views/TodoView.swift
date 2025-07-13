//
//  TodoView.swift
//  pagebook
//
//  Created by Максим Гоглов on 13.07.2025.
//
import SwiftUI
import SwiftData

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
                        HStack {
                            Button {
                                toggleTaskCompletion(task)
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
                    .onDelete(perform: deleteTask)
                }
            }
            .navigationTitle("Задачи")
            .toolbar {
                ToolbarItem(placement: .primaryAction) {
                    Button {
                        showingAddTask = true
                    } label: {
                        Label("Добавить", systemImage: "plus")
                    }
                }
                
                ToolbarItem(placement: .automatic) {
                    Toggle(isOn: $showCompleted) {
                        Label("Показать выполненные", systemImage: "eye")
                    }
                }
            }
            .sheet(isPresented: $showingAddTask) {
                NavigationView {
                    Form {
                        TextField("Название задачи", text: $newTaskTitle)
                        
                        Picker("Приоритет", selection: $newTaskPriority) {
                            ForEach(TodoTask.Priority.allCases, id: \.self) { priority in
                                Text(priority.description).tag(priority)
                            }
                        }
                        .pickerStyle(SegmentedPickerStyle())
                        
                        DatePicker("Срок выполнения", selection: $newTaskDueDate, displayedComponents: .date)
                    }
                    .navigationTitle("Новая задача")
                    .toolbar {
                        ToolbarItem(placement: .cancellationAction) {
                            Button("Отмена") {
                                showingAddTask = false
                                resetForm()
                            }
                        }
                        ToolbarItem(placement: .confirmationAction) {
                            Button("Добавить") {
                                addNewTask()
                            }
                            .disabled(newTaskTitle.isEmpty)
                        }
                    }
                }
                #if os(macOS)
                .frame(width: 400, height: 250)
                #endif
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
    
    private func formatDate(_ date: Date) -> String {
        let formatter = DateFormatter()
        formatter.dateStyle = .medium
        formatter.timeStyle = .none
        return formatter.string(from: date)
    }
}
