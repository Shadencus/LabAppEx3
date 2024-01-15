package de.hhn.labapp.persistence.todo.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import de.hhn.labapp.persistence.todo.viewmodel.TodoListViewModel

@Composable
fun TodoItemDialog(viewModel: TodoListViewModel) {
    viewModel.currentItemText = viewModel.currentItem?.text ?: ""

    fun onDismissRequest() {
        if (viewModel.currentItemText.isBlank()) {
            viewModel.deleteItem(viewModel.currentItem!!)
        }
        viewModel.currentItem = null
    }

    Dialog(
        onDismissRequest = ::onDismissRequest,
    ) {
        Card(
            modifier = Modifier.padding(4.dp),
            border = BorderStroke(2.dp, MaterialTheme.colorScheme.primary),
        ) {
            Column(
                modifier = Modifier.padding(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                TodoItemTextField(viewModel)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                ) {
                    TextButton(
                        onClick = {
                            viewModel.currentItem?.let { viewModel.deleteItem(it) }
                            onDismissRequest()
                        }
                    ) {
                        Text("Delete")
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    TextButton(onClick = ::onDismissRequest) {
                        Text("Cancel")
                    }
                    TextButton(
                        onClick = {
                            viewModel.onSaveItem()
                            onDismissRequest()
                        },
                        enabled = viewModel.isSaveEnabled,
                    ) {
                        Text("Save")
                    }
                }
            }
        }
    }
}

@Composable
private fun TodoItemTextField(viewModel: TodoListViewModel) {
    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        value = viewModel.currentItemText,
        onValueChange = { viewModel.currentItemText = it },
        label = { Text("Task description") },
    )
}