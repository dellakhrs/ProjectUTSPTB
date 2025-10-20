package com.example.moodmusic.ui.theme.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.moodmusic.data.getSongById
import kotlinx.coroutines.launch
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import com.example.moodmusic.playAudio
import androidx.compose.material.icons.filled.Pause
import com.example.moodmusic.LocalActiveSongId

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
    val context = LocalContext.current

    // AKSES STATE GLOBAL: Dapatkan dan atur ID lagu yang sedang aktif
    var activeSongId by LocalActiveSongId.current
    val isPlayingThisSong = activeSongId == songId

    // LOGIKA UI DINAMIS
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

            LaunchedEffect(Unit) {
                imageLoaded = true
            }

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
                            activeSongId = 0 // Reset state global
                            snackbarHostState.showSnackbar(message = "Paused ${song.title}...")

                        } else {
                            playAudio(context, song.audioUrl)
                            activeSongId = songId

                            snackbarHostState.showSnackbar(message = "Playing ${song.title}...")
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth().height(56.dp)
            ) {
                Icon(buttonIcon, contentDescription = buttonText, modifier = Modifier.size(24.dp))
                Spacer(Modifier.width(8.dp))
                Text(buttonText)
            }
        }
    }
}