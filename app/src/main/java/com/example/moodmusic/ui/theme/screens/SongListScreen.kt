package com.example.moodmusic.ui.theme.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Shuffle
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.moodmusic.data.getMoodById
import com.example.moodmusic.data.getSongsByMoodId
import com.example.moodmusic.ui.theme.MoodMusicTheme
import com.example.moodmusic.ui.theme.components.SongListItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SongListScreen(moodId: Int, onSongClick: (Int) -> Unit, onBackClick: () -> Unit) {
    val mood = getMoodById(moodId)
    val songs = getSongsByMoodId(moodId)
    val moodName = mood?.name ?: "Unknown Mood"

    MoodMusicTheme(moodColor = mood?.primaryColor) {
        val topAppBarColors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.onPrimary,
            navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
        )

        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(moodName) },
                    colors = topAppBarColors,
                    navigationIcon = {
                        IconButton(onClick = onBackClick) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                        }
                    }
                )
            },
            floatingActionButton = {
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