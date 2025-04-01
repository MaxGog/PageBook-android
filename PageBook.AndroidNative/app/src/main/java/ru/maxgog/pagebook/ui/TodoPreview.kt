package ru.maxgog.pagebook.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import ru.maxgog.pagebook.models.TodoModel
import ru.maxgog.pagebook.ui.theme.TodoListAppTheme

@Preview(showBackground = true)
@Composable
fun TodoPreview() {
    TodoListAppTheme {
        TodoItem(
            todo = TodoModel(title = "Sample Task", description = "This is a sample task"),
            onTodoClick = {},
            onDeleteClick = {}
        )
    }
}