package com.example.moodmusic.ui.theme.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.moodmusic.data.Mood
import com.example.moodmusic.ui.theme.components.MoodCard

@OptIn(ExperimentalMaterial3Api::class) // (Opsional) untuk menghilangkan warning API
@Composable
fun MoodListScreen(moods: List<Mood>, onMoodClick: (Int) -> Unit) {
    Scaffold(
        topBar = { TopAppBar(title = { Text("MoodMusic") }) }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(moods) { mood ->
                // Memanggil MoodCard dari package components
                MoodCard(mood = mood, onClick = onMoodClick)
            }
        }
    }
}
