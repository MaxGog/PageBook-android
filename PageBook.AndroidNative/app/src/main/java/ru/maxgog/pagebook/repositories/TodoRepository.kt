package ru.maxgog.pagebook.repositories

import kotlinx.coroutines.flow.Flow
import ru.maxgog.pagebook.dao.TodoDao
import ru.maxgog.pagebook.models.TodoModel

class TodoRepository(private val todoDao: TodoDao) {
    val allTodos: Flow<List<TodoModel>> = todoDao.getAllTodos()

    suspend fun insert(todo: TodoModel) = todoDao.insert(todo)
    suspend fun update(todo: TodoModel) = todoDao.update(todo)
    suspend fun delete(todo: TodoModel) = todoDao.delete(todo)
}