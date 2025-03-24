package ru.maxgog.pagebook.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import ru.maxgog.pagebook.models.NoteModel
import ru.maxgog.pagebook.storage.NoteRepository
import ru.maxgog.pagebook.storage.NoteRoomDatabase
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class NotesViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: NoteRepository
    val allNotes: LiveData<List<NoteModel>>

    init {
        val dao = NoteRoomDatabase.getInstance(application).noteDao()
        repository = NoteRepository(dao)
        allNotes = repository.allNotes
    }

    fun addNote(note: NoteModel) = viewModelScope.launch {
        repository.addNote(note)
    }

    fun updateNote(note: NoteModel) = viewModelScope.launch {
        repository.updateNote(note)
    }

    fun deleteNote(note: NoteModel) = viewModelScope.launch {
        repository.deleteNote(note)
    }
}