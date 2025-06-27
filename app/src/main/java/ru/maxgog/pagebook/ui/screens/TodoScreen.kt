package ru.maxgog.pagebook.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
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
fun TodoScreen(viewModel: TodoViewModel) {
    val todos by viewModel.allTodos.collectAsState(initial = emptyList())
    var showDialog by remember { mutableStateOf(false) }
    var newTodoTitle by remember { mutableStateOf("") }
    var newTodoDescription by remember { mutableStateOf("") }
    var showDatePicker by remember { mutableStateOf(false) }
    var selectedDate by remember { mutableStateOf<LocalDate?>(null) }
    var selectedTime by remember { mutableStateOf<LocalTime?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.todo_list)) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { showDialog = true }) {
                Icon(Icons.Default.Add, contentDescription = stringResource(R.string.add))
            }
        }
    ) { padding ->
        LazyColumn(modifier = Modifier.padding(padding)) {
            items(todos.size) { index ->
                TodoItem(
                    todo = todos[index],
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


    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text(stringResource(R.string.add)) },
            text = {
                Column {
                    TextField(
                        value = newTodoTitle,
                        onValueChange = { newTodoTitle = it },
                        label = { Text(stringResource(R.string.title)) },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    TextField(
                        value = newTodoDescription,
                        onValueChange = { newTodoDescription = it },
                        label = { Text(stringResource(R.string.description)) },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(
                        onClick = { showDatePicker = true },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            if (selectedDate != null && selectedTime != null)
                                "Reminder: ${selectedDate!!} ${selectedTime!!.format(
                                    DateTimeFormatter.ofPattern("HH:mm"))}"
                            else
                                "Set Reminder"
                        )
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (newTodoTitle.isNotBlank()) {
                            val reminderTime = if (selectedDate != null && selectedTime != null) {
                                selectedDate!!.atTime(selectedTime!!).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
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
                ) {
                    Text(stringResource(R.string.add))
                }
            },
            dismissButton = {
                Button(
                    onClick = { showDialog = false }
                ) {
                    Text(stringResource(R.string.cancel))
                }
            }
        )
    }

    if (showDatePicker) {
        val datePickerState = rememberDatePickerState()
        val timePickerState = rememberTimePickerState()

        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                Button(
                    onClick = {
                        selectedDate = Instant.ofEpochMilli(datePickerState.selectedDateMillis!!)
                            .atZone(ZoneId.systemDefault())
                            .toLocalDate()
                        selectedTime = LocalTime.of(timePickerState.hour, timePickerState.minute)
                        showDatePicker = false
                    }
                ) {
                    Text("OK")
                }
            },
            dismissButton = {
                Button(
                    onClick = { showDatePicker = false }
                ) {
                    Text("Cancel")
                }
            }
        ) {
            Column {
                DatePicker(state = datePickerState)
                Spacer(modifier = Modifier.height(16.dp))
                TimePicker(state = timePickerState)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TodoPreview() {
    TodoListAppTheme {
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