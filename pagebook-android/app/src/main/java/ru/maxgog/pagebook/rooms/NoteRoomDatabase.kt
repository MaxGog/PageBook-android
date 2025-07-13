package ru.maxgog.pagebook.rooms

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ru.maxgog.pagebook.dao.NoteDao
import ru.maxgog.pagebook.models.NoteModel

@Database(entities = [(NoteModel::class)], version = 1)
abstract class NoteRoomDatabase: RoomDatabase() {
    abstract fun noteDao(): NoteDao

    companion object {
        private var INSTANCE: NoteRoomDatabase? = null
        fun getDatabase(context: Context): NoteRoomDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        NoteRoomDatabase::class.java,
                        "notes_db"
                    ).fallbackToDestructiveMigration().build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}