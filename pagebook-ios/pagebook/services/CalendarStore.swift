//
//  CalendarStore.swift
//  pagebook
//
//  Created by Максим Гоглов on 13.07.2025.
//
import SwiftUI

class CalendarStore: ObservableObject {
    @Published var events: [CalendarEvent] = []
    init() {
        loadEvents()
    }
    
    func saveEvents() {
        if let encoded = try? JSONEncoder().encode(events) {
            UserDefaults.standard.set(encoded, forKey: "events")
        }
    }
    
    func loadEvents() {
        if let data = UserDefaults.standard.data(forKey: "events"),
           let decoded = try? JSONDecoder().decode([CalendarEvent].self, from: data) {
            events = decoded
        }
    }
}
