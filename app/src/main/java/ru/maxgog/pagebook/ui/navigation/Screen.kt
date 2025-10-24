package ru.maxgog.pagebook.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Note
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Checklist
import androidx.compose.material.icons.filled.Note
import androidx.compose.ui.graphics.vector.ImageVector
import ru.maxgog.pagebook.R

sealed class Screen(
    val route: String,
    val icon: ImageVector,
    val labelResId: Int
) {
    object Todo : Screen("todo", Icons.Default.Checklist, R.string.todo_list)
    object Notes : Screen("notes", Icons.AutoMirrored.Filled.Note, R.string.notes)
    object Calendar : Screen("calendar", Icons.Default.CalendarToday, R.string.calendar)

    companion object {
        val bottomNavItems = listOf(Todo, Notes, Calendar)
    }
}