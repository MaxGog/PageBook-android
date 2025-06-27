package ru.maxgog.pagebook.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import ru.maxgog.pagebook.models.EventModel
import ru.maxgog.pagebook.models.TodoModel
import ru.maxgog.pagebook.repositories.EventRepository
import ru.maxgog.pagebook.repositories.TodoRepository

class TodoViewModel(
    private val repository: TodoRepository,
    private val eventRepository: EventRepository
) : ViewModel() {
    val allTodos = repository.allTodos

    fun insert(todo: TodoModel) = viewModelScope.launch {
        repository.insert(todo)
        if (todo.hasReminder) {
            addToCalendar(todo)
        }
    }

    fun update(todo: TodoModel) = viewModelScope.launch {
        repository.update(todo)
    }

    fun delete(todo: TodoModel) = viewModelScope.launch {
        repository.delete(todo)
    }

    fun addToCalendar(todo: TodoModel) = viewModelScope.launch {
        if (todo.hasReminder && todo.reminderTime != null && !todo.isAddedToCalendar) {
            val instant = Instant.fromEpochMilliseconds(todo.reminderTime)
            val localDateTime = instant.toLocalDateTime(TimeZone.currentSystemDefault())

            val event = EventModel(
                title = todo.title,
                description = todo.description ?: "Todo reminder",
                date = localDateTime.date,
                time = "${localDateTime.hour}:${localDateTime.minute}"
            )

            eventRepository.insert(event)
            repository.update(todo.copy(isAddedToCalendar = true))
        }
    }
}