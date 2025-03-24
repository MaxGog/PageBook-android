package ru.maxgog.pagebook.ui

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import ru.maxgog.pagebook.models.NoteModel

@Composable
fun NoteListScreen(
    notes: List<NoteModel>,
    onNoteClick: (Int) -> Unit,
    onAddNote: () -> Unit
) {
    LazyColumn {
        items(notes) { note ->
            NoteItem(note = note, onClick = { onNoteClick(note.id) })
        }
    }

    FloatingActionButton(onClick = onAddNote) {
        Icon(Icons.Default.Add, contentDescription = "Add Note")
    }
}