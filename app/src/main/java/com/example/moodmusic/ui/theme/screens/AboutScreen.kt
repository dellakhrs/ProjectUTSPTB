package com.example.moodmusic.ui.theme.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.moodmusic.R // Untuk logo aplikasi
import androidx.compose.ui.res.painterResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutScreen(onBackClick: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Tentang MoodMusic") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Kembali")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 24.dp, vertical = 20.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // Logo Aplikasi (Menggunakan placeholder icon)
            Image(
                painter = painterResource(id = R.drawable.logo_aplikasi),
                contentDescription = "Logo MoodMusic",
                contentScale = ContentScale.Fit,
                modifier = Modifier.size(96.dp).padding(top = 16.dp) // Ukuran yang lebih besar
            )

            Spacer(Modifier.height(8.dp))

            // Nama dan Versi
            Text(
                text = "MoodMusic",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Versi 1.0.0",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(Modifier.height(32.dp))

            // Garis Pembatas
            HorizontalDivider()
            Spacer(Modifier.height(24.dp))

            // Deskripsi Singkat
            Text(
                text = "MoodMusic adalah aplikasi pemutar musik revolusioner yang dirancang khusus untuk memahami dan melayani suasana hati Anda. Kami percaya bahwa musik yang tepat dapat mengubah hari Anda, dan itulah mengapa kami mengorganisasi perpustakaan kami berdasarkan emosi, bukan hanya genre.",
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Justify,
                lineHeight = 24.sp
            )

            Spacer(Modifier.height(16.dp))

            // Fitur Utama
            Text(
                text = "Fitur Unggulan:",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
            )

            BulletPointText(text = "Navigasi Berbasis Mood: Cepat temukan 'Happy', 'Chill', atau 'Energetic'.")
            BulletPointText(text = "Warna Dinamis: Antarmuka yang menyesuaikan dengan mood yang sedang Anda dengarkan.")
            BulletPointText(text = "Kontrol Playback: Pengalaman mendengarkan tanpa gangguan saat berpindah layar.")

            Spacer(Modifier.height(24.dp))

            // Footer
            Text(
                text = "© 2025 Della Khairunnisa. Dibuat dengan Jetpack Compose.",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

// Komponen Pembantu untuk Bullet Point
@Composable
fun BulletPointText(text: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.Top
    ) {
        Text(
            text = "•",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(end = 8.dp)
        )
        Text(
            text = text,
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Justify
        )
    }
}