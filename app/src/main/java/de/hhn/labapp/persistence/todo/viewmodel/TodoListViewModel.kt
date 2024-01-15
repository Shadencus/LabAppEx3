package de.hhn.labapp.persistence.todo.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import de.hhn.labapp.persistence.todo.model.TodoItem

class TodoListViewModel : ViewModel() {
    val todoItems = mutableStateListOf(*createSampleData().toTypedArray())
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
    }

    fun onItemClicked(item: TodoItem) {
        currentItem = item
    }

    fun deleteItem(item: TodoItem): Boolean {
        todoItems.remove(item)
        return true
    }

    fun onAddItem() {
        val item = TodoItem(text = "")
        todoItems.add(item)
        currentItem = item
    }

    fun onSaveItem() {
        if (currentItem == null) {
            return
        }

        val item = currentItem!!.copy(text = currentItemText)
        val index = todoItems.indexOf(currentItem!!)
        todoItems[index] = item
    }

    private fun createSampleData(): List<TodoItem> {
        return listOf(
            TodoItem(text = "Buy milk", completed = true),
            TodoItem(text = "Buy eggs"),
            TodoItem(text = "Buy bread"),
        )
    }
}