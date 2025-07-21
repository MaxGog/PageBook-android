//
//  NoteDetailView.swift
//  pagebook
//
//  Created by Максим Гоглов on 21.07.2025.
//
import SwiftUI
import SwiftData

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
                editView
            } else {
                previewView
            }
        }
        .toolbar {
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
                        note.title = editedTitle
                        note.content = editedContent
                        onSave()
                        dismiss()
                    }
                    .disabled(editedTitle.isEmpty)
                } else {
                    Button(isEditing ? "Готово" : "Редактировать") {
                        if isEditing {
                            note.title = editedTitle
                            note.content = editedContent
                            onSave()
                        } else {
                            editedTitle = note.title
                            editedContent = note.content
                        }
                        isEditing.toggle()
                    }
                }
            }
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
    
    private var editView: some View {
        ScrollView {
            VStack(alignment: .leading, spacing: 16) {
                TextField("Заголовок", text: $editedTitle)
                    .font(.largeTitle)
                    .submitLabel(.done)
                
                if !isNewNote {
                    Text(note.createdAt.formatted())
                        .font(.subheadline)
                        .foregroundColor(.secondary)
                }
                
                Divider()
                
                TextEditor(text: $editedContent)
                    .font(.system(.body, design: .monospaced))
                    .frame(minHeight: 300)
                    .padding(.horizontal, -5)
                    .overlay(
                        editedContent.isEmpty ?
                        Text("Начните писать здесь...")
                            .foregroundColor(.gray)
                            .padding(.top, 8)
                            .padding(.leading, 5)
                            .allowsHitTesting(false)
                        : nil,
                        alignment: .topLeading
                    )
            }
            .padding()
        }
    }
    
    private var previewView: some View {
        ScrollView {
            VStack(alignment: .leading, spacing: 16) {
                Text(note.title)
                    .font(.largeTitle)
                
                Text(note.createdAt.formatted())
                    .font(.subheadline)
                    .foregroundColor(.secondary)
                
                Divider()
                
                if note.content.isEmpty {
                    Text("Заметка пуста")
                        .foregroundColor(.secondary)
                        .frame(maxWidth: .infinity, alignment: .center)
                        .padding(.vertical, 50)
                } else {
                    MarkdownText(text: note.content)
                }
            }
            .padding()
        }
    }
}
