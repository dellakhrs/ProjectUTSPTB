package com.example.moodmusic.ui.theme.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.yourpackage.moodmusic.data.Mood

@Composable
fun MoodListScreen(moods: List<Mood>, onMoodClick: (Int) -> Unit) {
    Scaffold(
        topBar = { TopAppBar(title = { Text("MoodMusic") }) }
    ) { padding ->
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(padding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(moods) { mood ->
                MoodCard(mood = mood, onClick = onMoodClick)
            }
        }
    }
}

// Komponen Card untuk setiap Mood (Memenuhi prinsip Material Design)
@Composable
fun MoodCard(mood: Mood, onClick: (Int) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
            .clickable { onClick(mood.id) }, // Efek klik (Ripple)
        colors = CardDefaults.cardColors(
            containerColor = mood.primaryColor.copy(alpha = 0.8f) // Warna card dari Mood
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