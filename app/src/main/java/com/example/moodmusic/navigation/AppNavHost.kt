package com.example.moodmusic.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.moodmusic.data.sampleMoods
import com.example.moodmusic.ui.theme.screens.MoodListScreen
import com.example.moodmusic.ui.theme.screens.SongDetailScreen
import com.example.moodmusic.ui.theme.screens.SongListScreen

object Routes {
    const val MOOD_LIST = "moodList"
    const val SONG_LIST = "songList/{moodId}"
    const val SONG_DETAIL = "songDetail/{songId}"
}

@Composable
fun AppNavHost() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Routes.MOOD_LIST) {

        // Transisi Sederhana (Slide Horizontal) untuk semua layar
        val slideDuration = 400

        // Layar 1: Daftar Mood
        composable(
            Routes.MOOD_LIST,
            exitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Start,
                    animationSpec = tween(slideDuration)
                )
            }
        ) {
            MoodListScreen(
                moods = sampleMoods,
                onMoodClick = { moodId ->
                    navController.navigate("songList/$moodId")
                }
            )
        }

        // Layar 2: Daftar Lagu
        composable(
            Routes.SONG_LIST,
            arguments = listOf(navArgument("moodId") { type = NavType.IntType }),
            enterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Start,
                    animationSpec = tween(slideDuration)
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Start,
                    animationSpec = tween(slideDuration)
                )
            },
            popEnterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.End,
                    animationSpec = tween(slideDuration)
                )
            }
        ) { backStackEntry ->
            val moodId = backStackEntry.arguments?.getInt("moodId") ?: 0
            SongListScreen(
                moodId = moodId,
                onSongClick = { songId ->
                    navController.navigate("songDetail/$songId")
                },
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
                    animationSpec = tween(slideDuration)
                )
            },
            popExitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.End,
                    animationSpec = tween(slideDuration)
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