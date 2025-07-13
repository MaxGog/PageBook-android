//
//  CalendarView.swift
//  pagebook
//
//  Created by Максим Гоглов on 13.07.2025.
//
import SwiftUI
import SwiftData

struct CalendarView: View {
    @StateObject private var store = CalendarStore()
    @State private var selectedDate = Date()
    @State private var showingAddEvent = false
    @State private var newEventTitle = ""
    @State private var newEventDescription = ""
    @State private var newEventDate = Date()
    @State private var newEventDuration = 1.0
    
    var body: some View {
        NavigationView {
            VStack {
                DatePicker(
                    "Выберите дату",
                    selection: $selectedDate,
                    displayedComponents: .date
                )
                .datePickerStyle(.graphical)
                .padding()
                
                List {
                    ForEach(eventsForSelectedDate) { event in
                        VStack(alignment: .leading, spacing: 4) {
                            Text(event.title)
                                .font(.headline)
                            
                            Text("\(formatTime(event.date)) - \(formatTime(event.date.addingTimeInterval(event.duration * 3600)))")
                                .font(.subheadline)
                                .foregroundColor(.secondary)
                            
                            if !event.description.isEmpty {
                                Text(event.description)
                                    .font(.body)
                            }
                        }
                        .padding(.vertical, 8)
                    }
                    .onDelete(perform: deleteEvent)
                }
                .listStyle(.plain)
            }
            .navigationTitle("Календарь")
            .toolbar {
                ToolbarItem(placement: .primaryAction) {
                    Button {
                        showingAddEvent = true
                        newEventDate = selectedDate
                    } label: {
                        Label("Добавить", systemImage: "plus")
                    }
                }
            }
            .sheet(isPresented: $showingAddEvent) {
                NavigationView {
                    Form {
                        TextField("Название события", text: $newEventTitle)
                        
                        DatePicker("Дата и время", selection: $newEventDate)
                        
                        Stepper(
                            "Продолжительность: \(Int(newEventDuration)) ч",
                            value: $newEventDuration,
                            in: 0.5...8,
                            step: 0.5
                        )
                        
                        TextField("Описание (необязательно)", text: $newEventDescription)
                    }
                    .navigationTitle("Новое событие")
                    .toolbar {
                        ToolbarItem(placement: .cancellationAction) {
                            Button("Отмена") {
                                showingAddEvent = false
                                resetForm()
                            }
                        }
                        ToolbarItem(placement: .confirmationAction) {
                            Button("Добавить") {
                                addNewEvent()
                            }
                            .disabled(newEventTitle.isEmpty)
                        }
                    }
                }
                #if os(macOS)
                .frame(width: 400, height: 300)
                #endif
            }
        }
    }
    
    private var eventsForSelectedDate: [CalendarEvent] {
        store.events.filter { Calendar.current.isDate($0.date, inSameDayAs: selectedDate) }
                   .sorted { $0.date < $1.date }
    }
    
    private func addNewEvent() {
        let event = CalendarEvent(
            id: UUID(),
            title: newEventTitle,
            description: newEventDescription,
            date: newEventDate,
            duration: newEventDuration
        )
        store.events.append(event)
        store.saveEvents()
        showingAddEvent = false
        resetForm()
    }
    
    private func deleteEvent(at offsets: IndexSet) {
        store.events.remove(atOffsets: offsets)
        store.saveEvents()
    }
    
    private func resetForm() {
        newEventTitle = ""
        newEventDescription = ""
        newEventDate = Date()
        newEventDuration = 1.0
    }
    
    private func formatTime(_ date: Date) -> String {
        let formatter = DateFormatter()
        formatter.timeStyle = .short
        return formatter.string(from: date)
    }
}
