package ru.maxgog.pagebook.storage

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

import ru.maxgog.pagebook.models.NoteModel

@Dao
interface NoteDao {
    @Insert
    suspend fun insert(note: NoteModel)

    @Query("SELECT * FROM notes ORDER BY id DESC")
    fun getAllNotes(): LiveData<List<NoteModel>>
}