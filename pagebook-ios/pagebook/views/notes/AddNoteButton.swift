//
//  AddNoteButton.swift
//  pagebook
//
//  Created by Максим Гоглов on 21.07.2025.
//


import SwiftUI

struct AddNoteButton: View {
    @Binding var showingNewNote: Bool
    @Binding var newNote: Note
    
    var body: some View {
        Button(action: {
            newNote = Note.empty
            showingNewNote = true
        }) {
            Label("Добавить", systemImage: "plus")
        }
    }
}