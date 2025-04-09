package ru.maxgog.pagebook.ui

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import io.github.boguszpawlowski.composecalendar.Calendar
import io.github.boguszpawlowski.composecalendar.header.MonthState
import io.github.boguszpawlowski.composecalendar.rememberCalendarState
import kotlinx.coroutines.launch
import ru.maxgog.pagebook.models.EventModel
import ru.maxgog.pagebook.viewmodels.CalendarViewModel
import java.time.format.TextStyle
import java.util.Locale
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.State
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import java.time.LocalDate

import io.github.boguszpawlowski.composecalendar.rememberCalendarState
import io.github.boguszpawlowski.composecalendar.Calendar
import io.github.boguszpawlowski.composecalendar.day.CalendarDay
import io.github.boguszpawlowski.composecalendar.header.MonthState

@OptIn(ExperimentalFoundationApi::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CalendarScreen(
    viewModel: CalendarViewModel
) {
    val state = rememberCalendarState()
    val coroutineScope = rememberCoroutineScope()
    val selectedDate by viewModel.selectedDate.collectAsState()
    val events by viewModel.events.collectAsState()

    Column(modifier = Modifier.fillMaxSize()) {
        Calendar(
            state = state,
            dayContent = { day ->
                val dayEvents = events.filter { event ->
                    event.date.toLocalDate() == day.date
                }

                Day(
                    day = day,
                    isSelected = day.date == selectedDate,
                    onClick = {
                        coroutineScope.launch {
                            state.animateScrollToMonth(day.date.yearMonth)
                        }
                        viewModel.onDateSelected(day.date)
                    },
                    events = dayEvents
                )
            },
            monthHeader = { monthState ->
                MonthHeader(monthState)
            },
            modifier = Modifier.weight(1f)
        )

        val selectedEvents = events.filter { event ->
            event.date.toLocalDate() == selectedDate
        }

        if (selectedEvents.isNotEmpty()) {
            LazyColumn(modifier = Modifier.weight(1f)) {
                items(selectedEvents) { count ->
                    EventItem(event = events[count], onDelete = {
                        viewModel.deleteEvent(events[count])
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
                color = if (isSelected) MaterialTheme.colors.primary.copy(alpha = 0.3f)
                else Color.Transparent,
                shape = CircleShape
            )
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = day.date.dayOfMonth.toString(),
                color = if (day.isFromCurrentMonth) MaterialTheme.colors.onSurface
                else MaterialTheme.colors.onSurface.copy(alpha = 0.5f)
            )

            if (events.isNotEmpty()) {
                Spacer(modifier = Modifier.height(2.dp))
                Box(
                    modifier = Modifier
                        .size(6.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colors.primary)
                )
            }
        }
    }
}

@Composable
fun MonthHeader(monthState: MonthState) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = monthState.yearMonth.month.displayName(
                TextStyle.FULL_STANDALONE,
                Locale.getDefault()
            ).replaceFirstChar { it.titlecase() } + " " + monthState.yearMonth.year,
            style = MaterialTheme.typography.h6,
            modifier = Modifier.padding(16.dp)
        )
    }
}

@Composable
fun EventItem(event: EventModel, onDelete: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = 4.dp
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = event.title,
                    style = MaterialTheme.typography.h6
                )
                IconButton(onClick = onDelete) {
                    Icon(Icons.Default.Delete, contentDescription = "Удалить")
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = event.description)
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Время: ${event.time}",
                style = MaterialTheme.typography.caption
            )
        }
    }
}

@Composable
fun AddEventDialog(
    date: LocalDate,
    onDismiss: () -> Unit,
    onConfirm: (title: String, description: String, date: LocalDate, time: String) -> Unit
) {
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var time by remember { mutableStateOf("12:00") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Добавить событие") },
        text = {
            Column {
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Название") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Описание") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = time,
                    onValueChange = { time = it },
                    label = { Text("Время (HH:mm)") },
                    modifier = Modifier.fillMaxWidth()
                )
                Text(
                    text = "Дата: ${date}",
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (title.isNotBlank()) {
                        onConfirm(title, description, date, time)
                        onDismiss()
                    }
                }
            ) {
                Text("Добавить")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text("Отмена")
            }
        }
    )
}