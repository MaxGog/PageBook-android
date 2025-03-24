package ru.maxgog.pagebook.storage

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import ru.maxgog.pagebook.models.NoteModel

@Dao
interface NoteDao {
    @Query("SELECT * FROM notes ORDER BY AtCreated DESC")
    fun getNotes(): LiveData<List<NoteModel>>

    @Query("SELECT * FROM notes WHERE NoteId = :id")
    suspend fun getNote(id: Int): NoteModel?

    @Insert
    suspend fun addNewNote(note: NoteModel)

    @Update
    suspend fun updateNote(note: NoteModel)

    @Delete
    suspend fun deleteNote(note: NoteModel)
}