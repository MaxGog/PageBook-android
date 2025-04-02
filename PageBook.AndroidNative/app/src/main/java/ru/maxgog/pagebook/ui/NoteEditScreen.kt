package ru.maxgog.pagebook.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import ru.maxgog.pagebook.models.NoteModel
import ru.maxgog.pagebook.viewmodels.NotesViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteEditScreen(
    note: NoteModel? = null,
    viewModel: NotesViewModel,
    onBack: () -> Unit
) {
    val isNewNote = note == null
    var editableNote by remember {
        mutableStateOf(note ?: NoteModel(title = "", content = ""))
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (isNewNote) "New Note" else "Edit Note") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = {
                        if (isNewNote) {
                            viewModel.insert(editableNote)
                        } else {
                            viewModel.update(editableNote)
                        }
                        onBack()
                    }) {
                        Icon(Icons.Default.Check, contentDescription = "Save")
                    }

                    if (!isNewNote) {
                        IconButton(onClick = {
                            viewModel.delete(editableNote)
                            onBack()
                        }) {
                            Icon(Icons.Default.Delete, contentDescription = "Delete")
                        }
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            OutlinedTextField(
                value = editableNote.title,
                onValueChange = { editableNote = editableNote.copy(title = editableNote.title) },
                label = { Text("Title") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = editableNote.content,
                onValueChange = { editableNote = editableNote.copy(content = editableNote.content) },
                label = { Text("Content") },
                modifier = Modifier.fillMaxWidth().weight(1f)
                //textAlign = TextAlign.Justify
            )
        }
    }
}