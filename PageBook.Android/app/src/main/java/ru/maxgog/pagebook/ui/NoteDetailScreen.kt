package ru.maxgog.pagebook.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import ru.maxgog.pagebook.models.NoteModel
import ru.maxgog.pagebook.viewmodels.NotesViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteDetailScreen(
    note: NoteModel?,
    viewModel: NotesViewModel,
    onBack: () -> Unit
) {
    var editableNote by remember { mutableStateOf(note) }
    var isEditing by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (isEditing) "Edit Note" else "Note Details") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    if (editableNote != null) {
                        if (isEditing) {
                            IconButton(onClick = {
                                editableNote?.let { viewModel.update(it) }
                                isEditing = false
                            }) {
                                Icon(Icons.Default.Check, contentDescription = "Save")
                            }
                        } else {
                            IconButton(onClick = { isEditing = true }) {
                                Icon(Icons.Default.Edit, contentDescription = "Edit")
                            }
                        }
                        IconButton(onClick = {
                            editableNote?.let { viewModel.delete(it) }
                            onBack()
                        }) {
                            Icon(Icons.Default.Delete, contentDescription = "Delete")
                        }
                    }
                }
            )
        }
    ) { padding ->
        if (editableNote == null) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                Text("Note not found", style = MaterialTheme.typography.titleLarge)
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp)
            ) {
                if (isEditing) {
                    OutlinedTextField(
                        value = editableNote!!.title,
                        onValueChange = { editableNote = editableNote!!.copy(title = it) },
                        label = { Text("Title") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    OutlinedTextField(
                        value = editableNote!!.content,
                        onValueChange = { editableNote = editableNote!!.copy(content = it) },
                        label = { Text("Content") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                    )
                } else {
                    Text(
                        text = editableNote!!.title,
                        style = MaterialTheme.typography.headlineMedium,
                        modifier = Modifier.fillMaxWidth(),
                        color = Color(editableNote!!.color)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = editableNote!!.content,
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        textAlign = TextAlign.Justify
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "Created: ${editableNote!!.atCreated}",
                        style = MaterialTheme.typography.labelSmall,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.End
                    )
                }
            }
        }
    }
}