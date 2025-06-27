package ru.maxgog.pagebook.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import java.time.Instant
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import ru.maxgog.pagebook.R
import ru.maxgog.pagebook.models.TodoModel
import ru.maxgog.pagebook.ui.items.TodoItem
import ru.maxgog.pagebook.ui.theme.TodoListAppTheme
import ru.maxgog.pagebook.viewmodels.TodoViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoScreen(
    viewModel: TodoViewModel,
    modifier: Modifier = Modifier
) {
    val todos by viewModel.allTodos.collectAsState(initial = emptyList())
    var showDialog by remember { mutableStateOf(false) }
    var newTodoTitle by remember { mutableStateOf("") }
    var newTodoDescription by remember { mutableStateOf("") }
    var showDateTimePicker by remember { mutableStateOf(false) }
    var selectedDate by remember { mutableStateOf<LocalDate?>(null) }
    var selectedTime by remember { mutableStateOf<LocalTime?>(null) }

    Scaffold(
        modifier = modifier,
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.todo_list),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surfaceContainer,
                    titleContentColor = MaterialTheme.colorScheme.onSurface
                )
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = { showDialog = true },
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.onPrimaryContainer
            ) {
                Icon(Icons.Default.Add, contentDescription = stringResource(R.string.add))
                Spacer(Modifier.width(8.dp))
                Text("Добавить заметку")
            }
        }
    ) { padding ->
        if (todos.isEmpty()) {
            EmptyState(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxWidth()
            )
        } else {
            LazyColumn(
                modifier = Modifier.padding(padding),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(
                    items = todos,
                    key = { it.id }
                ) { todo ->
                    TodoItem(
                        todo = todo,
                        onTodoClick = { updatedTodo ->
                            viewModel.update(updatedTodo)
                        },
                        onDeleteClick = { todoToDelete ->
                            viewModel.delete(todoToDelete)
                        },
                        onAddToCalendarClick = { todo ->
                            viewModel.addToCalendar(todo)
                        }
                    )
                }
            }
        }
    }

    if (showDialog) {
        AddTodoDialog(
            title = newTodoTitle,
            onTitleChange = { newTodoTitle = it },
            description = newTodoDescription,
            onDescriptionChange = { newTodoDescription = it },
            selectedDate = selectedDate,
            selectedTime = selectedTime,
            onDateTimeClick = { showDateTimePicker = true },
            onDismiss = { showDialog = false },
            onConfirm = {
                if (newTodoTitle.isNotBlank()) {
                    val reminderTime = if (selectedDate != null && selectedTime != null) {
                        selectedDate!!.atTime(selectedTime!!)
                            .atZone(ZoneId.systemDefault())
                            .toInstant()
                            .toEpochMilli()
                    } else null

                    viewModel.insert(
                        TodoModel(
                            title = newTodoTitle,
                            description = newTodoDescription,
                            hasReminder = reminderTime != null,
                            reminderTime = reminderTime
                        )
                    )
                    newTodoTitle = ""
                    newTodoDescription = ""
                    selectedDate = null
                    selectedTime = null
                    showDialog = false
                }
            }
        )
    }

    if (showDateTimePicker) {
        DateTimePickerDialog(
            onDismiss = { showDateTimePicker = false },
            onConfirm = { date, time ->
                selectedDate = date
                selectedTime = time
                showDateTimePicker = false
            }
        )
    }
}

@Composable
private fun EmptyState(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Нет задач",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(Modifier.height(8.dp))
        Text(
            text = "Добавить первую задачу",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AddTodoDialog(
    title: String,
    onTitleChange: (String) -> Unit,
    description: String,
    onDescriptionChange: (String) -> Unit,
    selectedDate: LocalDate?,
    selectedTime: LocalTime?,
    onDateTimeClick: () -> Unit,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Добавить задачу") },
        text = {
            Column {
                OutlinedTextField(
                    value = title,
                    onValueChange = onTitleChange,
                    label = { Text(stringResource(R.string.title)) },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = MaterialTheme.colorScheme.surface,
                        unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                        focusedTextColor = MaterialTheme.colorScheme.onSurface,
                        unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
                        focusedLabelColor = MaterialTheme.colorScheme.primary,
                        unfocusedLabelColor = MaterialTheme.colorScheme.onSurfaceVariant,
                        focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                        unfocusedIndicatorColor = MaterialTheme.colorScheme.outline
                    )
                )
                Spacer(Modifier.height(16.dp))
                OutlinedTextField(
                    value = description,
                    onValueChange = onDescriptionChange,
                    label = { Text(stringResource(R.string.description)) },
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = MaterialTheme.colorScheme.surface,
                        unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                        focusedTextColor = MaterialTheme.colorScheme.onSurface,
                        unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
                        focusedLabelColor = MaterialTheme.colorScheme.primary,
                        unfocusedLabelColor = MaterialTheme.colorScheme.onSurfaceVariant,
                        focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                        unfocusedIndicatorColor = MaterialTheme.colorScheme.outline
                    )
                )
                Spacer(Modifier.height(16.dp))
                FilledTonalButton(
                    onClick = onDateTimeClick,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        if (selectedDate != null && selectedTime != null) {
                            val dateFormat = DateTimeFormatter.ofPattern("dd MMM yyyy")
                            val timeFormat = DateTimeFormatter.ofPattern("HH:mm")
                            "Напоминание: ${selectedDate.format(dateFormat)} ${selectedTime.format(timeFormat)}"
                        } else {
                            "Поставить напоминание"
                        }
                    )
                }
            }
        },
        confirmButton = {
            Button(
                onClick = onConfirm,
                enabled = title.isNotBlank()
            ) {
                Text(stringResource(R.string.add))
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(stringResource(R.string.cancel))
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DateTimePickerDialog(
    onDismiss: () -> Unit,
    onConfirm: (LocalDate, LocalTime) -> Unit
) {
    val datePickerState = rememberDatePickerState()
    val timePickerState = rememberTimePickerState()

    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            Button(
                onClick = {
                    datePickerState.selectedDateMillis?.let { millis ->
                        val date = Instant.ofEpochMilli(millis)
                            .atZone(ZoneId.systemDefault())
                            .toLocalDate()
                        val time = LocalTime.of(timePickerState.hour, timePickerState.minute)
                        onConfirm(date, time)
                    }
                },
                enabled = datePickerState.selectedDateMillis != null
            ) {
                Text(stringResource(R.string.ok))
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(stringResource(R.string.cancel))
            }
        }
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 16.dp)
        ) {
            DatePicker(state = datePickerState)
            Spacer(Modifier.height(16.dp))
            TimePicker(state = timePickerState)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TodoPreview() {
    TodoListAppTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            TodoItem(
                todo = TodoModel(
                    title = stringResource(R.string.title),
                    description = stringResource(R.string.description)
                ),
                onTodoClick = {},
                onDeleteClick = {},
                onAddToCalendarClick = {}
            )
        }
    }
}