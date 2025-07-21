//
//  NoteCreationView.swift
//  pagebook
//
//  Created by Максим Гоглов on 21.07.2025.
//


import SwiftUI

struct NoteCreationView: View {
    @Binding var showingNewNote: Bool
    @Binding var newNote: Note
    @Binding var notes: [Note]
    let onSave: () -> Void
    
    var body: some View {
        NoteDetailView(
            note: $newNote,
            onSave: {
                if !newNote.title.isEmpty {
                    notes.append(newNote)
                    onSave()
                }
                showingNewNote = false
            },
            isNewNote: true
        )
    }
}
