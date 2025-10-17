package com.billieeilish.app.presentation.anime

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

/**
 * Anime screen displaying anime series
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnimeScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF121212),
                        Color(0xFF1E1E1E)
                    )
                )
            )
    ) {
        TopAppBar(
            title = {
                Text(
                    text = "الأنمي",
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontWeight = FontWeight.Bold
                    ),
                    color = Color.White
                )
            },
            actions = {
                IconButton(onClick = { /* TODO: Implement search */ }) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "البحث",
                        tint = Color.White
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color.Transparent
            )
        )
        
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "شاشة الأنمي قيد التطوير",
                color = Color.White,
                style = MaterialTheme.typography.headlineSmall
            )
        }
    }
}