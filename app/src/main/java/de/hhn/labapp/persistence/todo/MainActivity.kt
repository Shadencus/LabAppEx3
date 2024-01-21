package de.hhn.labapp.persistence.todo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import de.hhn.labapp.persistence.todo.components.TodoList
import de.hhn.labapp.persistence.todo.model.DatabaseProvider
import de.hhn.labapp.persistence.todo.ui.theme.Exercise3Theme
import de.hhn.labapp.persistence.todo.viewmodel.TodoListViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DatabaseProvider.init(applicationContext)
        setContent {
            Exercise3Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    TodoList()
                }
            }
        }
    }
}
