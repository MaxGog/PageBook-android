package ru.maxgog.pagebook.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import io.github.boguszpawlowski.composecalendar.kotlinxDateTime.now
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate
import ru.maxgog.pagebook.models.EventModel
import ru.maxgog.pagebook.repositories.EventRepository

class CalendarViewModel(
    private val repository: EventRepository
) : ViewModel() {
    private val _selectedDate = MutableStateFlow(LocalDate.now())
    val selectedDate: StateFlow<LocalDate> = _selectedDate.asStateFlow()

    private val _events = MutableStateFlow<List<EventModel>>(emptyList())
    val events: StateFlow<List<EventModel>> = _events.asStateFlow()

    private val _showDialog = MutableStateFlow(false)
    val showDialog: StateFlow<Boolean> = _showDialog.asStateFlow()

    private val _dialogDate = MutableStateFlow(LocalDate.now())
    val dialogDate: StateFlow<LocalDate> = _dialogDate.asStateFlow()

    init {
        loadEvents()
    }

    fun onDateSelected(date: LocalDate) {
        _selectedDate.value = date
    }

    fun showAddEventDialog(date: LocalDate) {
        _dialogDate.value = date
        _showDialog.value = true
    }

    fun hideAddEventDialog() {
        _showDialog.value = false
    }

    fun addEvent(event: EventModel) {
        viewModelScope.launch {
            repository.insert(event)
            loadEvents()
        }
    }

    fun deleteEvent(event: EventModel) {
        viewModelScope.launch {
            repository.delete(event)
            loadEvents()
        }
    }

    private fun loadEvents() {
        viewModelScope.launch {
            _events.value = repository.getAllEvents()
        }
    }
}

class CalendarViewModelFactory(
    private val eventsRepository: EventRepository,
    //private val weatherRepository: WeatherRepository? = null
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CalendarViewModel::class.java)) {
            return CalendarViewModel(eventsRepository, /*weatherRepository*/) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}