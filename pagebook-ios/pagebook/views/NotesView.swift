//
//  ContentView.swift
//  pagebook
//
//  Created by Максим Гоглов on 13.07.2025.
//

import SwiftUI
import SwiftData

struct NotesView: View {
    @StateObject private var store = NotesStore()
    @State private var showingNewNote = false
    @State private var newNote = Note(id: UUID(), title: "", content: "", createdAt: Date())
    
    var body: some View {
        NavigationView {
            List {
                ForEach($store.notes) { $note in
                    NavigationLink {
                        NoteDetailView(note: $note, onSave: {
                            store.saveNotes()
                        })
                    } label: {
                        NoteRowView(note: note)
                    }
                }
                .onDelete(perform: deleteNote)
            }
            .navigationTitle("Мои заметки")
            .toolbar {
                ToolbarItem {
                    Button(action: {
                        newNote = Note(id: UUID(), title: "", content: "", createdAt: Date())
                        showingNewNote = true
                    }) {
                        Label("Добавить", systemImage: "plus")
                    }
                }
            }
            .sheet(isPresented: $showingNewNote) {
                NoteDetailView(
                    note: $newNote,
                    onSave: {
                        if !newNote.title.isEmpty {
                            store.notes.append(newNote)
                            store.saveNotes()
                        }
                        showingNewNote = false
                    },
                    isNewNote: true
                )
            }
        }
    }
    
    func deleteNote(at offsets: IndexSet) {
        store.notes.remove(atOffsets: offsets)
        store.saveNotes()
    }
}

struct NoteRowView: View {
    var note: Note
    
    var body: some View {
        VStack(alignment: .leading) {
            Text(note.title)
                .font(.headline)
            
            if let preview = note.content.prefix(100).split(separator: "\n").first {
                Text(preview)
                    .font(.subheadline)
                    .foregroundColor(.secondary)
            }
        }
    }
}
