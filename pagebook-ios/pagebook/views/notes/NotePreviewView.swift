//
//  NotePreviewView.swift
//  pagebook
//
//  Created by Максим Гоглов on 21.07.2025.
//
import SwiftUI

struct NotePreviewView: View {
    let note: Note
    
    var body: some View {
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
