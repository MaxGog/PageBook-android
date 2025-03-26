package ru.maxgog.pagebook

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ru.maxgog.pagebook.converters.Converters
import ru.maxgog.pagebook.models.NoteModel
import ru.maxgog.pagebook.storage.NoteDao
import ru.maxgog.pagebook.storage.NotesRepository

@Database(entities = [NoteModel::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun noteDao(): NoteDao
}