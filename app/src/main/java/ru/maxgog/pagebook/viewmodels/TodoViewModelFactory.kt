package ru.maxgog.pagebook.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.maxgog.pagebook.repositories.EventRepository
import ru.maxgog.pagebook.repositories.TodoRepository

class TodoViewModelFactory(
    private val repository: TodoRepository,
    private val eventRepository: EventRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TodoViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TodoViewModel(repository, eventRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}