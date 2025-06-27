package ru.maxgog.pagebook.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ru.maxgog.pagebook.R
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
                title = { Text(if (isNewNote) stringResource(R.string.new_note) else stringResource(R.string.edit_note)) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = stringResource(R.string.back))
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
                        Icon(Icons.Default.Check, contentDescription = stringResource(R.string.save))
                    }

                    if (!isNewNote) {
                        IconButton(onClick = {
                            viewModel.delete(editableNote)
                            onBack()
                        }) {
                            Icon(Icons.Default.Delete, contentDescription = stringResource(R.string.delete))
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
                onValueChange = { editableNote = editableNote.copy(title = it) },
                label = { Text(stringResource(R.string.title)) },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = editableNote.content,
                onValueChange = { editableNote = editableNote.copy(content = it) },
                label = { Text(stringResource(R.string.content)) },
                modifier = Modifier.fillMaxWidth().weight(1f)
                //textAlign = TextAlign.Justify
            )
        }
    }
}