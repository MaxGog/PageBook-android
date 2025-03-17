package ru.maxgog.pagebook.storage

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

import ru.maxgog.pagebook.models.NoteModel

@Dao
interface NoteDao {
    @Query("SELECT * FROM notes")
    fun getNotes(): LiveData<List<NoteModel>>

    @Query("SELECT * FROM notes WHERE NoteId = :id")
    fun getNote(id: Int): NoteModel

    @Insert
    fun addNewNote(note: NoteModel)

    @Query("UPDATE notes SET title = :title, content = :content WHERE NoteId = :id")
    fun updateNote(id: Int, title: String, content: String)

    @Query("DELETE FROM notes WHERE NoteId = :id")
    fun deleteNote(id: Int)
}