//
//  NoteToolbar.swift
//  pagebook
//
//  Created by Максим Гоглов on 21.07.2025.
//
import SwiftUI

struct NoteToolbar: ToolbarContent {
    let isNewNote: Bool
    @Binding var isEditing: Bool
    let editedTitle: String
    @Binding var note: Note
    @Binding var editedTitleBinding: String
    @Binding var editedContentBinding: String
    let onSave: () -> Void
    let dismiss: DismissAction
    
    var body: some ToolbarContent {
        if isNewNote {
            ToolbarItem(placement: .cancellationAction) {
                Button("Отмена") {
                    dismiss()
                }
            }
        }
        
        ToolbarItem(placement: .primaryAction) {
            if isNewNote {
                Button("Готово") {
                    note.title = editedTitleBinding
                    note.content = editedContentBinding
                    onSave()
                    dismiss()
                }
                .disabled(editedTitle.isEmpty)
            } else {
                Button(isEditing ? "Готово" : "Редактировать") {
                    if isEditing {
                        note.title = editedTitleBinding
                        note.content = editedContentBinding
                        onSave()
                    } else {
                        editedTitleBinding = note.title
                        editedContentBinding = note.content
                    }
                    isEditing.toggle()
                }
            }
        }
    }
}
