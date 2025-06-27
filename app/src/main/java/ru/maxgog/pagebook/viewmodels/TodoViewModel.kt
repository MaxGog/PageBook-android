package ru.maxgog.pagebook.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.maxgog.pagebook.models.TodoModel
import ru.maxgog.pagebook.repositories.TodoRepository

class TodoViewModel(private val repository: TodoRepository) : ViewModel() {
    val allTodos = repository.allTodos

    fun insert(todo: TodoModel) = viewModelScope.launch {
        repository.insert(todo)
    }

    fun update(todo: TodoModel) = viewModelScope.launch {
        repository.update(todo)
    }

    fun delete(todo: TodoModel) = viewModelScope.launch {
        repository.delete(todo)
    }
}

class TodoViewModelFactory(private val repository: TodoRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TodoViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TodoViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}