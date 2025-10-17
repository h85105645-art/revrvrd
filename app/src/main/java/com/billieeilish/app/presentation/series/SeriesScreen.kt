package com.billieeilish.app.presentation.series

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.billieeilish.app.domain.model.Series
import com.billieeilish.app.utils.Resource

/**
 * Series screen displaying TV series with categories and search
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SeriesScreen(
    viewModel: SeriesViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsState()
    var selectedCategory by remember { mutableStateOf("popular") }
    
    LaunchedEffect(selectedCategory) {
        viewModel.loadSeries(selectedCategory)
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
                    text = "المسلسلات",
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontWeight = FontWeight.Bold
                    ),
                    color = Color.White
                )
            },
            actions = {
                IconButton(onClick = { viewModel.toggleSearch() }) {
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
        if (uiState.isSearchVisible) {
            OutlinedTextField(
                value = uiState.searchQuery,
                onValueChange = { viewModel.updateSearchQuery(it) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                placeholder = { Text("ابحث عن مسلسل...") },
                leadingIcon = {
                    Icon(Icons.Default.Search, contentDescription = null)
                },
                trailingIcon = {
                    if (uiState.searchQuery.isNotEmpty()) {
                        IconButton(onClick = { viewModel.clearSearch() }) {
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
        if (!uiState.isSearchVisible) {
            LazyRow(
                modifier = Modifier.padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(horizontal = 16.dp)
            ) {
                items(seriesCategories) { category ->
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
        
        // Content
        when (val resource = uiState.series) {
            is Resource.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        CircularProgressIndicator(
                            color = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "جاري تحميل المسلسلات...",
                            color = Color.White,
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }
            }
            is Resource.Error -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            imageVector = Icons.Default.Error,
                            contentDescription = null,
                            modifier = Modifier.size(64.dp),
                            tint = Color.Red
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "خطأ في تحميل المسلسلات",
                            color = Color.White,
                            style = MaterialTheme.typography.headlineSmall
                        )
                        Text(
                            text = resource.message ?: "حدث خطأ غير متوقع",
                            color = Color.Gray,
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(
                            onClick = { viewModel.loadSeries(selectedCategory) }
                        ) {
                            Text("إعادة المحاولة")
                        }
                    }
                }
            }
            is Resource.Success -> {
                if (resource.data?.isEmpty() == true) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(
                                imageVector = Icons.Default.Tv,
                                contentDescription = null,
                                modifier = Modifier.size(64.dp),
                                tint = Color.Gray
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            Text(
                                text = "لا توجد مسلسلات",
                                color = Color.White,
                                style = MaterialTheme.typography.headlineSmall
                            )
                        }
                    }
                } else {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(16.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        items(resource.data ?: emptyList()) { series ->
                            SeriesCard(
                                series = series,
                                onClick = { 
                                    // TODO: Navigate to series details
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun SeriesCard(
    series: Series,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(280.dp)
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
                model = "https://image.tmdb.org/t/p/w500${series.posterPath}",
                contentDescription = series.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .clip(RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp)),
                contentScale = ContentScale.Crop
            )
            
            // Series Info
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp)
            ) {
                Text(
                    text = series.name,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold
                    ),
                    color = Color.White,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                
                Spacer(modifier = Modifier.height(4.dp))
                
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
                        text = String.format("%.1f", series.voteAverage),
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.White
                    )
                    
                    Spacer(modifier = Modifier.weight(1f))
                    
                    Text(
                        text = series.firstAirDate.take(4),
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Gray
                    )
                }
                
                Spacer(modifier = Modifier.height(4.dp))
                
                Text(
                    text = series.overview,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

data class SeriesCategory(
    val key: String,
    val title: String
)

private val seriesCategories = listOf(
    SeriesCategory("popular", "الأكثر شعبية"),
    SeriesCategory("top_rated", "الأعلى تقييماً"),
    SeriesCategory("on_the_air", "يُعرض الآن"),
    SeriesCategory("airing_today", "يُعرض اليوم")
)