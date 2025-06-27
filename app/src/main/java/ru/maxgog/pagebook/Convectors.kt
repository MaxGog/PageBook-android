package ru.maxgog.pagebook

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.TypeConverter
import kotlinx.datetime.LocalDate
import java.time.LocalDate as JavaLocalDate

class Converters {
    @TypeConverter
    fun fromLocalDate(value: LocalDate?): String? {
        return value?.toString()
    }
    @TypeConverter
    fun toLocalDate(value: String?): LocalDate? {
        return value?.let { LocalDate.parse(it) }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun JavaLocalDate.toKotlinxLocalDate(): LocalDate {
    return LocalDate(year, monthValue, dayOfMonth)
}