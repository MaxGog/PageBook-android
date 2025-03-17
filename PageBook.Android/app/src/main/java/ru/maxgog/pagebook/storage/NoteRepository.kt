package ru.maxgog.pagebook.storage

import androidx.lifecycle.LiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import ru.maxgog.pagebook.models.NoteModel

class NoteRepository(private val noteDao: NoteDao) {
    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    val noteList: LiveData<List<NoteModel>> = noteDao.getNotes()
    var note: NoteModel
        get() { return note }
        set(editNote: NoteModel) { note = editNote }

    fun getNote(id:Int) {
        coroutineScope.launch(Dispatchers.IO) {
            note = noteDao.getNote(id)
        }
    }

    fun addNote(note: NoteModel) {
        coroutineScope.launch(Dispatchers.IO) {
            noteDao.addNewNote(note)
        }
    }

    fun deleteNote(id:Int) {
        coroutineScope.launch(Dispatchers.IO) {
            noteDao.deleteNote(id)
        }
    }
}