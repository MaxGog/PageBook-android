package ru.maxgog.pagebook.ui.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ru.maxgog.pagebook.R
import ru.maxgog.pagebook.models.NoteModel
import ru.maxgog.pagebook.ui.items.NoteItem
import ru.maxgog.pagebook.ui.theme.PageBookTheme
import ru.maxgog.pagebook.viewmodels.NotesViewModel

@Composable
fun NotesScreen(
    viewModel: NotesViewModel = viewModel(),
    navController: NavHostController = rememberNavController()
) {
    val notes by viewModel.allNotes.collectAsState(initial = emptyList())
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { padding ->
        NavHost(
            modifier = Modifier.padding(padding),
            navController = navController,
            startDestination = NotesDestinations.LIST_ROUTE,
            route = NotesDestinations.ROOT_ROUTE
        ) {
            composable(NotesDestinations.LIST_ROUTE) {
                NoteListScreen(
                    notes = notes,
                    onNoteClick = { id -> navController.navigate("${NotesDestinations.DETAIL_ROUTE}/$id") },
                    onAddNote = { navController.navigate(NotesDestinations.ADD_ROUTE) },
                )
            }

            composable(NotesDestinations.ADD_ROUTE) {
                NoteEditScreen(
                    note = null,
                    viewModel = viewModel,
                    onBack = { navController.popBackStack() }
                )
            }

            composable("${NotesDestinations.DETAIL_ROUTE}/{id}") { backStackEntry ->
                val noteId = backStackEntry.arguments?.getString("id")?.toIntOrNull()
                val note = notes.firstOrNull { note -> note.id == noteId }
                NoteEditScreen(
                    note = note,
                    viewModel = viewModel,
                    onBack = { navController.popBackStack() }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteListScreen(
    notes: List<NoteModel>,
    onNoteClick: (Int) -> Unit,
    onAddNote: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.notes),
                        style = MaterialTheme.typography.headlineSmall
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surfaceContainer,
                    titleContentColor = MaterialTheme.colorScheme.onSurface
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddNote,
                containerColor = MaterialTheme.colorScheme.primaryContainer
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(R.string.add)
                )
            }
        },
        modifier = modifier
    ) { padding ->
        if (notes.isEmpty()) {
            EmptyNotesScreen(modifier = Modifier.padding(padding))
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(horizontal = 16.dp)
            ) {
                items(
                    items = notes,
                    key = { note -> note.id }
                ) { note ->
                    NoteItem(
                        note = note,
                        onClick = { onNoteClick(note.id) },
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun EmptyNotesScreen(modifier: Modifier = Modifier) {
    Text(
        text = "Нет заметок",
        style = MaterialTheme.typography.bodyLarge,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    )
}

object NotesDestinations {
    const val ROOT_ROUTE = "notes_root"
    const val LIST_ROUTE = "list"
    const val ADD_ROUTE = "add"
    const val DETAIL_ROUTE = "detail"
}

@Preview
@Composable
private fun NotesScreenPreview() {
    PageBookTheme {
        NotesScreen()
    }
}

@Preview
@Composable
private fun NoteListScreenPreview() {
    PageBookTheme {
        NoteListScreen(
            notes = listOf(
                NoteModel(1, "Title 1", "Content 1"),
                NoteModel(2, "Title 2", "Content 2")
            ),
            onNoteClick = {},
            onAddNote = {}
        )
    }
}