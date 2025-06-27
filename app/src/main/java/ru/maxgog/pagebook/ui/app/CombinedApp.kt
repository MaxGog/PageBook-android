package ru.maxgog.pagebook.ui.app


import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
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

    AppContent(
        navController = navController,
        notesViewModel = notesViewModel,
        todoViewModel = todoViewModel,
        calendarViewModel = calendarViewModel
    )
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