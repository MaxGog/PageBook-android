package ru.maxgog.pagebook.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import jakarta.inject.Inject
import kotlinx.coroutines.launch
import ru.maxgog.pagebook.PageBookApplication
import ru.maxgog.pagebook.models.NoteModel
import ru.maxgog.pagebook.repositories.NotesRepository

class NotesViewModel @Inject constructor(
    private val repository: NotesRepository
) : ViewModel() {

    val allNotes = repository.allNotes

    fun insert(note: NoteModel) = viewModelScope.launch { repository.insert(note) }

    fun update(note: NoteModel) = viewModelScope.launch { repository.update(note) }

    fun delete(note: NoteModel) = viewModelScope.launch { repository.delete(note) }

    companion object {
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T =
                NotesViewModel(PageBookApplication.getApplication().notesRepository) as T
        }
    }
}