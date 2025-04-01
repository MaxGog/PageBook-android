package ru.maxgog.pagebook.repositories

import kotlinx.coroutines.flow.Flow
import ru.maxgog.pagebook.dao.NoteDao
import ru.maxgog.pagebook.models.NoteModel

class NotesRepository(private val noteDao: NoteDao) {
    val allNotes: Flow<List<NoteModel>> = noteDao.getAll()

    suspend fun insert(note: NoteModel) = noteDao.insert(note)
    suspend fun update(note: NoteModel) = noteDao.update(note)
    suspend fun delete(note: NoteModel) = noteDao.delete(note)
}