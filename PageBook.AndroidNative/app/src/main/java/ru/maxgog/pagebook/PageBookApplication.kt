package ru.maxgog.pagebook

import android.app.Application
import ru.maxgog.pagebook.rooms.NoteRoomDatabase
import ru.maxgog.pagebook.repositories.NotesRepository
import ru.maxgog.pagebook.rooms.TodoRoomDatabase
import ru.maxgog.pagebook.repositories.TodoRepository

class PageBookApplication : Application() {
    lateinit var notesRepository: NotesRepository
    lateinit var todoRepository: TodoRepository

    override fun onCreate() {
        super.onCreate()

        val notesDb = NoteRoomDatabase.getDatabase(this)
        notesRepository = NotesRepository(notesDb.noteDao())

        val todoDb = TodoRoomDatabase.getDatabase(this)
        todoRepository = TodoRepository(todoDb.todoDao())
    }
}