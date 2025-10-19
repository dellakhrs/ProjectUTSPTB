package com.example.moodmusic.data

import androidx.compose.ui.graphics.Color

// Data class untuk Mood
data class Mood(
    val id: Int,
    val name: String,
    val primaryColor: Color // Menggunakan Compose Color untuk tema dinamis
)

// Data class untuk Lagu
data class Song(
    val id: Int,
    val moodId: Int,
    val title: String,
    val artist: String,
    val albumArtUrl: String = "https://picsum.photos/400/400?random=$id", // URL dummy
    val duration: String
)