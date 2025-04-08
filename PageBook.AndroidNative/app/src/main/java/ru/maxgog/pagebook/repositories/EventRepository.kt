package ru.maxgog.pagebook.repositories

import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import kotlinx.datetime.atTime
import ru.maxgog.pagebook.dao.EventDao
import ru.maxgog.pagebook.models.EventModel

class EventsRepository(private val eventDao: EventDao) {
    fun getAllEvents(): Flow<List<EventModel>> = eventDao.getAllEvents()

    fun getEventsForDate(date: LocalDate): Flow<List<EventModel>> {
        val start = date.atStartOfDay()
        val end = date.atTime(LocalTime.MAX)
        return eventDao.getEventsBetween(start, end)
    }

    suspend fun addEvent(event: EventModel) {
        eventDao.insert(event)
    }

    suspend fun updateEvent(event: EventModel) {
        eventDao.update(event)
    }

    suspend fun deleteEvent(event: EventModel) {
        eventDao.delete(event)
    }
}
