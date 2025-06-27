package ru.maxgog.pagebook

import androidx.room.TypeConverter
import kotlinx.datetime.LocalDate
import java.time.LocalDate as JavaLocalDate

class Converters {
    @TypeConverter
    fun fromLocalDate(value: LocalDate?): String? = value?.toString()

    @TypeConverter
    fun toLocalDate(value: String?): LocalDate? = value?.let(LocalDate::parse)
}

fun JavaLocalDate.toKotlinxLocalDate(): LocalDate = LocalDate(year, monthValue, dayOfMonth)