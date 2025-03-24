package ru.maxgog.pagebook

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ru.maxgog.pagebook.models.NoteModel
import ru.maxgog.pagebook.storage.NoteDao
import ru.maxgog.pagebook.storage.NotesRepository

@Database(entities = [NoteModel::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun noteDao(): NoteDao
}

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