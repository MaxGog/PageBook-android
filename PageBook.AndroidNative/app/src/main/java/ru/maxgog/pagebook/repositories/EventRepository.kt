package ru.maxgog.pagebook.repositories

import ru.maxgog.pagebook.dao.EventDao
import ru.maxgog.pagebook.models.EventModel
import java.util.Date

class EventRepository(private val eventDao: EventDao) {
    suspend fun insert(event: EventModel) = eventDao.insert(event)
    suspend fun delete(event: EventModel) = eventDao.delete(event)
    suspend fun getAllEvents(): List<EventModel> = eventDao.getAllEvents()
}