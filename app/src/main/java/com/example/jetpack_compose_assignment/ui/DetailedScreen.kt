package com.example.todoapp.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.todoapp.ui.theme.*
import com.example.todoapp.viewmodel.TodoViewModel

// Re-define the color palette for consistency with ListScreen and to better match screenshot
private val PrimaryColor = Color(0xFF9C27B0) // Darker Purple
private val PrimaryLight = Color(0xFFE1BEE7) // Light Purple
private val PrimaryDark = Color(0xFF7B1FA2)
private val SecondaryColor = Color(0xFFF06292)
private val SecondaryLight = Color(0xFFF8BBD0)
private val SecondaryDark = Color(0xFFC2185B)
private val BackgroundColor = Color(0xFFF3E5F5)
private val SurfaceColor = Color(0xFFFFFFFF)
private val OnPrimary = Color.White
private val OnSecondary = Color.White
private val OnBackground = Color(0xFF212121)
private val OnSurface = Color(0xFF212121)
private val ErrorColor = Color(0xFFD32F2F)
private val OnError = Color.White
private val CompletedColor = Color(0xFF4CAF50)
private val PendingColor = Color(0xFFFFC107)
private val OnSurfaceVariantDark = Color(0xFF757575) // A Darker grey for secondary text - more accurate


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(todoId: Int, navController: NavController, viewModel: TodoViewModel) {
    val todos by viewModel.todos.collectAsState()
    val todo = todos.find { it.id == todoId }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Todo Detail", color = OnPrimary) }, // Consistent title
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = OnPrimary)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = PrimaryColor, titleContentColor = OnPrimary, navigationIconContentColor = OnPrimary) // Consistent colors
            )
        },
        containerColor = BackgroundColor,
        content = { innerPadding ->
            todo?.let { task ->
                Column(
                    modifier = Modifier
                        .padding(innerPadding)
                        .padding(16.dp)
                        .fillMaxSize(),
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    // Task Title
                    Text(
                        text = task.title,
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        color = OnSurface // Use OnSurface
                    )
                    Divider(color = PrimaryLight) // Lighter divider

                    // Status
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "Status:",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.SemiBold,
                            color = OnSurfaceVariantDark, // Darker variant
                            modifier = Modifier.width(80.dp)
                        )
                        val statusColor = if (task.completed) CompletedColor else PendingColor
                        Text(
                            text = if (task.completed) "Completed" else "Pending", // Consistent text
                            style = MaterialTheme.typography.bodyLarge,
                            color = statusColor
                        )
                    }

                    // User ID
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "User ID:",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.SemiBold,
                            color = OnSurfaceVariantDark,
                            modifier = Modifier.width(80.dp)
                        )
                        Text(
                            text = "${task.userId}",
                            style = MaterialTheme.typography.bodyLarge,
                            color = OnSurface
                        )
                    }

                    // Todo ID
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "Todo ID:",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.SemiBold,
                            color = OnSurfaceVariantDark,
                            modifier = Modifier.width(80.dp)
                        )
                        Text(
                            text = "${task.id}",
                            style = MaterialTheme.typography.bodyLarge,
                            color = OnSurface
                        )
                    }
                }
            } ?: Text(
                "Couldn't find the task details.",
                modifier = Modifier
                    .padding(innerPadding)
                    .padding(16.dp),
                style = MaterialTheme.typography.bodyMedium,
                color = OnSurface
            )
        }
    )
}

