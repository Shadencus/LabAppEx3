package de.hhn.labapp.persistence.todo.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.hhn.labapp.persistence.todo.model.DatabaseProvider.withDatabase
import de.hhn.labapp.persistence.todo.model.entity.TodoItem
import kotlinx.coroutines.launch

class TodoListOverviewModel : ViewModel() {
    val todoList = mutableStateOf(emptyList<TodoItem>())
        @Synchronized get

    fun init() {
        viewModelScope.launch {
            loadTodoList()
        }
    }

    private fun loadTodoList() {
        withDatabase {
            val temp = todoItemDao().getAll()

            synchronized(todoList){
                todoList.value = temp
            }
        }
    }

}