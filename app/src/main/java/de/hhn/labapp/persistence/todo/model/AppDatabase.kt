package de.hhn.labapp.persistence.todo.model

import androidx.room.Database
import androidx.room.RoomDatabase
import de.hhn.labapp.persistence.todo.model.dao.TodoItemDao
import de.hhn.labapp.persistence.todo.model.entity.TodoItem

@Database(
    entities = [
        TodoItem::class
    ], version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun todoItemDao(): TodoItemDao

}
