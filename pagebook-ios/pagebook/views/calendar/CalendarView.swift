//
//  CalendarView.swift
//  pagebook
//
//  Created by Максим Гоглов on 13.07.2025.
//
import SwiftUI

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
                CalendarDatePicker(selectedDate: $selectedDate)
                
                EventListView(
                    events: eventsForSelectedDate,
                    onDelete: deleteEvent
                )
            }
            .navigationTitle("Календарь")
            .toolbar {
                ToolbarItem(placement: .primaryAction) {
                    AddEventButton(
                        showingAddEvent: $showingAddEvent,
                        selectedDate: $selectedDate,
                        newEventDate: $newEventDate
                    )
                }
            }
            .sheet(isPresented: $showingAddEvent) {
                AddEventView(
                    isPresented: $showingAddEvent,
                    title: $newEventTitle,
                    description: $newEventDescription,
                    date: $newEventDate,
                    duration: $newEventDuration,
                    onAdd: addNewEvent,
                    onCancel: resetForm
                )
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
}
