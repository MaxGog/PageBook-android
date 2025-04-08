package ru.maxgog.pagebook.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.LocalDateTime
import ru.maxgog.pagebook.models.EventModel

@Dao
interface EventDao {
    @Query("SELECT * FROM events ORDER BY dateTime ASC")
    fun getAllEvents(): Flow<List<EventModel>>

    @Query("SELECT * FROM events WHERE dateTime BETWEEN :start AND :end ORDER BY dateTime ASC")
    fun getEventsBetween(start: LocalDateTime, end: LocalDateTime): Flow<List<EventModel>>

    @Insert
    suspend fun insert(event: EventModel)

    @Update
    suspend fun update(event: EventModel)

    @Delete
    suspend fun delete(event: EventModel)
}