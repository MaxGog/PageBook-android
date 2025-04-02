package ru.maxgog.pagebook.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import ru.maxgog.pagebook.R
import ru.maxgog.pagebook.models.TodoModel
import ru.maxgog.pagebook.ui.theme.TodoListAppTheme

@Preview(showBackground = true)
@Composable
fun TodoPreview() {
    TodoListAppTheme {
        TodoItem(
            todo = TodoModel(
                title = stringResource(R.string.title),
                description = stringResource(R.string.description)
            ),
            onTodoClick = {},
            onDeleteClick = {}
        )
    }
}