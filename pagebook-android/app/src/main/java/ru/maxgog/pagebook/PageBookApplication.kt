package ru.maxgog.pagebook

import android.app.Application
import ru.maxgog.pagebook.rooms.*
import ru.maxgog.pagebook.repositories.*

class PageBookApplication : Application() {
    val notesRepository by lazy { NotesRepository(NoteRoomDatabase.getDatabase(this).noteDao()) }
    val todoRepository by lazy { TodoRepository(TodoRoomDatabase.getDatabase(this).todoDao()) }
    val eventsRepository by lazy { EventRepository(EventRoomDatabase.getDatabase(this).eventDao()) }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    companion object {
        @Volatile private var instance: PageBookApplication? = null

        fun getApplication(): PageBookApplication =
            instance ?: throw IllegalStateException("Application not initialized")
    }
}