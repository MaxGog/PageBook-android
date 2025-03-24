package ru.maxgog.pagebook

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import ru.maxgog.pagebook.ui.NotesApp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MyNotesTheme {
                Surface(color = MaterialTheme.colors.background) {
                    NotesApp()
                }
            }
        }
    }
}

@Composable
fun MyNotesTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colors = lightColors(
            primary = Purple500,
            primaryVariant = Purple700,
            secondary = Teal200
        ),
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}