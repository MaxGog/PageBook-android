package ru.maxgog.pagebook.ui.app

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
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
        modifier = Modifier.systemBarsPadding(),
        contentWindowInsets = WindowInsets(0.dp),
        snackbarHost = { SnackbarHost(snackbarHostState) },
        bottomBar = { BottomNavBar(navController) },
        content = { padding ->
            AppNavHost(
                modifier = Modifier.padding(padding),
                navController = navController,
                paddingValues = padding,
                notesViewModel = notesViewModel,
                todoViewModel = todoViewModel,
                calendarViewModel = calendarViewModel,
            )
        }
    )
}