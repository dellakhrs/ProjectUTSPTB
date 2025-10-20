package com.example.moodmusic

import android.media.MediaPlayer
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.ui.Modifier
import com.example.moodmusic.navigation.AppNavHost
import com.example.moodmusic.ui.theme.MoodMusicTheme

class MainActivity : ComponentActivity() {
    private var mediaPlayer: MediaPlayer? = null
    private val activeSongIdState = mutableIntStateOf(0)

    // Implementasi AudioController
    private val audioController = object : AudioController {
        override fun play(audioUrl: String, songId: Int) {
            stop() // hentikan lagu sebelumnya
            mediaPlayer = MediaPlayer().apply {
                setDataSource(audioUrl)
                setOnPreparedListener { start() }
                prepareAsync()
            }
            activeSongIdState.intValue = songId
        }

        override fun pause() {
            mediaPlayer?.pause()
        }

        override fun stop() {
            mediaPlayer?.stop()
            mediaPlayer?.release()
            mediaPlayer = null
        }

        override fun getActiveSongIdState() = activeSongIdState
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MoodMusicTheme {
                CompositionLocalProvider(
                    LocalActiveSongId provides activeSongIdState,
                    LocalAudioController provides audioController
                ) {
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        AppNavHost()
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer?.release()
    }
}
