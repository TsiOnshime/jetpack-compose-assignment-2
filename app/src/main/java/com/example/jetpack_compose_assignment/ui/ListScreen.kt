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

// Define our new color palette - Adjusted to match screenshot
private val PrimaryColor = Color(0xFF9C27B0) // Darker Purple
private val PrimaryLight = Color(0xFFE1BEE7) // Light Purple
private val PrimaryDark = Color(0xFF7B1FA2)  // Darker Purple
private val SecondaryColor = Color(0xFFF06292) // A vibrant pink
private val SecondaryLight = Color(0xFFF8BBD0)
private val SecondaryDark = Color(0xFFC2185B)
private val BackgroundColor = Color(0xFFF3E5F5) // Lightest Purple
private val SurfaceColor = Color(0xFFFFFFFF) // White surface
private val OnPrimary = Color.White
private val OnSecondary = Color.White
private val OnBackground = Color(0xFF212121) // Very Dark Gray
private val OnSurface = Color(0xFF212121)
private val ErrorColor = Color(0xFFD32F2F)
private val OnError = Color.White
private val CompletedColor = Color(0xFF4CAF50)
private val PendingColor = Color(0xFFFFC107) // Amber for Pending


@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
fun ListScreen(navController: NavController, viewModel: TodoViewModel) {
    val todos by viewModel.todos.collectAsState()
    val loading by viewModel.isLoading.collectAsState()
    val error by viewModel.error.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Todo List", color = OnPrimary) }, // "Todo List"
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = PrimaryColor, // Use PrimaryColor
                    titleContentColor = OnPrimary,
                    navigationIconContentColor = OnPrimary
                )
            )
        },
        containerColor = BackgroundColor, // Set the background color of the whole screen
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
                            color = PrimaryColor // Use PrimaryColor for indicator
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
                                colors = ButtonDefaults.buttonColors(containerColor = PrimaryColor, contentColor = OnPrimary) // Primary Color
                            ) {
                                Text("Try Again")
                            }
                        }
                    }

                    else -> {
                        LazyColumn(
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            items(todos) { task ->
                                AnimatedVisibility(
                                    visible = true,
                                    enter = fadeIn() + expandVertically(),
                                    exit = fadeOut() + shrinkVertically()
                                ) {
                                    Card(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .shadow(2.dp, shape = MaterialTheme.shapes.medium) // Subtle shadow
                                            .clickable {
                                                navController.navigate("detail/${task.id}")
                                            },
                                        colors = CardDefaults.cardColors(
                                            containerColor = SurfaceColor // White Card
                                        ),
                                        shape = MaterialTheme.shapes.medium,
                                        border = CardDefaults.outlinedCardBorder(color = PrimaryLight, width = 1.dp)

                                    ) {
                                        Column(
                                            modifier = Modifier
                                                .padding(16.dp)
                                                .fillMaxWidth()
                                        ) {
                                            Text(
                                                text = task.title,
                                                style = MaterialTheme.typography.titleMedium, // Lighter title
                                                fontWeight = FontWeight.SemiBold, // Use Semibold
                                                color = OnSurface // Darker text
                                            )
                                            Spacer(modifier = Modifier.height(8.dp))
                                            Text(
                                                text = if (task.completed) "Completed" else "Pending",
                                                style = MaterialTheme.typography.bodySmall, // Smaller body
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

