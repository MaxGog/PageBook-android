package ru.maxgog.pagebook.ui

import android.os.Build
import androidx.annotation.RequiresApi
import ru.maxgog.pagebook.viewmodels.NotesViewModel

import androidx.compose.runtime.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NotesApp(viewModel: NotesViewModel) {
    val navController = rememberNavController()
    val notes by viewModel.allNotes.collectAsState(initial = emptyList())

    NavHost(navController = navController, startDestination = "list") {
        composable("list") {
            NoteListScreen(notes, onNoteClick = { id ->
                navController.navigate("detail/$id")
            }, onAddNote = {
                navController.navigate("add")
            })
        }
        composable("add") {
            AddNoteScreen(viewModel) {
                navController.popBackStack()
            }
        }
        composable("detail/{id}") { backStackEntry ->
            val noteId = backStackEntry.arguments?.getString("id")?.toIntOrNull()
            val note = notes.firstOrNull { it.id == noteId }
            NoteDetailScreen(note, viewModel) {
                navController.popBackStack()
            }
        }
    }
}
