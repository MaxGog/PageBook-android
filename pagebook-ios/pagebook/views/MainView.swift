//
//  MainView.swift
//  pagebook
//
//  Created by Максим Гоглов on 13.07.2025.
//
import SwiftUI

struct MainView: View {
    enum AppTab: Hashable {
        case notes, todos, calendar
    }
    
    @State private var selectedTab: AppTab? = .notes
    
    var body: some View {
        #if os(iOS)
        TabView(selection: $selectedTab) {
            NotesView()
                .tabItem {
                    Label("Заметки", systemImage: "note.text")
                }
                .tag(AppTab.notes)
            
            TodoView()
                .tabItem {
                    Label("Задачи", systemImage: "checklist")
                }
                .tag(AppTab.todos)
            
            CalendarView()
                .tabItem {
                    Label("Календарь", systemImage: "calendar")
                }
                .tag(AppTab.calendar)
        }
        #elseif os(macOS)
        NavigationView {
            List(selection: $selectedTab) {
                NavigationLink(destination: NotesView(), tag: AppTab.notes, selection: $selectedTab) {
                    Label("Заметки", systemImage: "note.text")
                }
                .tag(AppTab.notes)
                
                NavigationLink(destination: TodoView(), tag: AppTab.todos, selection: $selectedTab) {
                    Label("Задачи", systemImage: "checklist")
                }
                .tag(AppTab.todos)
                
                NavigationLink(destination: CalendarView(), tag: AppTab.calendar, selection: $selectedTab) {
                    Label("Календарь", systemImage: "calendar")
                }
                .tag(AppTab.calendar)
            }
            .listStyle(SidebarListStyle())
            .frame(minWidth: 200)
            
            Group {
                switch selectedTab {
                case .notes:
                    NotesView()
                case .todos:
                    TodoView()
                case .calendar:
                    CalendarView()
                case nil:
                    Text("Выберите раздел")
                        .frame(maxWidth: .infinity, maxHeight: .infinity)
                }
            }
            .frame(maxWidth: .infinity, maxHeight: .infinity)
        }
        .frame(minWidth: 800, minHeight: 600)
        #endif
    }
}
