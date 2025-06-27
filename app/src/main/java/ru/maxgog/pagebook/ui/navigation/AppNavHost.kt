package ru.maxgog.pagebook.ui.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import ru.maxgog.pagebook.ui.screens.CalendarScreen
import ru.maxgog.pagebook.ui.screens.NotesScreen
import ru.maxgog.pagebook.ui.screens.TodoScreen
import ru.maxgog.pagebook.viewmodels.CalendarViewModel
import ru.maxgog.pagebook.viewmodels.NotesViewModel
import ru.maxgog.pagebook.viewmodels.TodoViewModel

@Composable
fun AppNavHost(
    navController: NavHostController,
    paddingValues: PaddingValues,
    notesViewModel: NotesViewModel,
    todoViewModel: TodoViewModel,
    calendarViewModel: CalendarViewModel,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Todo.route,
        modifier = modifier.padding(paddingValues)
    ) {
        composable(Screen.Todo.route) {
            TodoScreen(viewModel = todoViewModel)
        }
        composable(Screen.Notes.route) {
            NotesScreen(viewModel = notesViewModel)
        }
        composable(Screen.Calendar.route) {
            CalendarScreen(viewModel = calendarViewModel)
        }
    }
}