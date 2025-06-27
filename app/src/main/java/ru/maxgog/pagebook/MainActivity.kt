package ru.maxgog.pagebook

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Checklist
import androidx.compose.material.icons.filled.Note
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ru.maxgog.pagebook.ui.CalendarScreen
import ru.maxgog.pagebook.ui.NotesScreen
import ru.maxgog.pagebook.ui.TodoScreen
import ru.maxgog.pagebook.ui.theme.PageBookTheme
import ru.maxgog.pagebook.viewmodels.CalendarViewModel
import ru.maxgog.pagebook.viewmodels.CalendarViewModelFactory
import ru.maxgog.pagebook.viewmodels.NotesViewModel
import ru.maxgog.pagebook.viewmodels.NotesViewModelFactory
import ru.maxgog.pagebook.viewmodels.TodoViewModel
import ru.maxgog.pagebook.viewmodels.TodoViewModelFactory

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PageBookTheme {
                val app = application as PageBookApplication
                val notesViewModelFactory = NotesViewModelFactory(app.notesRepository)
                val calendarViewModelFactory = CalendarViewModelFactory(app.eventsRepository)
                val todoViewModelFactory = TodoViewModelFactory(
                    app.todoRepository,
                    app.eventsRepository
                )
                CompositionLocalProvider(
                    LocalViewModelStoreOwner provides this
                ) {
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colors.background
                    ) {
                        CombinedApp(
                            notesViewModelFactory = notesViewModelFactory,
                            todoViewModelFactory = todoViewModelFactory,
                            calendarViewModelFactory = calendarViewModelFactory
                        )
                    }
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CombinedApp(
    notesViewModelFactory: ViewModelProvider.Factory,
    todoViewModelFactory: ViewModelProvider.Factory,
    calendarViewModelFactory: ViewModelProvider.Factory
) {
    val navController = rememberNavController()
    val viewModelStoreOwner = checkNotNull(LocalViewModelStoreOwner.current) {
        "No ViewModelStoreOwner was provided via LocalViewModelStoreOwner"
    }
    val notesViewModel: NotesViewModel = viewModel(
        viewModelStoreOwner = viewModelStoreOwner,
        factory = notesViewModelFactory
    )
    val todoViewModel: TodoViewModel = viewModel(
        viewModelStoreOwner = viewModelStoreOwner,
        factory = todoViewModelFactory
    )
    val calendarViewModel: CalendarViewModel = viewModel(
        viewModelStoreOwner = viewModelStoreOwner,
        factory = calendarViewModelFactory
    )


    Scaffold(
        bottomBar = { BottomNavBar(navController) }
    ) { padding ->
        NavHost(
            navController = navController,
            startDestination = "todo",
            modifier = Modifier.padding(padding)
        ) {
            composable("todo") {
                TodoScreen(todoViewModel)
            }
            composable("notes") {
                NotesScreen(notesViewModel)
            }
            composable("calendar") {
                CalendarScreen(calendarViewModel)
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun BottomNavBar(navController: NavController) {
    val currentRoute = navController.currentBackStackEntry?.destination?.route

    NavigationBar {
        NavigationBarItem(
            icon = { Icon(Icons.Default.Checklist, stringResource(R.string.todo)) },
            label = { Text(stringResource(R.string.todo_list)) },
            selected = currentRoute == "todo",
            onClick = {
                navController.navigate("todo") {
                    popUpTo(navController.graph.findStartDestination().id) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.Note, stringResource(R.string.notes)) },
            label = { Text(stringResource(R.string.notes)) },
            selected = currentRoute?.startsWith("notes") == true,
            onClick = {
                navController.navigate("notes") {
                    popUpTo(navController.graph.findStartDestination().id) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.CalendarToday, stringResource(R.string.calendar)) },
            label = { Text(stringResource(R.string.calendar)) },
            selected = currentRoute?.startsWith("calendar") == true,
            onClick = {
                navController.navigate("calendar") {
                    popUpTo(navController.graph.findStartDestination().id) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            }
        )
    }
}