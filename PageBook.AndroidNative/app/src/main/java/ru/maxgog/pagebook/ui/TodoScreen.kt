package ru.maxgog.pagebook.ui

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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults

import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.maxgog.pagebook.models.TodoModel

import ru.maxgog.pagebook.viewmodels.TodoViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoScreen(viewModel: TodoViewModel) {
    val todos by viewModel.allTodos.collectAsState(initial = emptyList())
    var showDialog by remember { mutableStateOf(false) }
    var newTodoTitle by remember { mutableStateOf("") }
    var newTodoDescription by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("To-Do List") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { showDialog = true }) {
                Icon(Icons.Default.Add, contentDescription = "Add Todo")
            }
        }
    ) { padding ->
        // Остальной код остается таким же
        LazyColumn(modifier = Modifier.padding(padding)) {
            items(todos.size) { index ->
                TodoItem(
                    todo = todos[index],
                    onTodoClick = { updatedTodo ->
                        viewModel.update(updatedTodo)
                    },
                    onDeleteClick = { todoToDelete ->
                        viewModel.delete(todoToDelete)
                    }
                )
            }
        }
    }


    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Add New Task") },
            text = {
                Column {
                    TextField(
                        value = newTodoTitle,
                        onValueChange = { newTodoTitle = it },
                        label = { Text("Title") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    TextField(
                        value = newTodoDescription,
                        onValueChange = { newTodoDescription = it },
                        label = { Text("Description") },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (newTodoTitle.isNotBlank()) {
                            viewModel.insert(
                                TodoModel(
                                    title = newTodoTitle,
                                    description = newTodoDescription
                                )
                            )
                            newTodoTitle = ""
                            newTodoDescription = ""
                            showDialog = false
                        }
                    }
                ) {
                    Text("Add")
                }
            },
            dismissButton = {
                Button(
                    onClick = { showDialog = false }
                ) {
                    Text("Cancel")
                }
            }
        )
    }
}