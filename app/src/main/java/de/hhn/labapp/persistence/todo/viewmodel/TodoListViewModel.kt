package de.hhn.labapp.persistence.todo.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.hhn.labapp.persistence.todo.model.DatabaseProvider.withDatabase
import de.hhn.labapp.persistence.todo.model.entity.TodoItem
import kotlinx.coroutines.launch

class TodoListViewModel() : ViewModel() {
    var todoItems = mutableStateListOf<TodoItem>()
        @Synchronized get


    var currentItemText by mutableStateOf("")
    var currentItem by mutableStateOf<TodoItem?>(null)


    val showDialog: Boolean
        get() = currentItem != null

    val isSaveEnabled: Boolean
        get() = currentItemText.isNotBlank()

    fun onItemChecked(newValue: Boolean, item: TodoItem) {
        val index = todoItems.indexOf(item)
        todoItems[index] = item.copy(completed = newValue)
        item.completed = newValue
        withDatabase {
            todoItemDao().update(item)
        }
    }

    fun onItemClicked(item: TodoItem) {
        currentItem = item
    }

    fun deleteItem(item: TodoItem): Boolean {
        withDatabase { todoItemDao().delete(item) }
        todoItems.remove(item)
        return true
    }

    fun onAddItem() {
        // Da die Wahrscheinlichkeit besteht, dass das Anlegen gecancelt wird,
        // habe ich mich dagegen entschieden hier einen Datenbankeintrag anzulegen.
        // Stattdessen wird in der Update-Funktion upsert verwendet. So wird vermieden
        // einen unnötigen Eintrag anzulegen
        val item = TodoItem(text = "")
        todoItems.add(item)
        currentItem = item
    }

    fun onSaveItem() {
        if (currentItem == null) {
            return
        } else {

            val item = currentItem!!.copy(text = currentItemText)
            val index = todoItems.indexOf(currentItem!!)
            todoItems[index] = item
            withDatabase {
                todoItemDao().upsert(item)
            }
        }
    }

    fun searchItem(todoItemText: String) {
        todoItems.clear()
        withDatabase {
            todoItems.addAll(todoItemDao().search(todoItemText))
        }
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

    fun loadTodoList() {
        if (todoItems.isNotEmpty()) {
            todoItems.clear()
        }
        withDatabase {
            synchronized(todoItems) {
                todoItems.addAll(todoItemDao().getAll())
            }
        }
    }
}