package ru.maxgog.pagebook.storage

import androidx.lifecycle.LiveData
import ru.maxgog.pagebook.models.NoteModel

class NoteRepository(private val noteDao: NoteDao) {
    val allNotes: LiveData<List<NoteModel>> = noteDao.getNotes()

    suspend fun getNote(id: Int): NoteModel? = noteDao.getNote(id)

    suspend fun addNote(note: NoteModel) = noteDao.addNewNote(note)

    suspend fun updateNote(note: NoteModel) = noteDao.updateNote(note)

    suspend fun deleteNote(note: NoteModel) = noteDao.deleteNote(note)
}