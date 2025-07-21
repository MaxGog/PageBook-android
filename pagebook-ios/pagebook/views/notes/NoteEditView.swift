//
//  NoteEditView.swift
//  pagebook
//
//  Created by Максим Гоглов on 21.07.2025.
//
import SwiftUI

struct NoteEditView: View {
    @Binding var title: String
    @Binding var content: String
    let createdAt: Date
    let isNewNote: Bool
    
    var body: some View {
        ScrollView {
            VStack(alignment: .leading, spacing: 16) {
                TextField("Заголовок", text: $title)
                    .font(.largeTitle)
                    .submitLabel(.done)
                
                if !isNewNote {
                    Text(createdAt.formatted())
                        .font(.subheadline)
                        .foregroundColor(.secondary)
                }
                
                Divider()
                
                TextEditor(text: $content)
                    .font(.system(.body, design: .monospaced))
                    .frame(minHeight: 300)
                    .padding(.horizontal, -5)
                    .overlay(
                        content.isEmpty ?
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
}
