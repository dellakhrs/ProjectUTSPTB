package com.example.moodmusic.ui.theme.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Shuffle
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.yourpackage.moodmusic.data.getMoodById
import com.yourpackage.moodmusic.data.getSongsByMoodId
import com.yourpackage.moodmusic.ui.theme.MoodMusicTheme

@Composable
fun SongListScreen(moodId: Int, onSongClick: (Int) -> Unit, onBackClick: () -> Unit) {
    val mood = getMoodById(moodId)
    val songs = getSongsByMoodId(moodId)
    val moodName = mood?.name ?: "Unknown Mood"

    // Menerapkan Tema Dinamis berdasarkan Mood Color
    MoodMusicTheme(moodColor = mood?.primaryColor) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(moodName) },
                    navigationIcon = {
                        IconButton(onClick = onBackClick) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                        }
                    }
                )
            },
            floatingActionButton = {
                // Floating Action Button (Memenuhi prinsip Material Design)
                FloatingActionButton(onClick = { /* Handle Shuffle Play */ }) {
                    Icon(Icons.Filled.Shuffle, contentDescription = "Shuffle Play")
                }
            }
        ) { padding ->
            LazyColumn(modifier = Modifier.padding(padding)) {
                items(songs) { song ->
                    SongListItem(song = song, onClick = onSongClick)
                }
            }
        }
    }
}

// Komponen Card/ListItem untuk setiap Lagu
@Composable
fun SongListItem(song: com.yourpackage.moodmusic.data.Song, onClick: (Int) -> Unit) {
    ListItem(
        headlineContent = { Text(song.title) },
        supportingContent = { Text(song.artist) },
        trailingContent = { Text(song.duration) },
        modifier = Modifier.clickable { onClick(song.id) }
    )
    Divider(Modifier.padding(horizontal = 16.dp))
}