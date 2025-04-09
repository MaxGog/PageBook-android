package ru.maxgog.pagebook.ui

import android.annotation.SuppressLint
import ru.maxgog.pagebook.models.EventModel
import ru.maxgog.pagebook.ui.dialogs.AddEventDialog
import ru.maxgog.pagebook.ui.items.EventItem
import ru.maxgog.pagebook.viewmodels.CalendarViewModel

import android.os.Build
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.remember
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import kotlinx.coroutines.launch
import com.kizitonwose.calendar.compose.CalendarState
import com.kizitonwose.calendar.compose.VerticalCalendar
import com.kizitonwose.calendar.compose.rememberCalendarState
import com.kizitonwose.calendar.core.CalendarDay
import com.kizitonwose.calendar.core.CalendarMonth
import com.kizitonwose.calendar.core.yearMonth
import ru.maxgog.pagebook.toKotlinxLocalDate
import java.time.YearMonth
import java.time.format.TextStyle
import java.time.temporal.WeekFields
import java.util.Locale

@SuppressLint("StateFlowValueCalledInComposition")
@OptIn(ExperimentalFoundationApi::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CalendarScreen(
    viewModel: CalendarViewModel
) {
    val currentMonth = remember { YearMonth.now() }
    val startMonth = remember { currentMonth.minusMonths(12) }
    val endMonth = remember { currentMonth.plusMonths(12) }
    val state = rememberCalendarState(
        startMonth = startMonth,
        endMonth = endMonth,
        firstVisibleMonth = currentMonth
    )

    val coroutineScope = rememberCoroutineScope()
    val selectedDate by viewModel.selectedDate.collectAsState()
    val events by viewModel.events.collectAsState()

    Column(modifier = Modifier.fillMaxSize()) {
        VerticalCalendar(
            state = state,
            modifier = Modifier.weight(1f),
            dayContent = { day ->
                val dayEvents = events.filter { event ->
                    event.date == day.date
                }

                Day(
                    day = day,
                    isSelected = day.date == selectedDate,
                    onClick = {
                        coroutineScope.launch {
                            state.animateScrollToMonth(day.date.yearMonth)
                        }
                        viewModel.onDateSelected(day.date.toKotlinxLocalDate())
                    },
                    events = dayEvents
                )
            },
            monthHeader = { month ->
                MonthHeader(month.yearMonth)
            }
        )

        val selectedEvents = events.filter { event ->
            event.date == selectedDate
        }

        if (selectedEvents.isNotEmpty()) {
            LazyColumn(modifier = Modifier.weight(1f)) {
                items(selectedEvents) { event ->
                    EventItem(event = event, onDelete = {
                        viewModel.deleteEvent(event)
                    })
                }
            }
        } else {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Text("Нет событий на выбранную дату")
            }
        }

        ExtendedFloatingActionButton(
            onClick = { viewModel.showAddEventDialog(selectedDate) },
            modifier = Modifier
                .align(Alignment.End)
                .padding(16.dp)
        ) {
            Icon(Icons.Default.Add, contentDescription = "Добавить событие")
            Spacer(modifier = Modifier.width(8.dp))
            Text("Добавить событие")
        }
    }

    if (viewModel.showDialog.value) {
        AddEventDialog(
            date = viewModel.dialogDate.value,
            onDismiss = { viewModel.hideAddEventDialog() },
            onConfirm = { title, description, date, time ->
                viewModel.addEvent(
                    EventModel(
                        title = title,
                        description = description,
                        date = date,
                        time = time
                    )
                )
            }
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun WeekHeaders() {
    val daysOfWeek = remember { WeekFields.of(Locale.getDefault()).firstDayOfWeek.let { firstDay ->
        (0 until 7).map { firstDay.plus(it.toLong()) }
    }}

    Row(modifier = Modifier.fillMaxWidth()) {
        daysOfWeek.forEach { dayOfWeek ->
            Text(
                text = dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.getDefault()),
                modifier = Modifier.weight(1f).padding(8.dp),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.labelMedium
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MonthHeader(yearMonth: YearMonth) {
    Text(
        text = yearMonth.month.getDisplayName(
            TextStyle.FULL_STANDALONE,
            Locale.getDefault()
        ).replaceFirstChar { it.titlecase() } + " " + yearMonth.year,
        style = MaterialTheme.typography.headlineSmall,
        modifier = Modifier.padding(16.dp)
    )
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Day(
    day: CalendarDay,
    isSelected: Boolean,
    onClick: () -> Unit,
    events: List<EventModel>
) {
    Box(
        modifier = Modifier
            .aspectRatio(1f)
            .padding(4.dp)
            .background(
                color = if (isSelected) MaterialTheme.colorScheme.primary.copy(alpha = 0.3f)
                else Color.Transparent,
                shape = CircleShape
            )
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = day.date.dayOfMonth.toString(),
                color = MaterialTheme.colorScheme.onSurface
            )

            if (events.isNotEmpty()) {
                Spacer(modifier = Modifier.height(2.dp))
                Box(
                    modifier = Modifier
                        .size(6.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.primary)
                )
            }
        }
    }
}