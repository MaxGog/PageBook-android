//
//  NoteDetailView.swift
//  pagebook
//
//  Created by Максим Гоглов on 13.07.2025.
//
import SwiftUI

struct NoteDetailView: View {
    var note: Note
    
    var body: some View {
        ScrollView {
            VStack(alignment: .leading, spacing: 16) {
                Text(note.title)
                    .font(.largeTitle)
                Text(note.createdAt.formatted())
                    .font(.subheadline)
                    .foregroundColor(.secondary)
                Divider()
                Text(note.content)
                    .font(.body)
            }
            .padding()
        }
        .navigationTitle(note.title)
        #if os(macOS)
        .frame(minWidth: 500, minHeight: 600)
        #endif
    }
}
