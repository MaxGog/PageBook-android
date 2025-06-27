package ru.maxgog.pagebook.ui.app

import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import ru.maxgog.pagebook.viewmodels.CalendarViewModel
import ru.maxgog.pagebook.viewmodels.NotesViewModel
import ru.maxgog.pagebook.viewmodels.TodoViewModel
import ru.maxgog.pagebook.ui.navigation.AppNavHost
import ru.maxgog.pagebook.ui.navigation.BottomNavBar

@Composable
fun CombinedApp(
    viewModelStoreOwner: ViewModelStoreOwner = checkNotNull(LocalViewModelStoreOwner.current) {
        "No ViewModelStoreOwner was provided via LocalViewModelStoreOwner"
    }
) {
    val navController = rememberNavController()
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    val notesViewModel: NotesViewModel = viewModel(
        viewModelStoreOwner = viewModelStoreOwner,
        factory = NotesViewModel.Factory
    )

    val todoViewModel: TodoViewModel = viewModel(
        viewModelStoreOwner = viewModelStoreOwner,
        factory = TodoViewModel.Factory
    )

    val calendarViewModel: CalendarViewModel = viewModel(
        viewModelStoreOwner = viewModelStoreOwner,
        factory = CalendarViewModel.Factory
    )

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        bottomBar = { BottomNavBar(navController) }
    ) { padding ->
        AppNavHost(
            navController = navController,
            paddingValues = padding,
            notesViewModel = notesViewModel,
            todoViewModel = todoViewModel,
            calendarViewModel = calendarViewModel,
        )
    }
}
@Composable
private fun AppContent(
    navController: NavHostController,
    notesViewModel: NotesViewModel,
    todoViewModel: TodoViewModel,
    calendarViewModel: CalendarViewModel
) {
    Scaffold(
        bottomBar = { BottomNavBar(navController) }
    ) { padding ->
        AppNavHost(
            navController = navController,
            paddingValues = padding,
            notesViewModel = notesViewModel,
            todoViewModel = todoViewModel,
            calendarViewModel = calendarViewModel
        )
    }
}