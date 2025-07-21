//
//  ContentView.swift
//  pagebook
//
//  Created by Максим Гоглов on 13.07.2025.
//

import SwiftUI

struct NotesView: View {
    @StateObject private var store = NotesStore()
    @State private var showingNewNote = false
    @State private var newNote = Note.empty
    
    var body: some View {
        NavigationView {
            NotesListView(
                notes: $store.notes,
                showingNewNote: $showingNewNote,
                newNote: $newNote,
                onDelete: deleteNote,
                onSave: store.saveNotes
            )
        }
    }
    
    private func deleteNote(at offsets: IndexSet) {
        store.notes.remove(atOffsets: offsets)
        store.saveNotes()
    }
}
