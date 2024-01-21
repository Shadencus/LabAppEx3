package de.hhn.labapp.persistence.todo.model

import android.content.Context
import androidx.room.Room
import de.hhn.labapp.persistence.todo.model.entity.TodoItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

object DatabaseProvider {
    lateinit var database: AppDatabase
        private set

    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    fun init(context: Context) {
        database = Room.databaseBuilder(
            context,
            AppDatabase::class.java, "exercise3"
        ).build()

        withDatabase { populateDatabase() }
    }

    fun withDatabase(block: AppDatabase.() -> Unit): Job {
        return coroutineScope.launch { database.block() }
    }

    private fun populateDatabase() {
        if (database.todoItemDao().getAll().isEmpty()) {
            database.todoItemDao().insertAll(
                listOf(
                    TodoItem(text = "Buy milk", completed = true),
                    TodoItem(text = "Buy eggs"),
                    TodoItem(text = "Buy bread"),
                )
            )
        }

    }
}