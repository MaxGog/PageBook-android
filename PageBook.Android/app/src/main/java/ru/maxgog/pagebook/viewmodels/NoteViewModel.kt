package ru.maxgog.pagebook.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

import ru.maxgog.pagebook.models.NoteModel
import ru.maxgog.pagebook.storage.NoteRepository
import ru.maxgog.pagebook.storage.NoteDatabase

class NoteViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: NoteRepository

    val allNotes: LiveData<List<NoteModel>>

    init {
        val noteDao = NoteDatabase.getDatabase(application).noteDao()
        repository = NoteRepository(noteDao)
        allNotes = repository.allNotes
    }

    fun insert(note: NoteModel) = viewModelScope.launch {
        repository.insert(note)
    }
}