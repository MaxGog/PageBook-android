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
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneId
import ru.maxgog.pagebook.R
import ru.maxgog.pagebook.models.TodoModel
import ru.maxgog.pagebook.ui.dialogs.AddTodoDialog
import ru.maxgog.pagebook.ui.dialogs.DateTimePickerDialog
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