package com.example.moodmusic

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.moodmusic.ui.theme.MoodMusicTheme
import com.example.moodmusic.navigation.AppNavHost
import android.media.MediaPlayer
import android.content.Context
import android.util.Log
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.mutableStateOf

fun playAudio(context: Context, audioUrl: String) {
    try {
        val mediaPlayer = MediaPlayer().apply {
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
        Log.e("AudioPlayer", "Error playing audio: ${e.message}")
        e.printStackTrace()
    }
}

class MainActivity : ComponentActivity() {
    private val activeSongIdState = mutableStateOf(0)
    private var mediaPlayer: MediaPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MoodMusicTheme {
                CompositionLocalProvider(
                    LocalActiveSongId provides activeSongIdState,
                    LocalMediaPlayer provides mediaPlayer
                ) {
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = androidx.compose.material3.MaterialTheme.colorScheme.background
                    ) {
                        AppNavHost()
                    }
                }
            }
        }
    }
}
