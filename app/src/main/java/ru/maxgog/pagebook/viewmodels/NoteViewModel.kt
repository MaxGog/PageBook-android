package ru.maxgog.pagebook.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.maxgog.pagebook.models.NoteModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.maxgog.pagebook.repositories.NotesRepository

class NotesViewModel(private val repository: NotesRepository) : ViewModel() {
    val allNotes = repository.allNotes

    fun insert(note: NoteModel) = viewModelScope.launch {
        repository.insert(note)
    }

    fun update(note: NoteModel) = viewModelScope.launch {
        repository.update(note)
    }

    fun delete(note: NoteModel) = viewModelScope.launch {
        repository.delete(note)
    }
}