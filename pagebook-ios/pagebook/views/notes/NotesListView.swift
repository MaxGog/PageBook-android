//
//  NotesListView.swift
//  pagebook
//
//  Created by Максим Гоглов on 21.07.2025.
//
import SwiftUI

struct NotesListView: View {
    @Binding var notes: [Note]
    @Binding var showingNewNote: Bool
    @Binding var newNote: Note
    let onDelete: (IndexSet) -> Void
    let onSave: () -> Void
    
    var body: some View {
        List {
            ForEach($notes) { $note in
                NavigationLink {
                    NoteDetailView(
                        note: $note,
                        onSave: onSave
                    )
                } label: {
                    NoteRowView(note: note)
                }
            }
            .onDelete(perform: onDelete)
        }
        .navigationTitle("Мои заметки")
        .toolbar {
            ToolbarItem {
                AddNoteButton(
                    showingNewNote: $showingNewNote,
                    newNote: $newNote
                )
            }
        }
        .sheet(isPresented: $showingNewNote) {
            NoteCreationView(
                showingNewNote: $showingNewNote,
                newNote: $newNote,
                notes: $notes,
                onSave: onSave
            )
        }
    }
}
