package ru.maxgog.pagebook.ui

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import ru.maxgog.pagebook.viewmodels.NotesViewModel

import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ru.maxgog.pagebook.R
import ru.maxgog.pagebook.models.NoteModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NotesScreen(viewModel: NotesViewModel) {
    val notes by viewModel.allNotes.collectAsState(initial = emptyList())
    val notesNavController = rememberNavController()

    NavHost(
        navController = notesNavController,
        startDestination = "list",
        route = "notes_root"
    ) {
        composable("list") {
            NoteListScreen(
                notes = notes,
                onNoteClick = { id -> notesNavController.navigate("detail/$id") },
                onAddNote = { notesNavController.navigate("add") },
            )
        }
        composable("add") {
            NoteEditScreen(
                note = null,
                viewModel = viewModel,
                onBack = { notesNavController.popBackStack()} )
        }
        composable("detail/{id}") { backStackEntry ->
            val noteId = backStackEntry.arguments?.getString("id")?.toIntOrNull()
            val note = notes.firstOrNull { it.id == noteId }
            NoteEditScreen(
                note = note,
                viewModel = viewModel,
                onBack =  { notesNavController.popBackStack() } )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteListScreen(
    notes: List<NoteModel>,
    onNoteClick: (Int) -> Unit,
    onAddNote: () -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.notes)) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onAddNote) {
                Icon(Icons.Default.Add, contentDescription = stringResource(R.string.add))
            }
        }
    ) { padding ->
        LazyColumn(modifier = Modifier.padding(padding)) {
            items(notes.size) { index ->
                val note = notes[index]
                NoteItem(note = note, onClick = { onNoteClick(note.id) })
            }
        }
    }
}