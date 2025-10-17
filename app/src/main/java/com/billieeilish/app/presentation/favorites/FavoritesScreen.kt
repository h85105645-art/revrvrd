package com.billieeilish.app.presentation.favorites

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

/**
 * Favorites screen displaying user's favorite content with categories
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoritesScreen() {
    var selectedTab by remember { mutableStateOf(0) }
    val tabs = listOf("الكل", "أفلام", "مسلسلات", "أنمي", "قنوات")
    
    // Sample favorite items
    val sampleFavorites = remember {
        listOf(
            FavoriteItem(
                id = 1,
                title = "Inception",
                titleArabic = "البداية",
                type = "فيلم",
                posterUrl = "https://image.tmdb.org/t/p/w500/9gk7adHYeDvHkCSEqAvQNLV5Uge.jpg",
                rating = 8.8,
                year = "2010"
            ),
            FavoriteItem(
                id = 2,
                title = "Breaking Bad",
                titleArabic = "بريكنغ باد",
                type = "مسلسل",
                posterUrl = "https://image.tmdb.org/t/p/w500/ggFHVNu6YYI5L9pCfOacjizRGt.jpg",
                rating = 9.5,
                year = "2008"
            ),
            FavoriteItem(
                id = 3,
                title = "Attack on Titan",
                titleArabic = "هجوم العمالقة",
                type = "أنمي",
                posterUrl = "https://image.tmdb.org/t/p/w500/hTP1DtLGFamjfu8WqjnuQdP1n4i.jpg",
                rating = 9.0,
                year = "2013"
            ),
            FavoriteItem(
                id = 4,
                title = "The Dark Knight",
                titleArabic = "فارس الظلام",
                type = "فيلم",
                posterUrl = "https://image.tmdb.org/t/p/w500/qJ2tW6WMUDux911r6m7haRef0WH.jpg",
                rating = 9.0,
                year = "2008"
            ),
            FavoriteItem(
                id = 5,
                title = "Game of Thrones",
                titleArabic = "صراع العروش",
                type = "مسلسل",
                posterUrl = "https://image.tmdb.org/t/p/w500/u3bZgnGQ9T01sWNhyveQz0wH0Hl.jpg",
                rating = 9.3,
                year = "2011"
            )
        )
    }
    
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
        // Top App Bar
        TopAppBar(
            title = {
                Text(
                    text = "المفضلة",
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
                IconButton(onClick = { /* TODO: Implement sort */ }) {
                    Icon(
                        imageVector = Icons.Default.Sort,
                        contentDescription = "ترتيب",
                        tint = Color.White
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color.Transparent
            )
        )
        
        // Category Tabs
        LazyRow(
            modifier = Modifier.padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(horizontal = 16.dp)
        ) {
            items(tabs.size) { index ->
                FilterChip(
                    onClick = { selectedTab = index },
                    label = { Text(tabs[index]) },
                    selected = selectedTab == index,
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = MaterialTheme.colorScheme.primary,
                        selectedLabelColor = Color.White,
                        containerColor = Color(0xFF2A2A2A),
                        labelColor = Color.White
                    )
                )
            }
        }
        
        // Content
        if (sampleFavorites.isEmpty()) {
            // Empty State
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = Icons.Default.FavoriteBorder,
                        contentDescription = null,
                        modifier = Modifier.size(64.dp),
                        tint = Color.Gray
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "لا توجد عناصر مفضلة",
                        color = Color.White,
                        style = MaterialTheme.typography.headlineSmall
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "ابدأ بإضافة أفلام ومسلسلات إلى مفضلتك",
                        color = Color.Gray,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        } else {
            // Favorites List
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(
                    sampleFavorites.filter { favorite ->
                        when (selectedTab) {
                            0 -> true // الكل
                            1 -> favorite.type == "فيلم"
                            2 -> favorite.type == "مسلسل"
                            3 -> favorite.type == "أنمي"
                            4 -> favorite.type == "قناة"
                            else -> true
                        }
                    }
                ) { favorite ->
                    FavoriteItemCard(
                        favorite = favorite,
                        onRemoveClick = {
                            // TODO: Remove from favorites
                        },
                        onClick = {
                            // TODO: Navigate to details
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun FavoriteItemCard(
    favorite: FavoriteItem,
    onRemoveClick: () -> Unit,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF2A2A2A)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxSize()
        ) {
            // Poster Image
            AsyncImage(
                model = favorite.posterUrl,
                contentDescription = favorite.title,
                modifier = Modifier
                    .width(80.dp)
                    .fillMaxHeight()
                    .clip(RoundedCornerShape(topStart = 12.dp, bottomStart = 12.dp)),
                contentScale = ContentScale.Crop
            )
            
            // Content
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .padding(12.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.Top
                    ) {
                        Column(
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(
                                text = favorite.titleArabic,
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Bold
                                ),
                                color = Color.White,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                            Text(
                                text = favorite.title,
                                style = MaterialTheme.typography.bodySmall,
                                color = Color.Gray,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                        
                        IconButton(
                            onClick = onRemoveClick,
                            modifier = Modifier.size(24.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Favorite,
                                contentDescription = "إزالة من المفضلة",
                                tint = Color.Red,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }
                }
                
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp),
                            tint = Color(0xFFFFD700)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = String.format("%.1f", favorite.rating),
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.White
                        )
                        
                        Spacer(modifier = Modifier.width(12.dp))
                        
                        Text(
                            text = favorite.year,
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.Gray
                        )
                    }
                    
                    Surface(
                        color = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            text = favorite.type,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }
        }
    }
}

data class FavoriteItem(
    val id: Int,
    val title: String,
    val titleArabic: String,
    val type: String,
    val posterUrl: String,
    val rating: Double,
    val year: String
)