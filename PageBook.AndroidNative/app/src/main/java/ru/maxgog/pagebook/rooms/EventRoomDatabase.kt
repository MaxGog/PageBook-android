package ru.maxgog.pagebook.rooms

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ru.maxgog.pagebook.Converters
import ru.maxgog.pagebook.dao.EventDao
import ru.maxgog.pagebook.models.EventModel

@Database(entities = [EventModel::class], version = 1)
@TypeConverters(Converters::class)
abstract class EventRoomDatabase : RoomDatabase() {
    abstract fun eventDao(): EventDao

    companion object {
        @Volatile
        private var INSTANCE: EventRoomDatabase? = null

        fun getDatabase(context: Context): EventRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    EventRoomDatabase::class.java,
                    "event_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}