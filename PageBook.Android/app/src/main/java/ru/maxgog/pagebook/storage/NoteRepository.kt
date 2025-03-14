package ru.maxgog.pagebook.storage

import androidx.lifecycle.LiveData

import ru.maxgog.pagebook.models.NoteModel

class NoteRepository(private val noteDao: NoteDao) {

    val allNotes: LiveData<List<NoteModel>> = noteDao.getAllNotes()

    suspend fun insert(note: NoteModel) {
        noteDao.insert(note)
    }
}