package ru.maxgog.pagebook.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import ru.maxgog.pagebook.models.NoteModel

@Dao
interface NoteDao {
    @Query("SELECT * FROM notes")
    fun getAll(): Flow<List<NoteModel>>

    @Insert
    suspend fun insert(note: NoteModel)

    @Update
    suspend fun update(note: NoteModel)

    @Delete
    suspend fun delete(note: NoteModel)
}