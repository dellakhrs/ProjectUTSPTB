package com.example.moodmusic.ui.theme.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.moodmusic.data.Song

@Composable
fun SongListItem(song: Song, onClick: (Int) -> Unit) {
    ListItem(
        headlineContent = { Text(song.title) },
        supportingContent = { Text(song.artist) },
        trailingContent = { Text(song.duration) },
        modifier = Modifier.clickable { onClick(song.id) }
    )
    HorizontalDivider(Modifier.padding(horizontal = 16.dp))
}