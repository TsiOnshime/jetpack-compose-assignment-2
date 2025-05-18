package com.example.todoapp.ui

import androidx.compose.animation.*
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.todoapp.viewmodel.TodoViewModel

// Define our new color palette
private val DarkBackground = Color(0xFF121212)
private val DarkSurface = Color(0xFF1E1E1E)
private val AccentColor = Color(0xFF00BCD4) // A vibrant teal
private val OnDarkSurface = Color.White
private val OnAccent = Color.Black
private val ErrorColor = Color(0xFFD32F2F)
private val OnError = Color.White
private val CompletedColor = Color(0xFF4CAF50)
private val PendingColor = Color(0xFFFF9800)

@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
fun ListScreen(navController: NavController, viewModel: TodoViewModel) {
    val todos by viewModel.todos.collectAsState()
    val loading by viewModel.isLoading.collectAsState()
    val error by viewModel.error.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Tasks at Hand", color = OnAccent) },

                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = AccentColor,
                    titleContentColor = OnAccent,
                    navigationIconContentColor = OnAccent
                )
            )
        },
        content = { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(horizontal = 16.dp, vertical = 16.dp)

            ) {
                when {
                    loading -> {
                        CircularProgressIndicator(
                            modifier = Modifier.align(Alignment.Center),
                            color = AccentColor // Use accent color for indicator
                        )
                    }

                    error != null -> {
                        Column(
                            modifier = Modifier.align(Alignment.Center),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "Something went wrong: $error",
                                color = ErrorColor,
                                style = MaterialTheme.typography.bodyLarge
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Button(
                                onClick = { viewModel.loadTodos() },
                                colors = ButtonDefaults.buttonColors(containerColor = AccentColor, contentColor = OnAccent)
                            ) {
                                Text("Try Again")
                            }
                        }
                    }

else -> {
                        LazyColumn(
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            items(todos) { task -> // Renamed 'todo' to 'task' for clarity
                                AnimatedVisibility(
                                    visible = true,
                                    enter = fadeIn() + expandVertically(),
                                    exit = fadeOut() + shrinkVertically()
                                ) {
                                    Card(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .shadow(4.dp, shape = MaterialTheme.shapes.medium) // Slightly more shadow
                                            .clickable {
                                                navController.navigate("detail/${task.id}")
                                            },
                                        colors = CardDefaults.cardColors(
                                            containerColor = DarkSurface // Darker card surface
                                        ),
                                        shape = MaterialTheme.shapes.medium,

                                    ) {
                                        Column(
                                            modifier = Modifier
                                                .padding(16.dp)
                                                .fillMaxWidth()
                                        ) {
                                            Text(
                                                text = task.title,
                                                style = MaterialTheme.typography.titleLarge,
                                                fontWeight = FontWeight.Bold,
                                                color = OnDarkSurface // White text on dark surface
                                            )
                                            Spacer(modifier = Modifier.height(8.dp))
                                            Text(
                                                text = if (task.completed) "Done" else "Waiting", // Updated text
                                                style = MaterialTheme.typography.bodyMedium,
                                                color = if (task.completed) CompletedColor else PendingColor
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    )
}
