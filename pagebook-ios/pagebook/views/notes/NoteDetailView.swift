//
//  NoteDetailView.swift
//  pagebook
//
//  Created by Максим Гоглов on 21.07.2025.
//
import SwiftUI

struct NoteDetailView: View {
    @Binding var note: Note
    var onSave: () -> Void
    var isNewNote: Bool = false
    @State private var isEditing = false
    @State private var editedTitle = ""
    @State private var editedContent = ""
    @Environment(\.dismiss) private var dismiss
    
    var body: some View {
        Group {
            if isNewNote || isEditing {
                NoteEditView(
                    title: $editedTitle,
                    content: $editedContent,
                    createdAt: note.createdAt,
                    isNewNote: isNewNote
                )
            } else {
                NotePreviewView(note: note)
            }
        }
        .toolbar {
            NoteToolbar(
                isNewNote: isNewNote,
                isEditing: $isEditing,
                editedTitle: editedTitle,
                note: $note,
                editedTitleBinding: $editedTitle,
                editedContentBinding: $editedContent,
                onSave: onSave,
                dismiss: dismiss
            )
        }
        .navigationTitle(isNewNote ? "Новая заметка" : (isEditing ? "Редактирование" : note.title))
        .onAppear {
            if isNewNote {
                editedTitle = note.title
                editedContent = note.content
                isEditing = true
            }
        }
    }
}
