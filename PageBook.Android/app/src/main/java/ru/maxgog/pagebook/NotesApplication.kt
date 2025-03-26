package ru.maxgog.pagebook

import android.app.Application
import androidx.room.Room
import ru.maxgog.pagebook.storage.NotesRepository

class NotesApplication : Application() {
    val database by lazy {
        Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "notes_db"
        ).build()
    }

    val repository by lazy { NotesRepository(database.noteDao()) }
}