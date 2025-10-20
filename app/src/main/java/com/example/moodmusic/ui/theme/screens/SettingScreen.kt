package com.example.moodmusic.ui.theme.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.moodmusic.navigation.Routes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(onMenuClick: () -> Unit, navController: NavController) {
    var isDarkThemeEnabled by remember { mutableStateOf(false) }
    var isHighQualityAudio by remember { mutableStateOf(true) }
    var isNotificationEnabled by remember { mutableStateOf(true) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Pengaturan Aplikasi") },
                navigationIcon = {
                    IconButton(onClick = onMenuClick) {
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
                .verticalScroll(rememberScrollState()),
        ) {
            SettingsHeader("Tampilan & Umum")

            SettingToggleItem(
                icon = Icons.Default.DarkMode,
                title = "Mode Gelap",
                summary = "Mengubah tema aplikasi menjadi gelap.",
                checked = isDarkThemeEnabled,
                onCheckedChange = { isDarkThemeEnabled = it }
            )

            SettingToggleItem(
                icon = Icons.Default.NotificationsActive,
                title = "Izinkan Notifikasi",
                summary = "Menerima pembaruan dan rekomendasi lagu.",
                checked = isNotificationEnabled,
                onCheckedChange = { isNotificationEnabled = it }
            )

            HorizontalDivider(modifier = Modifier.padding(vertical = 16.dp))

            SettingsHeader("Audio & Pemutaran")

            SettingToggleItem(
                icon = Icons.Default.HighQuality,
                title = "Kualitas Audio Tinggi (HD)",
                summary = "Membutuhkan data lebih banyak untuk streaming.",
                checked = isHighQualityAudio,
                onCheckedChange = { isHighQualityAudio = it }
            )
            SettingClickableItem(
                icon = Icons.Default.Info,
                title = "Tentang Aplikasi",
                onClick = { navController.navigate(Routes.ABOUT_ROOT) }
            )
            Spacer(Modifier.height(80.dp))
        }
    }
}

@Composable
fun SettingsHeader(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleMedium,
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colorScheme.primary,
        modifier = Modifier.padding(start = 16.dp, top = 24.dp, bottom = 8.dp)
    )
}

@Composable
fun SettingToggleItem(
    icon: ImageVector,
    title: String,
    summary: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    ListItem(
        headlineContent = { Text(title) },
        supportingContent = { Text(summary) },
        leadingContent = {
            Icon(
                icon,
                contentDescription = title,
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        },
        trailingContent = {
            Switch(
                checked = checked,
                onCheckedChange = onCheckedChange
            )
        },
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
fun SettingClickableItem(
    icon: ImageVector,
    title: String,
    onClick: () -> Unit
) {
    ListItem(
        headlineContent = { Text(title) },
        leadingContent = {
            Icon(
                icon,
                contentDescription = title,
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        },
        trailingContent = {
            Icon(
                Icons.AutoMirrored.Filled.ArrowForward,
                contentDescription = "Navigate",
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        },
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
    )
}