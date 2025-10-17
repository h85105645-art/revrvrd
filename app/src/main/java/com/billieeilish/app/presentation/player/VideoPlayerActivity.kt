package com.billieeilish.app.presentation.player

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.ui.PlayerView
import com.billieeilish.app.presentation.theme.BillieEilishTheme
import dagger.hilt.android.AndroidEntryPoint

/**
 * Full-screen video player activity using ExoPlayer
 */
@AndroidEntryPoint
class VideoPlayerActivity : ComponentActivity() {
    
    private var exoPlayer: ExoPlayer? = null
    
    companion object {
        const val EXTRA_VIDEO_URL = "video_url"
        const val EXTRA_VIDEO_TITLE = "video_title"
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Set full screen and landscape orientation
        setupFullScreen()
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        
        val videoUrl = intent.getStringExtra(EXTRA_VIDEO_URL) ?: ""
        val videoTitle = intent.getStringExtra(EXTRA_VIDEO_TITLE) ?: ""
        
        setContent {
            BillieEilishTheme {
                VideoPlayerScreen(
                    videoUrl = videoUrl,
                    videoTitle = videoTitle,
                    onBackPressed = { finish() }
                )
            }
        }
    }
    
    private fun setupFullScreen() {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        
        val windowInsetsController = WindowCompat.getInsetsController(window, window.decorView)
        windowInsetsController.systemBarsBehavior = 
            WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        windowInsetsController.hide(WindowInsetsCompat.Type.systemBars())
        
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
    }
    
    override fun onDestroy() {
        super.onDestroy()
        exoPlayer?.release()
    }
}

@Composable
fun VideoPlayerScreen(
    videoUrl: String,
    videoTitle: String,
    onBackPressed: () -> Unit
) {
    val context = LocalContext.current
    
    var exoPlayer by remember { mutableStateOf<ExoPlayer?>(null) }
    
    DisposableEffect(videoUrl) {
        val player = ExoPlayer.Builder(context).build().apply {
            val mediaItem = MediaItem.fromUri(videoUrl)
            setMediaItem(mediaItem)
            prepare()
            playWhenReady = true
        }
        exoPlayer = player
        
        onDispose {
            player.release()
        }
    }
    
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        exoPlayer?.let { player ->
            AndroidView(
                factory = { context ->
                    PlayerView(context).apply {
                        this.player = player
                        useController = true
                        controllerAutoShow = true
                        controllerHideOnTouch = true
                        
                        // Custom player controls
                        setShowBuffering(PlayerView.SHOW_BUFFERING_WHEN_PLAYING)
                        setShowNextButton(false)
                        setShowPreviousButton(false)
                        setShowFastForwardButton(true)
                        setShowRewindButton(true)
                    }
                },
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}