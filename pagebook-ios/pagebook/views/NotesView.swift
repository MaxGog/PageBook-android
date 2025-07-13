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
    @State private var showingAddNote = false
    @State private var newNoteTitle = ""
    @State private var newNoteContent = ""
    
    var body: some View {
        NavigationView {
            List {
                ForEach(store.notes) { note in
                    NavigationLink {
                        NoteDetailView(note: note)
                    } label: {
                        VStack(alignment: .leading) {
                            Text(note.title)
                                .font(.headline)
                            Text(note.content.prefix(50))
                                .font(.subheadline)
                                .foregroundColor(.secondary)
                        }
                    }
                }
                .onDelete(perform: deleteNote)
            }
            .navigationTitle("Мои заметки")
            .toolbar {
                ToolbarItem {
                    Button(action: { showingAddNote = true }) {
                        Label("Добавить", systemImage: "plus")
                    }
                }
            }
        }
        .sheet(isPresented: $showingAddNote) {
            NavigationView {
                Form {
                    TextField("Заголовок", text: $newNoteTitle)
                    TextEditor(text: $newNoteContent)
                        .frame(minHeight: 200)
                }
                .navigationTitle("Новая заметка")
                .toolbar {
                    ToolbarItem(placement: .cancellationAction) {
                        Button("Отмена") {
                            showingAddNote = false
                            newNoteTitle = ""
                            newNoteContent = ""
                        }
                    }
                    ToolbarItem(placement: .confirmationAction) {
                        Button("Сохранить") {
                            let note = Note(
                                id: UUID(),
                                title: newNoteTitle,
                                content: newNoteContent,
                                createdAt: Date()
                            )
                            store.notes.append(note)
                            store.saveNotes()
                            showingAddNote = false
                            newNoteTitle = ""
                            newNoteContent = ""
                        }
                        .disabled(newNoteTitle.isEmpty)
                    }
                }
            }
            #if os(macOS)
            .frame(minWidth: 400, minHeight: 500)
            #endif
        }
    }
    
    func deleteNote(at offsets: IndexSet) {
        store.notes.remove(atOffsets: offsets)
        store.saveNotes()
    }
}
