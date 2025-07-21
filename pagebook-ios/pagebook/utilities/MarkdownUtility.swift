//
//  MarkdownUtility.swift
//  pagebook
//
//  Created by Максим Гоглов on 21.07.2025.
//
import SwiftUI

struct MarkdownText: View {
    let text: String
    
    var body: some View {
        VStack(alignment: .leading, spacing: 8) {
            ForEach(processMarkdown(text), id: \.self) { component in
                switch component.type {
                case .heading1:
                    Text(component.text)
                        .font(.title)
                        .bold()
                        .padding(.vertical, 4)
                case .heading2:
                    Text(component.text)
                        .font(.title2)
                        .bold()
                        .padding(.vertical, 4)
                case .heading3:
                    Text(component.text)
                        .font(.title3)
                        .bold()
                        .padding(.vertical, 4)
                case .bold:
                    Text(component.text)
                        .bold()
                case .italic:
                    Text(component.text)
                        .italic()
                case .code:
                    Text(component.text)
                        .font(.system(.body, design: .monospaced))
                        .padding(4)
                        .background(Color.gray.opacity(0.2))
                        .cornerRadius(4)
                case .quote:
                    Text(component.text)
                        .padding(.leading, 10)
                        .border(Color.gray, width: 1)
                case .listItem:
                    HStack(alignment: .top) {
                        Text("•")
                        Text(component.text)
                    }
                case .text:
                    Text(component.text)
                }
            }
        }
        .frame(maxWidth: .infinity, alignment: .leading)
    }
    
    private func processMarkdown(_ text: String) -> [MarkdownComponent] {
        var components: [MarkdownComponent] = []
        let lines = text.components(separatedBy: .newlines)
        
        for line in lines {
            if line.hasPrefix("# ") {
                components.append(MarkdownComponent(text: String(line.dropFirst(2)), type: .heading1))
            } else if line.hasPrefix("## ") {
                components.append(MarkdownComponent(text: String(line.dropFirst(3)), type: .heading2))
            } else if line.hasPrefix("### ") {
                components.append(MarkdownComponent(text: String(line.dropFirst(4)), type: .heading3))
            } else if line.hasPrefix("> ") {
                components.append(MarkdownComponent(text: String(line.dropFirst(2)), type: .quote))
            } else if line.hasPrefix("- ") {
                components.append(MarkdownComponent(text: String(line.dropFirst(2)), type: .listItem))
            } else if line.contains("**") {
                let parts = line.components(separatedBy: "**")
                for (index, part) in parts.enumerated() {
                    if index % 2 == 1 {
                        components.append(MarkdownComponent(text: part, type: .bold))
                    } else {
                        components.append(MarkdownComponent(text: part, type: .text))
                    }
                }
            } else if line.contains("`") {
                let parts = line.components(separatedBy: "`")
                for (index, part) in parts.enumerated() {
                    if index % 2 == 1 {
                        components.append(MarkdownComponent(text: part, type: .code))
                    } else {
                        components.append(MarkdownComponent(text: part, type: .text))
                    }
                }
            } else {
                components.append(MarkdownComponent(text: line, type: .text))
            }
        }
        
        return components
    }
}

struct MarkdownComponent: Hashable {
    let text: String
    let type: MarkdownComponentType
}

enum MarkdownComponentType: Hashable {
    case heading1
    case heading2
    case heading3
    case bold
    case italic
    case code
    case quote
    case listItem
    case text
}
