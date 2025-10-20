package com.example.moodmusic.ui.theme.screens

import android.media.MediaPlayer
import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.moodmusic.data.getSongById
import com.example.moodmusic.LocalActiveSongId
import kotlinx.coroutines.launch

fun playAudio(audioUrl: String) {
    try {
        MediaPlayer().apply {
            setAudioAttributes(
                android.media.AudioAttributes.Builder()
                    .setContentType(android.media.AudioAttributes.CONTENT_TYPE_MUSIC)
                    .setUsage(android.media.AudioAttributes.USAGE_MEDIA)
                    .build()
            )
            setDataSource(audioUrl)
            prepareAsync()

            setOnPreparedListener {
                it.start()
                Log.d("AudioPlayer", "Playing audio from URL: $audioUrl")
            }

            setOnCompletionListener {
                it.release()
                Log.d("AudioPlayer", "Audio finished and resources released.")
            }
        }
    } catch (e: Exception) {
        Log.e("AudioPlayer", "Error playing audio: ${e.message}", e)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SongDetailScreen(songId: Int, onBackClick: () -> Unit) {
    val song = getSongById(songId)
    if (song == null) {
        Text("Song Not Found")
        return
    }

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    var imageLoaded by remember { mutableStateOf(false) }
    var activeSongId by LocalActiveSongId.current

    val isPlayingThisSong = activeSongId == songId
    val buttonIcon = if (isPlayingThisSong) Icons.Filled.Pause else Icons.Filled.PlayArrow
    val buttonText = if (isPlayingThisSong) "Pause Music" else "Play Song (${song.duration})"

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(
                title = { Text(song.title, maxLines = 1) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AnimatedVisibility(
                visible = imageLoaded,
                enter = fadeIn(initialAlpha = 0.3f),
                modifier = Modifier.size(250.dp)
            ) {
                AsyncImage(
                    model = song.albumArtUrl,
                    contentDescription = "Album Art",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .aspectRatio(1f)
                        .clip(MaterialTheme.shapes.medium)
                )
            }

            LaunchedEffect(Unit) { imageLoaded = true }

            Spacer(Modifier.height(24.dp))

            Text(
                song.title,
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onBackground
            )
            Text(
                song.artist,
                style = MaterialTheme.typography.titleMedium.copy(
                    color = MaterialTheme.colorScheme.primary
                )
            )

            Spacer(Modifier.height(40.dp))

            Button(
                onClick = {
                    scope.launch {
                        if (isPlayingThisSong) {
                            activeSongId = 0
                            snackbarHostState.showSnackbar("Paused ${song.title}...")
                        } else {
                            playAudio(song.audioUrl)
                            activeSongId = songId
                            snackbarHostState.showSnackbar("Playing ${song.title}...")
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
            ) {
                Icon(buttonIcon, contentDescription = buttonText, modifier = Modifier.size(24.dp))
                Spacer(Modifier.width(8.dp))
                Text(buttonText)
            }
        }
    }
}
