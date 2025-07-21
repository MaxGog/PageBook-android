//
//  Note.swift
//  pagebook
//
//  Created by Максим Гоглов on 13.07.2025.
//

import Foundation

struct Note: Identifiable, Codable {
    let id: UUID
    var title: String
    var content: String
    let createdAt: Date
    
    init(id: UUID = UUID(), title: String = "", content: String = "", createdAt: Date = Date()) {
        self.id = id
        self.title = title
        self.content = content
        self.createdAt = createdAt
    }
    
    static var empty: Note {
        Note(id: UUID(), title: "", content: "", createdAt: Date())
    }
}
