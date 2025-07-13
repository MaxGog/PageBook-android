//
//  Note.swift
//  pagebook
//
//  Created by Максим Гоглов on 13.07.2025.
//

import SwiftUI

struct Note: Identifiable, Codable {
    let id: UUID
    var title: String
    var content: String
    var createdAt: Date
}
