package ru.maxgog.pagebook.ui

import ru.maxgog.pagebook.models.NoteModel
import ru.maxgog.pagebook.viewmodels.NotesViewModel

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun NotesApp(viewModel: NotesViewModel = viewModel()) {
    val notes by viewModel.allNotes.observeAsState(emptyList())
    var showDialog by remember { mutableStateOf(false) }
    var currentNote by remember { mutableStateOf<NoteModel?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Мои заметки") },
                actions = {
                    IconButton(onClick = { showDialog = true }) {
                        Icon(Icons.Default.Add, contentDescription = "Добавить")
                    }
                }
            )
        },
        floatingActionButton = {
            if (showDialog) {
                NoteEditDialog(
                    note = currentNote,
                    onDismiss = { showDialog = false },
                    onSave = { note ->
                        if (note.id == 0) {
                            viewModel.addNote(note)
                        } else {
                            viewModel.updateNote(note)
                        }
                        showDialog = false
                    }
                )
            }
        }
    ) { padding ->
        LazyColumn(modifier = Modifier.padding(padding)) {
            items(notes) { note ->
                NoteItem(
                    note = note,
                    onEdit = {
                        currentNote = note
                        showDialog = true
                    },
                    onDelete = { viewModel.deleteNote(note) }
                )
            }
        }
    }
}