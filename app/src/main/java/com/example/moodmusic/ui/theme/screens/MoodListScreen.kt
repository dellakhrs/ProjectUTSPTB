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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.filled.Menu

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MoodListScreen(
    moods: List<Mood>,
    onMoodClick: (Int) -> Unit,
    onMenuClick: () -> Unit // <-- TAMBAHKAN PARAMETER INI
) {

    var searchText by remember { mutableStateOf("") }
    val filteredMoods = remember(moods, searchText) {
        if (searchText.isBlank()) {
            moods
        } else {
            moods.filter {
                it.name.contains(searchText, ignoreCase = true)
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row {
                        Text("Beranda ")
                        Text(
                            "MoodMusic",
                            fontWeight = FontWeight.Bold
                        )
                    }
                },
                // TAMBAHKAN NAVIGATION ICON UNTUK MEMBUKA DRAWER
                navigationIcon = {
                    IconButton(onClick = onMenuClick) { // <-- Panggil aksi dari AppNavHost
                        Icon(Icons.Default.Menu, contentDescription = "Menu Drawer")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // ============== KOLOM SEARCH BAR ==============
            OutlinedTextField(
                value = searchText,
                onValueChange = { searchText = it },
                label = { Text("Cari Mood atau Lagu...") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search Icon") },
                singleLine = true,
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
            )

            // Daftar Mood (LazyColumn)
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(bottom = 16.dp),
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