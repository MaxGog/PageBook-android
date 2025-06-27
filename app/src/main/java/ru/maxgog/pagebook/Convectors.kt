package ru.maxgog.pagebook

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.TypeConverter
import kotlinx.datetime.LocalDate
import java.time.LocalDate as JavaLocalDate
import kotlinx.datetime.toJavaLocalDate

class Converters {
    @TypeConverter
    fun fromTimestamp(value: String?): LocalDate? {
        return value?.let { LocalDate.parse(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: LocalDate?): String? {
        return date?.toString()
    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun LocalDate.toJavaLocalDate(): JavaLocalDate {
    return JavaLocalDate.of(year, monthNumber, dayOfMonth)
}

@RequiresApi(Build.VERSION_CODES.O)
fun JavaLocalDate.toKotlinxLocalDate(): LocalDate {
    return LocalDate(year, monthValue, dayOfMonth)
}