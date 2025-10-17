package com.billieeilish.app.presentation.anime

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
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
 * Anime screen displaying anime series with categories and search
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnimeScreen() {
    var selectedCategory by remember { mutableStateOf("popular") }
    var isSearchVisible by remember { mutableStateOf(false) }
    var searchQuery by remember { mutableStateOf("") }
    
    // Sample anime data
    val sampleAnime = remember {
        listOf(
            AnimeItem(
                id = 1,
                title = "Attack on Titan",
                titleArabic = "هجوم العمالقة",
                posterUrl = "https://image.tmdb.org/t/p/w500/hTP1DtLGFamjfu8WqjnuQdP1n4i.jpg",
                rating = 9.0,
                year = "2013",
                episodes = 87,
                status = "مكتمل",
                description = "في عالم تحاصره العمالقة الآكلة للبشر، يقاتل إيرين ييغر وأصدقاؤه من أجل البقاء واستعادة الحرية."
            ),
            AnimeItem(
                id = 2,
                title = "Demon Slayer",
                titleArabic = "قاتل الشياطين",
                posterUrl = "https://image.tmdb.org/t/p/w500/xUfRZu2mi8jH6SzQEJGP6tjBuYj.jpg",
                rating = 8.7,
                year = "2019",
                episodes = 44,
                status = "مستمر",
                description = "تانجيرو كامادو يصبح قاتل شياطين لإنقاذ أخته نيزوكو التي تحولت إلى شيطان."
            ),
            AnimeItem(
                id = 3,
                title = "One Piece",
                titleArabic = "قطعة واحدة",
                posterUrl = "https://image.tmdb.org/t/p/w500/cMD9Ygz11zjJzAovURpO75Qg7rT.jpg",
                rating = 9.2,
                year = "1999",
                episodes = 1000,
                status = "مستمر",
                description = "مونكي دي لوفي وطاقمه يبحثون عن الكنز الأسطوري المعروف باسم 'قطعة واحدة'."
            ),
            AnimeItem(
                id = 4,
                title = "Naruto",
                titleArabic = "ناروتو",
                posterUrl = "https://image.tmdb.org/t/p/w500/vauCEnR7CiyBDzRCeElKkCaXIYu.jpg",
                rating = 8.4,
                year = "2002",
                episodes = 720,
                status = "مكتمل",
                description = "ناروتو أوزوماكي، نينجا شاب يحلم بأن يصبح هوكاغي قريته."
            ),
            AnimeItem(
                id = 5,
                title = "My Hero Academia",
                titleArabic = "أكاديمية البطل",
                posterUrl = "https://image.tmdb.org/t/p/w500/aoPoQSStjrQrTnXqz81yYvFdUAW.jpg",
                rating = 8.6,
                year = "2016",
                episodes = 138,
                status = "مستمر",
                description = "في عالم يمتلك فيه معظم الناس قوى خارقة، يسعى إيزوكو ميدوريا ليصبح بطلاً خارقاً."
            ),
            AnimeItem(
                id = 6,
                title = "Jujutsu Kaisen",
                titleArabic = "جوجوتسو كايسن",
                posterUrl = "https://image.tmdb.org/t/p/w500/qw3J9cNeLioOLoR68WX7z79aCdK.jpg",
                rating = 8.9,
                year = "2020",
                episodes = 24,
                status = "مستمر",
                description = "يوجي إيتادوري يدخل عالم السحرة لمحاربة اللعنات الشريرة."
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
                    text = "الأنمي",
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontWeight = FontWeight.Bold
                    ),
                    color = Color.White
                )
            },
            actions = {
                IconButton(onClick = { isSearchVisible = !isSearchVisible }) {
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
        
        // Search Bar
        if (isSearchVisible) {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                placeholder = { Text("ابحث عن أنمي...") },
                leadingIcon = {
                    Icon(Icons.Default.Search, contentDescription = null)
                },
                trailingIcon = {
                    if (searchQuery.isNotEmpty()) {
                        IconButton(onClick = { searchQuery = "" }) {
                            Icon(Icons.Default.Clear, contentDescription = "مسح")
                        }
                    }
                },
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                    unfocusedBorderColor = Color.Gray
                )
            )
        }
        
        // Categories
        if (!isSearchVisible) {
            LazyRow(
                modifier = Modifier.padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(horizontal = 16.dp)
            ) {
                items(animeCategories) { category ->
                    FilterChip(
                        onClick = { selectedCategory = category.key },
                        label = { Text(category.title) },
                        selected = selectedCategory == category.key,
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = MaterialTheme.colorScheme.primary,
                            selectedLabelColor = Color.White,
                            containerColor = Color(0xFF2A2A2A),
                            labelColor = Color.White
                        )
                    )
                }
            }
        }
        
        // Anime Grid
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(
                if (searchQuery.isNotEmpty()) {
                    sampleAnime.filter { 
                        it.title.contains(searchQuery, ignoreCase = true) ||
                        it.titleArabic.contains(searchQuery, ignoreCase = true)
                    }
                } else {
                    sampleAnime
                }
            ) { anime ->
                AnimeCard(
                    anime = anime,
                    onClick = { 
                        // TODO: Navigate to anime details
                    }
                )
            }
        }
    }
}

@Composable
private fun AnimeCard(
    anime: AnimeItem,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(320.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF2A2A2A)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column {
            // Poster Image
            AsyncImage(
                model = anime.posterUrl,
                contentDescription = anime.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .clip(RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp)),
                contentScale = ContentScale.Crop
            )
            
            // Anime Info
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp)
            ) {
                Text(
                    text = anime.titleArabic,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold
                    ),
                    color = Color.White,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                
                Text(
                    text = anime.title,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
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
                        text = String.format("%.1f", anime.rating),
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.White
                    )
                    
                    Spacer(modifier = Modifier.weight(1f))
                    
                    Text(
                        text = anime.year,
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Gray
                    )
                }
                
                Spacer(modifier = Modifier.height(4.dp))
                
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.PlayCircle,
                        contentDescription = null,
                        modifier = Modifier.size(14.dp),
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "${anime.episodes} حلقة",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.White
                    )
                    
                    Spacer(modifier = Modifier.weight(1f))
                    
                    Text(
                        text = anime.status,
                        style = MaterialTheme.typography.bodySmall,
                        color = if (anime.status == "مستمر") Color(0xFF4CAF50) else Color(0xFF2196F3)
                    )
                }
                
                Spacer(modifier = Modifier.height(4.dp))
                
                Text(
                    text = anime.description,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

data class AnimeItem(
    val id: Int,
    val title: String,
    val titleArabic: String,
    val posterUrl: String,
    val rating: Double,
    val year: String,
    val episodes: Int,
    val status: String,
    val description: String
)

data class AnimeCategory(
    val key: String,
    val title: String
)

private val animeCategories = listOf(
    AnimeCategory("popular", "الأكثر شعبية"),
    AnimeCategory("top_rated", "الأعلى تقييماً"),
    AnimeCategory("airing", "يُعرض الآن"),
    AnimeCategory("completed", "مكتمل"),
    AnimeCategory("upcoming", "قريباً")
)