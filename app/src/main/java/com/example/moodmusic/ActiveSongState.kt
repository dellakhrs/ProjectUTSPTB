package com.example.moodmusic


import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

val LocalActiveSongId = compositionLocalOf { mutableStateOf(0) }
val LocalMediaPlayer = compositionLocalOf<android.media.MediaPlayer?> { null }