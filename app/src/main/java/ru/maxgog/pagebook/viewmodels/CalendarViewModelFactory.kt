package ru.maxgog.pagebook.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.maxgog.pagebook.repositories.EventRepository

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