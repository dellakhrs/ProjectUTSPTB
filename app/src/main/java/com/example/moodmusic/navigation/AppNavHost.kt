package com.example.moodmusic.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.moodmusic.data.sampleMoods
import com.example.moodmusic.ui.theme.screens.MoodListScreen
import com.example.moodmusic.ui.theme.screens.SongDetailScreen
import com.example.moodmusic.ui.theme.screens.SongListScreen
import com.example.moodmusic.ui.theme.screens.ProfileScreen // <-- WAJIB DIIMPOR


object Routes {
    // Navigasi Detail (Sub-Route)
    const val SONG_LIST = "songList/{moodId}"
    const val SONG_DETAIL = "songDetail/{songId}"

    // Navigasi Root (Level 1, untuk Bottom Bar)
    const val HOME_ROOT = "homeRoot"
    const val FAVORITE_ROOT = "favoriteRoot"
    const val PROFILE_ROOT = "profileRoot"

    // Container Root
    const val MAIN_SCREEN = "mainScreen" // Start Destination utama
}

// Data Class untuk Item Bottom Bar
data class BottomNavItem(
    val route: String,
    val icon: ImageVector,
    val label: String
)

val bottomNavItems = listOf(
    BottomNavItem(Routes.HOME_ROOT, Icons.Default.Home, "Beranda"),
    BottomNavItem(Routes.FAVORITE_ROOT, Icons.Default.Favorite, "Favorit"),
    BottomNavItem(Routes.PROFILE_ROOT, Icons.Default.AccountCircle, "Profil")
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        bottomBar = {
            NavigationBar(
                containerColor = MaterialTheme.colorScheme.surfaceContainer
            ) {
                bottomNavItems.forEach { item ->
                    // Logika untuk menandai item yang dipilih
                    val isSelected = currentRoute == item.route ||
                            (item.route == Routes.HOME_ROOT && (currentRoute?.startsWith("songList") == true || currentRoute?.startsWith("songDetail") == true))

                    NavigationBarItem(
                        icon = { Icon(item.icon, contentDescription = item.label) },
                        label = { Text(item.label) },
                        selected = isSelected,
                        onClick = {
                            if (currentRoute != item.route) {
                                navController.navigate(item.route) {
                                    popUpTo(navController.graph.startDestinationId) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        }
                    )
                }
            }
        }
    ) { padding ->
        // NavHost untuk mengelola transisi layar di dalam Scaffold
        NavHost(
            navController = navController,
            startDestination = Routes.HOME_ROOT,
            Modifier.padding(padding)
        ) {
            // =========================================================================
            // 1. ROOT SCREENS (Ditampilkan di Bottom Bar)
            // =========================================================================

            // HOME ROOT
            composable(Routes.HOME_ROOT) {
                MoodListScreen(
                    moods = sampleMoods,
                    onMoodClick = { moodId -> navController.navigate("songList/$moodId") }
                )
            }

            // FAVORITE ROOT
            composable(Routes.FAVORITE_ROOT) {
                Text("Halaman Favorit Anda", modifier = Modifier.fillMaxSize().wrapContentSize(Alignment.Center))
            }

            // PROFILE ROOT <-- DIPERBAIKI DI SINI
            composable(Routes.PROFILE_ROOT) {
                ProfileScreen() // <-- Memanggil ProfileScreen dengan konten penuh
            }

            // =========================================================================
            // 2. NESTED SCREENS (Detail)
            // =========================================================================

            // Layar 2: Daftar Lagu
            composable(
                Routes.SONG_LIST,
                arguments = listOf(navArgument("moodId") { type = NavType.IntType }),
                enterTransition = {
                    slideIntoContainer(
                        AnimatedContentTransitionScope.SlideDirection.Start,
                        animationSpec = tween(400)
                    )
                },
                exitTransition = {
                    slideOutOfContainer(
                        AnimatedContentTransitionScope.SlideDirection.Start,
                        animationSpec = tween(400)
                    )
                },
                popEnterTransition = {
                    slideIntoContainer(
                        AnimatedContentTransitionScope.SlideDirection.End,
                        animationSpec = tween(400)
                    )
                }
            ) { backStackEntry ->
                val moodId = backStackEntry.arguments?.getInt("moodId") ?: 0
                SongListScreen(
                    moodId = moodId,
                    onSongClick = { songId -> navController.navigate("songDetail/$songId") },
                    onBackClick = { navController.popBackStack() }
                )
            }

            // Layar 3: Detail Lagu
            composable(
                Routes.SONG_DETAIL,
                arguments = listOf(navArgument("songId") { type = NavType.IntType }),
                enterTransition = {
                    slideIntoContainer(
                        AnimatedContentTransitionScope.SlideDirection.Start,
                        animationSpec = tween(400)
                    )
                },
                popExitTransition = {
                    slideOutOfContainer(
                        AnimatedContentTransitionScope.SlideDirection.End,
                        animationSpec = tween(400)
                    )
                }
            ) { backStackEntry ->
                val songId = backStackEntry.arguments?.getInt("songId") ?: 0
                SongDetailScreen(
                    songId = songId,
                    onBackClick = { navController.popBackStack() }
                )
            }
        }
    }
}


// NavHost Utama (Mulai Aplikasi)
@Composable
fun AppNavHost() {
    val navController = rememberNavController()

    // Start Destination sekarang adalah MainScreen yang memuat Bottom Bar
    NavHost(navController = navController, startDestination = Routes.MAIN_SCREEN) {
        composable(Routes.MAIN_SCREEN) {
            MainScreen()
        }
    }
}