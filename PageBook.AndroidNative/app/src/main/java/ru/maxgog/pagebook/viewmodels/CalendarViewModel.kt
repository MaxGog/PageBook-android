package ru.maxgog.pagebook.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import io.github.boguszpawlowski.composecalendar.kotlinxDateTime.now
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate
import ru.maxgog.pagebook.models.EventModel
import ru.maxgog.pagebook.models.WeatherData
import ru.maxgog.pagebook.repositories.EventsRepository
//import ru.maxgog.pagebook.repositories.WeatherRepository

class CalendarViewModel(
    private val eventsRepository: EventsRepository,
    //private val weatherRepository: WeatherRepository
) : ViewModel() {
    private val _selectedDate = mutableStateOf(LocalDate.now())
    val selectedDate: State<LocalDate> = _selectedDate

    private val _events = mutableStateOf(emptyList<EventModel>())
    val events: State<List<EventModel>> = _events

    //private val _weather = mutableStateOf(emptyList<WeatherData>())
    //val weather: State<List<WeatherData>> = _weather

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    init {
        loadEventsForDate(_selectedDate.value)
        loadWeatherForecast()
    }

    fun selectDate(date: LocalDate) {
        _selectedDate.value = date
        loadEventsForDate(date)
    }

    private fun loadEventsForDate(date: LocalDate) {
        viewModelScope.launch {
            eventsRepository.getEventsForDate(date).collect { events ->
                _events.value = events
            }
        }
    }

    /*private fun loadWeatherForecast() {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                _weather.value = weatherRepository.getWeatherForecast("Moscow")
            } finally {
                _isLoading.value = false
            }
        }
    }*/

    fun addEvent(event: EventModel) {
        viewModelScope.launch {
            eventsRepository.addEvent(event)
            loadEventsForDate(event.dateTime.toLocalDate())
        }
    }

    fun deleteEvent(event: EventModel) {
        viewModelScope.launch {
            eventsRepository.deleteEvent(event)
            loadEventsForDate(event.dateTime.toLocalDate())
        }
    }

}

class CalendarViewModelFactory(
    private val eventsRepository: EventsRepository,
    //private val weatherRepository: WeatherRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CalendarViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CalendarViewModel(eventsRepository, /*weatherRepository*/) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}