package ru.maxgog.pagebook.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import jakarta.inject.Inject
import kotlinx.coroutines.launch
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import ru.maxgog.pagebook.PageBookApplication
import ru.maxgog.pagebook.models.EventModel
import ru.maxgog.pagebook.models.TodoModel
import ru.maxgog.pagebook.repositories.EventRepository
import ru.maxgog.pagebook.repositories.TodoRepository

class TodoViewModel @Inject constructor(
    private val repository: TodoRepository,
    private val eventRepository: EventRepository
) : ViewModel() {

    val allTodos = repository.allTodos

    fun insert(todo: TodoModel) = viewModelScope.launch {
        repository.insert(todo)
        if (todo.hasReminder) addToCalendar(todo)
    }

    fun update(todo: TodoModel) = viewModelScope.launch {
        repository.update(todo)
    }

    fun delete(todo: TodoModel) = viewModelScope.launch {
        repository.delete(todo)
    }

    fun addToCalendar(todo: TodoModel) = viewModelScope.launch {
        if (shouldAddToCalendar(todo)) {
            val (date, time) = convertReminderTime(todo.reminderTime!!)

            eventRepository.insert(createEventFromTodo(todo, date, time))
            repository.update(todo.copy(isAddedToCalendar = true))
        }
    }

    private fun shouldAddToCalendar(todo: TodoModel): Boolean =
        todo.hasReminder && todo.reminderTime != null && !todo.isAddedToCalendar

    private fun convertReminderTime(reminderTime: Long): Pair<LocalDate, String> {
        val localDateTime = Instant.fromEpochMilliseconds(reminderTime)
            .toLocalDateTime(TimeZone.currentSystemDefault())
        return localDateTime.date to "${localDateTime.hour}:${localDateTime.minute}"
    }

    private fun createEventFromTodo(todo: TodoModel, date: LocalDate, time: String): EventModel =
        EventModel(
            title = todo.title,
            description = todo.description ?: "Todo reminder",
            date = date,
            time = time
        )

    companion object {
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                val application = PageBookApplication.getApplication()
                return TodoViewModel(application.todoRepository, application.eventsRepository) as T
            }
        }
    }
}