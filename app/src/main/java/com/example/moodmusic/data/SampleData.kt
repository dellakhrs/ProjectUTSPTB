package com.example.moodmusic.data

import androidx.compose.ui.graphics.Color

val sampleMoods = listOf(
    // Warna Cerah dan Menarik
    Mood(1, "Happy", Color(0xFFFDD835)),       // Kuning Cerah (Amber A400)
    Mood(2, "Chill", Color(0xFF00ACC1)),        // Cyan Gelap (Cyan 700)
    Mood(3, "Energetic", Color(0xFFE53935)),    // Merah Terang (Red 600)
    Mood(4, "Sad", Color(0xFF5E35B1))           // Ungu Tua (Deep Purple 700)
)

val sampleSongs = listOf(
    // Mood Happy (ID 1)
    Song(101, 1, "Sunshine Day", "The Groovers", duration = "3:15"),
    Song(102, 1, "Good Day Vibes", "Juno & Co", duration = "4:00"),

    // Mood Chill (ID 2)
    Song(201, 2, "Rainy Mood", "Elias Sound", duration = "3:45"),
    Song(202, 2, "Coffee Shop Jazz", "The Quiet Ensemble", duration = "5:10"),

    // Mood Energetic (ID 3)
    Song(301, 3, "Run Fast Beat", "Velocity Crew", duration = "2:50"),
    Song(302, 3, "Power Surge", "Electro Pulse", duration = "3:05"),

    // Mood Sad (ID 4)
    Song(401, 4, "Silent Night", "Melancholy Man", duration = "4:30"),
    Song(402, 4, "Fading Echo", "Lost Notes", duration = "5:00")
)

fun getSongsByMoodId(moodId: Int): List<Song> {
    return sampleSongs.filter { it.moodId == moodId }
}

fun getSongById(songId: Int): Song? {
    return sampleSongs.find { it.id == songId }
}

fun getMoodById(moodId: Int): Mood? {
    return sampleMoods.find { it.id == moodId }
}