//
//  EventListView.swift
//  pagebook
//
//  Created by Максим Гоглов on 21.07.2025.
//
import SwiftUI

struct EventListView: View {
    let events: [CalendarEvent]
    let onDelete: (IndexSet) -> Void
    
    var body: some View {
        List {
            ForEach(events) { event in
                EventRow(event: event)
            }
            .onDelete(perform: onDelete)
        }
        .listStyle(.plain)
    }
}

struct EventRow: View {
    let event: CalendarEvent
    
    var body: some View {
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
    
    private func formatTime(_ date: Date) -> String {
        let formatter = DateFormatter()
        formatter.timeStyle = .short
        return formatter.string(from: date)
    }
}
