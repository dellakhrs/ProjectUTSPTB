package com.example.moodmusic

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.MutableIntState

// Interface untuk kontrol audio
interface AudioController {
    fun play(audioUrl: String, songId: Int)
    fun pause()
    fun stop()
    fun getActiveSongIdState(): MutableIntState
}

// State yang menyimpan ID lagu aktif
val LocalActiveSongId = compositionLocalOf { mutableIntStateOf(0) }

// CompositionLocal untuk menyediakan AudioController
val LocalAudioController = compositionLocalOf<AudioController> {
    error("No AudioController provided")
}
