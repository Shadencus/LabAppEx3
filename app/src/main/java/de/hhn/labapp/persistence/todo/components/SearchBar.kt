package de.hhn.labapp.persistence.todo.components

import android.util.Log
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.setValue
import de.hhn.labapp.persistence.todo.viewmodel.TodoListViewModel

@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    onQueryChanged: (String) -> Unit,
    query: String = "",
    placeholder: String = "Search",
) {
    var searchQuery by remember { mutableStateOf(query) }

    OutlinedTextField(
        modifier = modifier,
        singleLine = true,
        value = searchQuery,
        onValueChange = {
            searchQuery = it
            onQueryChanged(it)

        },
        label = {
            Text(placeholder)
        },
        trailingIcon = {
            if (searchQuery.isNotEmpty()) {

                IconButton(
                    onClick = {
                        searchQuery = ""
                        onQueryChanged("")
                    }
                ) {
                    Icon(Icons.Filled.Clear, "Clear Search")
                }
            }
        }
    )
}