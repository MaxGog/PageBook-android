package ru.maxgog.pagebook.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.text.input.TextFieldValue
import ru.maxgog.pagebook.models.NoteModel

@Composable
fun NoteEditDialog(
    note: NoteModel?,
    onDismiss: () -> Unit,
    onSave: (NoteModel) -> Unit
) {
    var title by remember { mutableStateOf(note?.title ?: "") }
    var content by remember { mutableStateOf(note?.content ?: "") }
    var color by remember { mutableStateOf(note?.color ?: 0xFFBB86FC.toInt()) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(text = if (note == null) "Новая заметка" else "Редактировать")
        },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Заголовок") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                OutlinedTextField(
                    value = content,
                    onValueChange = { content = it },
                    label = { Text("Содержание") },
                    modifier = Modifier.fillMaxWidth(),
                    maxLines = 5
                )

                ColorPicker(
                    selectedColor = color,
                    onColorSelected = { color = it }
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    onSave(
                        NoteModel(
                            id = note?.id ?: 0,
                            title = title,
                            content = content,
                            color = color
                        )
                    )
                },
                enabled = title.isNotBlank()
            ) {
                Text("Сохранить")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Отмена")
            }
        }
    )
}