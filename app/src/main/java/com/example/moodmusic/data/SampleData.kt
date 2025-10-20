package com.example.moodmusic.data

import androidx.compose.ui.graphics.Color

private const val SONG_URL_HAPPY_1 = "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-1.mp3"
private const val SONG_URL_HAPPY_2 = "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-8.mp3"
private const val SONG_URL_CHILL_1 = "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-3.mp3"
private const val SONG_URL_CHILL_2 = "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-4.mp3"
private const val SONG_URL_ENERGETIC_1 = "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-7.mp3"
private const val SONG_URL_ENERGETIC_2 = "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-9.mp3"
private const val SONG_URL_SAD_1 = "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-5.mp3"
private const val SONG_URL_SAD_2 = "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-6.mp3"

// Data Sample Baru
val sampleNotifications = listOf(
    Notification(
        id = 1,
        title = "Pembaruan Musik!",
        summary = "100+ lagu baru di mood 'Energetic' telah rilis. Coba sekarang!",
        detail = "Kami telah merilis pembaruan besar yang mencakup peningkatan performa, perbaikan bug, dan yang paling penting, lebih dari seratus lagu baru ditambahkan ke kategori 'Energetic' dan 'Chill'. Nikmati pengalaman musik yang lebih segar!",
        date = "5m yang lalu",
        isRead = false
    ),
    Notification(
        id = 2,
        title = "Promo Premium Eksklusif",
        summary = "Penawaran eksklusif 50% untuk Premium segera berakhir besok.",
        detail = "Tingkatkan pengalaman mendengarkan Anda tanpa iklan dan kualitas audio HD. Promo 50% untuk langganan Premium berakhir besok malam. Segera berlangganan sebelum terlambat!",
        date = "2 jam yang lalu",
        isRead = false
    ),
    Notification(
        id = 3,
        title = "Lagu Pilihan Hari Ini",
        summary = "Mood 'Happy' merekomendasikan: 'Sunshine Vibe' oleh The Beats.",
        detail = "Kami memilihkan lagu 'Sunshine Vibe' khusus untuk mood Anda hari ini. Lagu ini sempurna untuk memulai hari dengan semangat positif. Dengarkan sekarang dan bagikan ke teman Anda!",
        date = "1 hari yang lalu",
        isRead = true
    )
)

fun getNotificationById(id: Int): Notification? {
    return sampleNotifications.find { it.id == id }
}

val sampleMoods = listOf(
    Mood(1, "Happy", Color(0xFFFDD835)),       // Kuning Cerah (Amber A400)
    Mood(2, "Chill", Color(0xFF00ACC1)),        // Cyan Gelap (Cyan 700)
    Mood(3, "Energetic", Color(0xFFE53935)),    // Merah Terang (Red 600)
    Mood(4, "Sad", Color(0xFF5E35B1))           // Ungu Tua (Deep Purple 700)
)

val sampleSongs = listOf(
    // Mood Happy (ID 1)
    Song(101, 1, "Sunshine Day", "The Groovers", duration = "3:15", audioUrl = SONG_URL_HAPPY_1),
    Song(102, 1, "Good Day Vibes", "Juno & Co", duration = "4:00", audioUrl = SONG_URL_HAPPY_2),

    // Mood Chill (ID 2)
    Song(201, 2, "Rainy Mood", "Elias Sound", duration = "3:45", audioUrl = SONG_URL_CHILL_1),
    Song(202, 2, "Coffee Shop Jazz", "The Quiet Ensemble", duration = "5:10", audioUrl = SONG_URL_CHILL_2),

    // Mood Energetic (ID 3)
    Song(301, 3, "Run Fast Beat", "Velocity Crew", duration = "2:50", audioUrl = SONG_URL_ENERGETIC_1),
    Song(302, 3, "Power Surge", "Electro Pulse", duration = "3:05", audioUrl = SONG_URL_ENERGETIC_2),

    // Mood Sad (ID 4)
    Song(401, 4, "Silent Night", "Melancholy Man", duration = "4:30", audioUrl = SONG_URL_SAD_1),
    Song(402, 4, "Fading Echo", "Lost Notes", duration = "5:00", audioUrl = SONG_URL_SAD_2)
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