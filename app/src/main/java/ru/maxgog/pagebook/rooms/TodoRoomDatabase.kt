package ru.maxgog.pagebook.rooms

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ru.maxgog.pagebook.dao.TodoDao
import ru.maxgog.pagebook.models.TodoModel

@Database(entities = [TodoModel::class], version = 1)
abstract class TodoRoomDatabase : RoomDatabase() {
    abstract fun todoDao(): TodoDao

    companion object {
        @Volatile private var instance: TodoRoomDatabase? = null

        fun getDatabase(context: Context): TodoRoomDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context,
                TodoRoomDatabase::class.java,
                "todo_db"
            ).build()
    }
}