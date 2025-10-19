package com.example.moodmusic.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.material3.*
import androidx.compose.ui.graphics.Color
import android.R.attr.content


private val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80
)

private val LightColorScheme = lightColorScheme(
    primary = Purple40,
    secondary = PurpleGrey40,
    tertiary = Pink40

    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
)

// Fungsi Tema Kustom: Menerima Mood Color
@Composable
fun MoodMusicTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    moodColor: Color? = null // Warna dinamis dari Mood
) {
    val colorScheme = when {
        // ... (Logika Dynamic Color bawaan Android)
        moodColor != null -> {
            // Gunakan warna mood sebagai primary color
            lightColorScheme(
                primary = moodColor,
                secondary = moodColor.copy(alpha = 0.7f),
                background = Color.White,
                surface = Color.White,
                onBackground = Color.Black,
                onSurface = Color.Black
            )
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    // ... (Logika penyesuaian status bar)

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}