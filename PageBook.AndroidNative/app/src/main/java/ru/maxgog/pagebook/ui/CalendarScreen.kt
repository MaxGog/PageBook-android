package ru.maxgog.pagebook.ui

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Divider
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DatePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import io.github.boguszpawlowski.composecalendar.kotlinxDateTime.now
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import ru.maxgog.pagebook.models.EventModel
import ru.maxgog.pagebook.models.WeatherData
import ru.maxgog.pagebook.viewmodels.CalendarViewModel
import java.time.YearMonth
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalendarScreen(
    viewModel: CalendarViewModel
) {
    val selectedDate by viewModel.selectedDate
    val events by viewModel.events
    val weather by viewModel.weather
    val isLoading by viewModel.isLoading

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Календарь
        CalendarView(
            selectedDate = selectedDate,
            onDateSelected = { date ->
                viewModel.selectDate(date)
            },
            events = emptyList() // Здесь можно передать все события для подсветки дат
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Погода
        WeatherSection(
            weather = weather.find { it.date == selectedDate },
            isLoading = isLoading
        )

        Spacer(modifier = Modifier.height(16.dp))

        // События
        EventsSection(
            events = events,
            onAddEvent = { event ->
                viewModel.addEvent(event)
            },
            onDeleteEvent = { event ->
                viewModel.deleteEvent(event)
            }
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CalendarTitle(date: LocalDate) {
    Text(
        text = date.format(DateTimeFormatter.ofPattern("MMMM yyyy")),
        style = MaterialTheme.typography.titleLarge,
        modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp)
    )
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MonthHeader(month: YearMonth) {
    Text(
        text = month.format(DateTimeFormatter.ofPattern("MMMM yyyy")),
        style = MaterialTheme.typography.titleMedium,
        modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp)
    )
}

@Composable
fun WeatherSection(
    weather: WeatherData?,
    isLoading: Boolean
) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Погода",
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(8.dp))

            if (isLoading) {
                CircularProgressIndicator()
            } else if (weather != null) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Здесь можно использовать иконку погоды
                    Icon(
                        imageVector = Icons.Default.WbSunny,
                        contentDescription = "Погода",
                        modifier = Modifier.size(40.dp)
                    )

                    Spacer(modifier = Modifier.width(16.dp))

                    Column {
                        Text(
                            text = "${weather.temperature}°C",
                            style = MaterialTheme.typography.displaySmall
                        )
                        Text(
                            text = weather.condition,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            } else {
                Text(
                    text = "Нет данных о погоде",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

@Composable
fun EventsSection(
    events: List<EventModel>,
    onAddEvent: (EventModel) -> Unit,
    onDeleteEvent: (EventModel) -> Unit
) {
    var showAddDialog by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "События",
                    style = MaterialTheme.typography.titleMedium
                )

                IconButton(onClick = { showAddDialog = true }) {
                    Icon(Icons.Default.Add, contentDescription = "Добавить событие")
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            if (events.isEmpty()) {
                Text(
                    text = "Нет событий на выбранную дату",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            } else {
                LazyColumn {
                    items(events) { event ->
                        EventItem(
                            event = event,
                            onDelete = { onDeleteEvent(event) }
                        )
                        Divider()
                    }
                }
            }
        }
    }

    if (showAddDialog) {
        AddEventDialog(
            onDismiss = { showAddDialog = false },
            onConfirm = { event ->
                onAddEvent(event)
                showAddDialog = false
            }
        )
    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEventDialog(
    onDismiss: () -> Unit,
    onConfirm: (EventModel) -> Unit
) {
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var selectedDateTime by remember { mutableStateOf(LocalDateTime.now()) }
    var selectedColor by remember { mutableStateOf(Color.Blue.toArgb()) }

    val colors = listOf(
        Color.Red.toArgb(),
        Color.Blue.toArgb(),
        Color.Green.toArgb(),
        Color.Yellow.toArgb(),
        Color.Magenta.toArgb()
    )

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

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Время: ", modifier = Modifier.padding(end = 8.dp))
                    val timePickerState = rememberTimePickerState(
                        initialHour = selectedDateTime.hour,
                        initialMinute = selectedDateTime.minute
                    )
                    TimePicker(state = timePickerState)

                    val datePickerState = rememberDatePickerState(
                        initialSelectedDateMillis = selectedDateTime
                            .atZone(ZoneId.systemDefault())
                            .toInstant()
                            .toEpochMilli()
                    )
                    DatePicker(state = datePickerState)
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text("Цвет: ")
                Row {
                    colors.forEach { color ->
                        Box(
                            modifier = Modifier
                                .size(24.dp)
                                .background(Color(color), shape = CircleShape)
                                .clickable { selectedColor = color }
                                .border(
                                    width = if (color == selectedColor) 2.dp else 0.dp,
                                    color = Color.Black,
                                    shape = CircleShape
                                )
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                    }
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    // Получаем выбранную дату из DatePicker
                    val selectedDateMillis = datePickerState.selectedDateMillis
                    val selectedDate = selectedDateMillis?.let { millis ->
                        Instant.ofEpochMilli(millis)
                            .atZone(ZoneId.systemDefault())
                            .toLocalDate()
                    } ?: LocalDate.now()

                    // Получаем выбранное время из TimePicker
                    val selectedTime = LocalTime.of(
                        timePickerState.hour,
                        timePickerState.minute
                    )

                    // Собираем LocalDateTime
                    val dateTime = LocalDateTime.of(selectedDate, selectedTime)

                    onConfirm(
                        EventModel(
                            title = title,
                            description = description,
                            dateTime = dateTime,
                            color = selectedColor
                        )
                    )
                },
                enabled = title.isNotBlank()
            ) {
                Text("Добавить")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Отмена")
            }
        }
    )
}