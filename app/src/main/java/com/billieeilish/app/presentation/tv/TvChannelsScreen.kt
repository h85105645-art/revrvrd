package com.billieeilish.app.presentation.tv

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.billieeilish.app.domain.model.TvChannel
import com.billieeilish.app.presentation.player.VideoPlayerActivity
import com.billieeilish.app.utils.UiState

/**
 * TV channels screen displaying live TV channels from IPTV
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TvChannelsScreen(
    viewModel: TvChannelsViewModel = hiltViewModel()
) {
    val channelsState by viewModel.channels.collectAsState()
    val context = LocalContext.current
    
    LaunchedEffect(Unit) {
        viewModel.loadChannels()
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
        TopAppBar(
            title = {
                Text(
                    text = "القنوات التلفزيونية",
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
        
        when (channelsState) {
            is UiState.Loading -> {
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
                            text = "جاري تحميل القنوات...",
                            color = Color.White,
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }
            }
            
            is UiState.Success -> {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items((channelsState as UiState.Success).data) { channel ->
                        ChannelItem(
                            channel = channel,
                            onClick = {
                                // Launch video player for live TV
                                val intent = Intent(context, VideoPlayerActivity::class.java).apply {
                                    putExtra(VideoPlayerActivity.EXTRA_VIDEO_URL, channel.url)
                                    putExtra(VideoPlayerActivity.EXTRA_VIDEO_TITLE, channel.name)
                                }
                                context.startActivity(intent)
                            }
                        )
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
                        Icon(
                            imageVector = Icons.Default.Error,
                            contentDescription = null,
                            tint = Color.Red,
                            modifier = Modifier.size(48.dp)
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = (channelsState as UiState.Error).message,
                            color = Color.Red,
                            style = MaterialTheme.typography.bodyLarge
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(
                            onClick = { viewModel.loadChannels() }
                        ) {
                            Text("إعادة المحاولة")
                        }
                    }
                }
            }
            
            is UiState.Empty -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "لا توجد قنوات متاحة",
                        color = Color.Gray,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
        }
    }
}

@Composable
private fun ChannelItem(
    channel: TvChannel,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF2A2A2A)
        ),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Channel Logo
            if (channel.logo != null) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(channel.logo)
                        .crossfade(true)
                        .build(),
                    contentDescription = channel.name,
                    modifier = Modifier
                        .size(60.dp)
                        .clip(RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Crop
                )
            } else {
                Box(
                    modifier = Modifier
                        .size(60.dp)
                        .background(
                            color = MaterialTheme.colorScheme.primary,
                            shape = RoundedCornerShape(8.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Tv,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
            
            Spacer(modifier = Modifier.width(16.dp))
            
            // Channel Info
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = channel.displayName,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.SemiBold
                    ),
                    color = Color.White,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                
                Spacer(modifier = Modifier.height(4.dp))
                
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = channel.countryFlag,
                        style = MaterialTheme.typography.bodyMedium
                    )
                    
                    Spacer(modifier = Modifier.width(8.dp))
                    
                    Text(
                        text = channel.group,
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Gray
                    )
                }
                
                if (channel.category != "General") {
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = channel.category,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
            
            // Live indicator
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .background(
                            color = Color.Red,
                            shape = RoundedCornerShape(4.dp)
                        )
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "مباشر",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Red
                )
            }
        }
    }
}