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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SongDetailScreen(songId: Int, onBackClick: () -> Unit) {
    val song = getSongById(songId)
    if (song == null) {
        // Menangani lagu tidak ditemukan
        Text("Song Not Found")
        return
    }

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    // State untuk Animasi Fade-In Gambar Album
    var imageLoaded by remember { mutableStateOf(false) }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }, // Host Snackbar
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

            // Gambar Album dengan Animasi Fade-In
            AnimatedVisibility(
                visible = imageLoaded,
                enter = fadeIn(initialAlpha = 0.3f), // Animasi muncul (fade-in)
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

            // Simulasi tanda bahwa gambar sudah dimuat (gunakan ImageRequest state untuk implementasi riil)
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

            // Tombol Play
            Button(
                onClick = {
                    scope.launch {
                        // Menampilkan Snackbar notifikasi (Memenuhi prinsip Material Design)
                        snackbarHostState.showSnackbar(
                            message = "Playing ${song.title}...",
                            duration = SnackbarDuration.Short
                        )
                    }
                },
                modifier = Modifier.fillMaxWidth().height(56.dp)
            ) {
                Icon(Icons.Filled.PlayArrow, contentDescription = "Play", modifier = Modifier.size(24.dp))
                Spacer(Modifier.width(8.dp))
                Text("Play Song (${song.duration})")
            }
        }
    }
}