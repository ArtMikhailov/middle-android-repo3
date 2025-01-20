package ru.yandex.architectureproject

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.yandex.architectureproject.ui.theme.ArchitectureProjectTheme

class MainActivity : ComponentActivity() {
    private var tasks = mutableStateListOf<Task>()
    private var taskIdCounter = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ArchitectureProjectTheme {
                TodoApp()
            }
        }
    }

    @Composable
    fun TodoApp() {
        var newTaskText by remember { mutableStateOf("") }

        Column(
            modifier = Modifier
                .padding(16.dp)
                .systemBarsPadding()
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextField(
                    value = newTaskText,
                    onValueChange = { newTaskText = it },
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Button(onClick = {
                    if (newTaskText.isNotBlank()) {
                        tasks.add(Task(taskIdCounter++, newTaskText, mutableStateOf(false)))
                        newTaskText = ""
                    }
                }) {
                    Text("Добавить")
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            LazyColumn {
                items(tasks) { task ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = task.text,
                            style = if (task.isDone.value) MaterialTheme.typography.bodyMedium.copy(
                                textDecoration = TextDecoration.LineThrough
                            )
                            else MaterialTheme.typography.bodyMedium
                        )
                        Row {
                            Checkbox(
                                checked = task.isDone.value,
                                onCheckedChange = { task.isDone.value = it }
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Button(onClick = { tasks.remove(task) }) {
                                Text("Удалить")
                            }
                        }
                    }
                }
            }
        }
    }
}

data class Task(val id: Int, val text: String, var isDone: MutableState<Boolean>)
