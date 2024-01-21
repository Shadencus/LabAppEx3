package de.hhn.labapp.persistence.todo.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TodoItem(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    var text: String,
    var completed: Boolean = false

)