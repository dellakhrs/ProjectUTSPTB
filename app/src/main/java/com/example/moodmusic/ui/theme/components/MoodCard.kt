package com.example.moodmusic.ui.theme.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.moodmusic.data.Mood

@Composable
fun MoodCard(mood: Mood, onClick: (Int) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
            .clickable { onClick(mood.id) }, // Efek klik (Ripple)
        colors = CardDefaults.cardColors(
            // Menggunakan warna kustom dari objek Mood
            containerColor = mood.primaryColor.copy(alpha = 0.8f)
        )
    ) {
        Row(
            modifier = Modifier.fillMaxSize().padding(24.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                Icons.Filled.MusicNote,
                contentDescription = mood.name,
                modifier = Modifier.size(48.dp),
                // Pastikan ikon terlihat jelas di atas warna Card
                tint = Color.White
            )
            Spacer(Modifier.width(20.dp))
            Text(
                mood.name,
                style = MaterialTheme.typography.headlineLarge,
                color = Color.White
            )
        }
    }
}