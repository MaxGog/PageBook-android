package ru.maxgog.pagebook.ui

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import io.github.boguszpawlowski.composecalendar.kotlinxDateTime.YearMonth
import io.github.boguszpawlowski.composecalendar.rememberCalendarState
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.LocalDate

import ru.maxgog.pagebook.models.EventModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CalendarView(
    selectedDate: LocalDate,
    onDateSelected: (LocalDate) -> Unit,
    events: List<EventModel>
) {
    val calendarState = rememberCalendarState(
        startMonth = YearMonth.now().minusMonths(12),
        endMonth = YearMonth.now().plusMonths(12),
        firstVisibleMonth = YearMonth.from(selectedDate),
        firstDayOfWeek = DayOfWeek.MONDAY
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface)
    ) {
        CalendarTitle(selectedDate)

        HorizontalCalendar(
            state = calendarState,
            dayContent = { day ->
                val isSelected = day.date == selectedDate
                val hasEvents = events.any { it.dateTime.toLocalDate() == day.date }

                Day(day, isSelected, hasEvents) {
                    onDateSelected(day.date)
                }
            },
            monthHeader = { month ->
                MonthHeader(month)
            }
        )
    }
}

@Composable
private fun Day(
    day: CalendarDay,
    isSelected: Boolean,
    hasEvents: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .aspectRatio(1f)
            .padding(2.dp)
            .background(
                color = when {
                    isSelected -> MaterialTheme.colorScheme.primary
                    day.date.isToday() -> MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)
                    else -> Color.Transparent
                },
                shape = CircleShape
            )
            .clickable { onClick() },
        contentAlignment = Center
    ) {
        Column(
            horizontalAlignment = CenterHorizontally
        ) {
            Text(
                text = day.date.dayOfMonth.toString(),
                color = when {
                    isSelected -> MaterialTheme.colorScheme.onPrimary
                    day.date.month != day.month -> MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f)
                    else -> MaterialTheme.colorScheme.onSurface
                },
                fontWeight = if (day.date.isToday()) FontWeight.Bold else FontWeight.Normal
            )

            if (hasEvents) {
                Spacer(modifier = Modifier.height(2.dp))
                Box(
                    modifier = Modifier
                        .size(4.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.primary)
                )
            }
        }
    }
}