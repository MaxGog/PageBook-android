package ru.maxgog.pagebook.ui

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Button
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Text
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import ru.maxgog.pagebook.models.NoteModel
import ru.maxgog.pagebook.viewmodels.NotesViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AddNoteScreen(viewModel: NotesViewModel, onBack: () -> Unit) {
    var title by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }

    Column {
        TextField(value = title, onValueChange = { title = it }, label = { Text("Title") })
        TextField(value = content, onValueChange = { content = it }, label = { Text("Content") })
        Button(onClick = {
            viewModel.insert(NoteModel(
                title = title,
                content = content
                //atCreated = LocalDate.now()
            ))
            onBack()
        }) {
            Text("Save")
        }
    }
}