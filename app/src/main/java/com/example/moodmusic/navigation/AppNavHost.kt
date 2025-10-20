package com.example.moodmusic.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
import com.example.moodmusic.ui.theme.screens.ProfileScreen
import androidx.compose.material.icons.filled.Notifications
import com.example.moodmusic.ui.theme.screens.NotificationScreen
import com.example.moodmusic.ui.theme.screens.NotificationDetailScreen
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.unit.dp
import com.example.moodmusic.ui.theme.screens.AboutScreen
import kotlinx.coroutines.launch
import com.example.moodmusic.ui.theme.screens.SettingsScreen

object Routes {
    const val SONG_LIST = "songList/{moodId}"
    const val SONG_DETAIL = "songDetail/{songId}"
    const val NOTIFICATION_DETAIL = "notificationDetail/{notificationId}"

    const val HOME_ROOT = "homeRoot"
    const val NOTIFICATION_ROOT = "notificationRoot"
    const val PROFILE_ROOT = "profileRoot"
    const val SETTINGS_ROOT = "settingsRoot"
    const val ABOUT_ROOT = "aboutRoot"

    const val MAIN_SCREEN = "mainScreen"
}

data class BottomNavItem(
    val route: String,
    val icon: ImageVector,
    val label: String
)

val bottomNavItems = listOf(
    BottomNavItem(Routes.HOME_ROOT, Icons.Default.Home, "Beranda"),
    BottomNavItem(Routes.NOTIFICATION_ROOT, Icons.Default.Notifications, "Notifikasi"),
    BottomNavItem(Routes.PROFILE_ROOT, Icons.Default.AccountCircle, "Profil")
)

data class DrawerItem(
    val route: String,
    val icon: ImageVector,
    val label: String
)

val drawerItems = listOf(
    DrawerItem(Routes.HOME_ROOT, Icons.Default.Home, "Beranda"),
    DrawerItem(Routes.SETTINGS_ROOT, Icons.Default.Settings, "Pengaturan")
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    val showBottomBar = currentRoute in bottomNavItems.map { it.route } ||
            currentRoute?.startsWith("songList") == true ||
            currentRoute?.startsWith("songDetail") == true ||
            currentRoute?.startsWith("notificationDetail") == true

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Text(
                    "MoodMusic Menu",
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.padding(16.dp)
                )
                HorizontalDivider()
                Spacer(Modifier.height(8.dp))

                drawerItems.forEach { item ->
                    val isSelected = currentRoute == item.route ||
                            (item.route == Routes.HOME_ROOT &&
                                    (currentRoute?.startsWith("songList") == true ||
                                            currentRoute?.startsWith("songDetail") == true))

                    NavigationDrawerItem(
                        icon = { Icon(item.icon, contentDescription = item.label) },
                        label = { Text(item.label) },
                        selected = isSelected,
                        onClick = {
                            scope.launch { drawerState.close() }

                            if (!isSelected) {
                                navController.navigate(item.route) {
                                    popUpTo(navController.graph.startDestinationId) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        },
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp)
                    )
                }
            }
        },
        content = {
            Scaffold(
                bottomBar = {
                    if (showBottomBar) {
                        NavigationBar(
                            containerColor = MaterialTheme.colorScheme.surfaceContainer
                        ) {
                            bottomNavItems.forEach { item ->
                                val isSelected = currentRoute == item.route ||
                                        (item.route == Routes.HOME_ROOT &&
                                                (currentRoute?.startsWith("songList") == true ||
                                                        currentRoute?.startsWith("songDetail") == true)) ||
                                        (item.route == Routes.NOTIFICATION_ROOT &&
                                                currentRoute?.startsWith("notificationDetail") == true)

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
                }
            ) { padding ->
                NavHost(
                    navController = navController,
                    startDestination = Routes.HOME_ROOT,
                    Modifier.padding(padding)
                ) {
                    composable(Routes.HOME_ROOT) {
                        MoodListScreen(
                            moods = sampleMoods,
                            onMoodClick = { moodId ->
                                navController.navigate("songList/$moodId")
                            },
                            onMenuClick = { scope.launch { drawerState.open() } }
                        )
                    }

                    composable(Routes.NOTIFICATION_ROOT) {
                        NotificationScreen(
                            onNotificationClick = { notificationId ->
                                navController.navigate("notificationDetail/$notificationId")
                            }
                        )
                    }

                    composable(Routes.PROFILE_ROOT) {
                        ProfileScreen()
                    }

                    composable(Routes.SETTINGS_ROOT) {
                        SettingsScreen(
                            onMenuClick = { scope.launch { drawerState.open() } },
                            navController = navController
                        )
                    }

                    composable(Routes.ABOUT_ROOT) {
                        AboutScreen(onBackClick = { navController.popBackStack() })
                    }

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
                            onSongClick = { songId ->
                                navController.navigate("songDetail/$songId")
                            },
                            onBackClick = { navController.popBackStack() }
                        )
                    }

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

                    composable(
                        Routes.NOTIFICATION_DETAIL,
                        arguments = listOf(navArgument("notificationId") { type = NavType.IntType }),
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
                        val notificationId = backStackEntry.arguments?.getInt("notificationId") ?: 0
                        NotificationDetailScreen(
                            notificationId = notificationId,
                            onBackClick = { navController.popBackStack() }
                        )
                    }
                }
            }
        }
    )
}

@Composable
fun AppNavHost() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Routes.MAIN_SCREEN) {
        composable(Routes.MAIN_SCREEN) {
            MainScreen()
        }
    }
}