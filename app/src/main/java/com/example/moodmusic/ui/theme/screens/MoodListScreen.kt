package com.example.moodmusic.ui.theme.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.moodmusic.data.Mood
import com.example.moodmusic.ui.theme.components.MoodCard
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MoodListScreen(moods: List<Mood>, onMoodClick: (Int) -> Unit) {

    // State lokal untuk mengelola input pencarian
    var searchText by remember { mutableStateOf("") }

    // LOGIKA FILTERING (Akan dihitung ulang saat searchText atau moods berubah)
    val filteredMoods = remember(moods, searchText) {
        if (searchText.isBlank()) {
            // Jika kolom pencarian kosong, tampilkan semua mood
            moods
        } else {
            // Filter mood yang namanya mengandung teks pencarian (case-insensitive)
            moods.filter {
                it.name.contains(searchText, ignoreCase = true)
            }
        }
    }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Beranda MoodMusic") }) }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp), // Padding horizontal untuk keseluruhan konten
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // ============== KOLOM SEARCH BAR ==============
            OutlinedTextField(
                value = searchText,
                onValueChange = { searchText = it },
                label = { Text("Cari Mood (Happy, Chill, dll.)...") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search Icon") },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp) // Padding atas dan bawah
            )

            // Daftar Mood (LazyColumn)
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(bottom = 16.dp), // Padding bawah agar list tidak mentok
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Menggunakan daftar yang sudah difilter
                items(filteredMoods) { mood ->
                    MoodCard(mood = mood, onClick = onMoodClick)
                }
            }
        }
    }
}