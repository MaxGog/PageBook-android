//
//  NoteRowView.swift
//  pagebook
//
//  Created by Максим Гоглов on 21.07.2025.
//
import SwiftUI

struct NoteRowView: View {
    let note: Note
    
    var body: some View {
        VStack(alignment: .leading) {
            Text(note.title)
                .font(.headline)
                .lineLimit(1)
            
            if let preview = note.content.prefix(100).split(separator: "\n").first {
                Text(preview)
                    .font(.subheadline)
                    .foregroundColor(.secondary)
                    .lineLimit(2)
            }
        }
        .padding(.vertical, 4)
    }
}
