package com.billieeilish.app.presentation.moviedetails

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
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
import coil.request.ImageRequest
import com.billieeilish.app.domain.model.Movie
import com.billieeilish.app.domain.model.MovieDetails
import com.billieeilish.app.domain.model.Cast
import com.billieeilish.app.presentation.player.VideoPlayerActivity
import com.billieeilish.app.utils.UiState

/**
 * Movie details screen showing comprehensive movie information
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieDetailsScreen(
    movieId: Int,
    onBackPressed: () -> Unit,
    viewModel: MovieDetailsViewModel = hiltViewModel()
) {
    val movieState by viewModel.movie.collectAsState()
    val creditsState by viewModel.credits.collectAsState()
    val context = LocalContext.current
    
    LaunchedEffect(movieId) {
        viewModel.loadMovieDetails(movieId)
    }
    
    when (movieState) {
        is UiState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
        
        is UiState.Success -> {
            val movieDetails = (movieState as UiState.Success).data
            
            LazyColumn(
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
                item {
                    // Movie Header with Backdrop
                    MovieHeader(
                        movie = movieDetails,
                        onBackPressed = onBackPressed,
                        onPlayClick = {
                            // Launch video player with SuperEmbed URL
                            val intent = Intent(context, VideoPlayerActivity::class.java).apply {
                                putExtra(VideoPlayerActivity.EXTRA_VIDEO_URL, 
                                    "https://multiembed.mov/directstream.php?video_id=${movieDetails.id}&tmdb=1")
                                putExtra(VideoPlayerActivity.EXTRA_VIDEO_TITLE, movieDetails.title)
                            }
                            context.startActivity(intent)
                        },
                        onFavoriteClick = {
                            viewModel.toggleFavorite(movieDetails)
                        }
                    )
                }
                
                item {
                    // Movie Info
                    MovieInfo(movie = movieDetails)
                }
                
                item {
                    // Cast Section
                    when (creditsState) {
                        is UiState.Success -> {
                            CastSection(cast = (creditsState as UiState.Success).data.cast)
                        }
                        else -> {}
                    }
                }
            }
        }
        
        is UiState.Error -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = (movieState as UiState.Error).message,
                        color = Color.Red,
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = { viewModel.loadMovieDetails(movieId) }
                    ) {
                        Text("إعادة المحاولة")
                    }
                }
            }
        }
        
        else -> {}
    }
}

@Composable
private fun MovieHeader(
    movie: MovieDetails,
    onBackPressed: () -> Unit,
    onPlayClick: () -> Unit,
    onFavoriteClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(400.dp)
    ) {
        // Backdrop Image
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data("https://image.tmdb.org/t/p/w1280${movie.backdropPath}")
                .crossfade(true)
                .build(),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        
        // Gradient Overlay
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color.Transparent,
                            Color.Black.copy(alpha = 0.8f)
                        ),
                        startY = 200f
                    )
                )
        )
        
        // Top Bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(
                onClick = onBackPressed,
                colors = IconButtonDefaults.iconButtonColors(
                    containerColor = Color.Black.copy(alpha = 0.5f)
                )
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "رجوع",
                    tint = Color.White
                )
            }
            
            IconButton(
                onClick = onFavoriteClick,
                colors = IconButtonDefaults.iconButtonColors(
                    containerColor = Color.Black.copy(alpha = 0.5f)
                )
            ) {
                Icon(
                    imageVector = Icons.Default.FavoriteBorder,
                    contentDescription = "إضافة للمفضلة",
                    tint = Color.White
                )
            }
        }
        
        // Movie Info at Bottom
        Column(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(16.dp)
        ) {
            Text(
                text = movie.title,
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.Bold
                ),
                color = Color.White
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (movie.voteAverage > 0) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = null,
                        tint = Color.Yellow,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = String.format("%.1f", movie.voteAverage),
                        color = Color.White,
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                }
                
                if (movie.releaseDate.isNotEmpty()) {
                    Text(
                        text = movie.releaseDate.take(4),
                        color = Color.Gray,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Play Button
            Button(
                onClick = onPlayClick,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                ),
                shape = RoundedCornerShape(8.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.PlayArrow,
                    contentDescription = null,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "شاهد الآن",
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.SemiBold
                    )
                )
            }
        }
    }
}

@Composable
private fun MovieInfo(movie: MovieDetails) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF2A2A2A)
        ),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "نظرة عامة",
                style = MaterialTheme.typography.headlineSmall.copy(
                    fontWeight = FontWeight.Bold
                ),
                color = Color.White
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = movie.overview,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray,
                lineHeight = 20.sp
            )
        }
    }
}

@Composable
private fun CastSection(cast: List<Cast>) {
    if (cast.isNotEmpty()) {
        Column(
            modifier = Modifier.padding(horizontal = 16.dp)
        ) {
            Text(
                text = "طاقم التمثيل",
                style = MaterialTheme.typography.headlineSmall.copy(
                    fontWeight = FontWeight.Bold
                ),
                color = Color.White,
                modifier = Modifier.padding(bottom = 12.dp)
            )
            
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(bottom = 16.dp)
            ) {
                items(cast.take(10)) { actor ->
                    CastItem(actor = actor)
                }
            }
        }
    }
}

@Composable
private fun CastItem(actor: Cast) {
    Column(
        modifier = Modifier.width(80.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data("https://image.tmdb.org/t/p/w185${actor.profilePath}")
                .crossfade(true)
                .build(),
            contentDescription = actor.name,
            modifier = Modifier
                .size(60.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )
        
        Spacer(modifier = Modifier.height(8.dp))
        
        Text(
            text = actor.name,
            style = MaterialTheme.typography.bodySmall,
            color = Color.White,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
        
        Text(
            text = actor.character,
            style = MaterialTheme.typography.bodySmall,
            color = Color.Gray,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}