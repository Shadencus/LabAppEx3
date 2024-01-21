package de.hhn.labapp.persistence.todo.model.dao

import androidx.room.*
import de.hhn.labapp.persistence.todo.model.entity.TodoItem

@Dao
interface TodoItemDao {
    @Query("SELECT * FROM todoitem")
    fun getAll(): List<TodoItem>

    @Query("SELECT * FROM todoitem WHERE id = :id")
    fun get(id: Int): TodoItem?

    @Insert
    fun insert(toDoItem: TodoItem)

    @Insert
    fun insertAll(toDoItem: List<TodoItem>)

    @Update
    fun update(toDoItem: TodoItem)

    @Upsert
    fun upsert (toDoItem: TodoItem)

    @Delete
    fun delete(toDoItem: TodoItem)

    //Prüft, ob es Einträge gibt, die teils oder ganz dem übergebenen Wert entsprechen und
    //gibt eine Liste mit diesen zurück
    @Query("SELECT * FROM todoitem WHERE text LIKE '%' || :text || '%'")
    fun search(text: String): List<TodoItem>

}