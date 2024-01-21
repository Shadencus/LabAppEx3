package de.hhn.labapp.persistence.todo.viewmodel

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.hhn.labapp.persistence.todo.model.AppDatabase
import de.hhn.labapp.persistence.todo.model.DatabaseProvider.withDatabase
import de.hhn.labapp.persistence.todo.model.dao.TodoItemDao
import de.hhn.labapp.persistence.todo.model.entity.TodoItem
import kotlinx.coroutines.launch

class TodoListViewModel(

) : ViewModel() {
    var todoItems = mutableStateListOf<TodoItem>()
        @Synchronized get
    var currentItemText by mutableStateOf("")
    var currentItem by mutableStateOf<TodoItem?>(null)


    val showDialog: Boolean
        get() = currentItem != null

    val isSaveEnabled: Boolean
        get() = currentItemText.isNotBlank()

    fun onItemChecked(newValue: Boolean, item: TodoItem) {
        //val index = todoItems.indexOf(item)
        //todoItems[index] = item.copy(completed = newValue)
        item.completed = newValue
        withDatabase {
            todoItemDao().update(item)
        }
    }

    fun onItemClicked(item: TodoItem) {
        currentItem = item
    }

    fun deleteItem(item: TodoItem): Boolean {
        //todoItems.remove(item)
        withDatabase { todoItemDao().delete(item) }
        loadTodoList()
        return true
    }

    fun onAddItem() {
        val item = TodoItem(text = "")
        withDatabase { todoItemDao().insert(item) }
        currentItem = item
    }

    fun onSaveItem() {
        if (currentItem == null) {
            return
        }else{
            //var text = currentItem!!.copy(text = currentItemText)
            val item: TodoItem = currentItem!!
            val text = currentItemText
            item.text = text
            withDatabase {
                todoItemDao().update(item)
            }

        }
        loadTodoList()
    }

    private fun createSampleData(): List<TodoItem> {
        return listOf(
            TodoItem(text = "Buy milk", completed = true),
            TodoItem(text = "Buy eggs"),
            TodoItem(text = "Buy bread"),
        )
    }

    fun init() {
        viewModelScope.launch {
            loadTodoList()
        }
    }

    private fun loadTodoList() {
        withDatabase {
            val temp = todoItemDao().getAll()

            synchronized(todoItems){
                todoItems = temp.toMutableStateList()
            }
        }
    }
}