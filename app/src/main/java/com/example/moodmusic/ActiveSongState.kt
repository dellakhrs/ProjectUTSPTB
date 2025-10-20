package com.example.moodmusic

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.MutableIntState

interface AudioController {
    fun play(audioUrl: String, songId: Int)
    fun pause()
    fun stop()
    fun getActiveSongIdState(): MutableIntState
}

val LocalActiveSongId = compositionLocalOf { mutableIntStateOf(0) }

val LocalAudioController = compositionLocalOf<AudioController> {
    error("No AudioController provided")
}