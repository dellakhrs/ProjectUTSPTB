package com.example.moodmusic.ui.theme.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Mail
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.moodmusic.R
import androidx.compose.ui.layout.ContentScale
import androidx.compose.material.icons.filled.DateRange

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Profil Pengguna") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 24.dp, vertical = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // Area Foto Profil
            ProfilePicture(R.drawable.foto_della)

            Spacer(Modifier.height(30.dp))

            // Nama Pengguna
            Text(
                text = "Della Khairunnisa",
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )

            Spacer(Modifier.height(4.dp))

            // Status atau Role
            Text(
                text = "Pengguna MoodMusic Premium",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.secondary
            )

            // GARIS PEMISAH LEBIH MENARIK
            HorizontalDivider(
                modifier = Modifier.padding(vertical = 24.dp),
                thickness = 1.dp,
                color = MaterialTheme.colorScheme.outlineVariant
            )

            // Detail Kontak/Akun dalam bentuk Card

            // 1. DETAIL BERGABUNG SEJAK (FITUR BARU)
            ProfileDetailCard(
                icon = Icons.Default.DateRange, // Icon Kalender/Tanggal
                label = "Bergabung Sejak",
                value = "13 Oktober 2025"
            )

            Spacer(Modifier.height(16.dp))

            // 2. DETAIL EMAIL
            ProfileDetailCard(
                icon = Icons.Default.Mail,
                label = "Email",
                value = "dellakhairunnisa43@gmail.com"
            )

            Spacer(Modifier.height(16.dp))

            // 3. DETAIL TIPE AKUN
            ProfileDetailCard(
                icon = Icons.Default.Person,
                label = "Tipe Akun",
                value = "Premium"
            )
        }
    }
}

// Fungsi ProfilePicture sudah dioptimalkan sebelumnya
@Composable
fun ProfilePicture(drawableId: Int) {
    Image(
        painter = painterResource(id = drawableId),
        contentDescription = "Foto Profil",
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .size(120.dp)
            .clip(CircleShape)
    )
}

// Fungsi ProfileDetailCard tidak berubah
@Composable
fun ProfileDetailCard(icon: ImageVector, label: String, value: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainerLow)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                icon,
                contentDescription = label,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(24.dp)
            )

            Spacer(Modifier.width(16.dp))

            Column {
                Text(
                    text = label,
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = value,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}