//
//  CalendarEvent.swift
//  pagebook
//
//  Created by Максим Гоглов on 13.07.2025.
//
import SwiftUI

struct CalendarEvent: Identifiable, Codable {
    let id: UUID
    var title: String
    var description: String
    var date: Date
    var duration: TimeInterval // в часах
}
