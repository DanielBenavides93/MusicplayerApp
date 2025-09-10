package com.dbtech.musicplayerapp.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.LibraryMusic
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun BottomNavBar(navController: NavController) {
    val items = listOf(
        Screen.Music,
        Screen.Playlist,
        Screen.Offline
    )

    NavigationBar {
        val navBackStackEntry = navController.currentBackStackEntryAsState().value
        val currentRoute = navBackStackEntry?.destination?.route

        items.forEach { screen ->
            NavigationBarItem(
                icon = {
                    when (screen) {
                        is Screen.Music -> Icon(Icons.Default.LibraryMusic, contentDescription = screen.title)
                        is Screen.Playlist -> Icon(Icons.Default.Favorite, contentDescription = screen.title)
                        is Screen.Offline -> Icon(Icons.Default.Download, contentDescription = screen.title)
                    }
                },
                label = { Text(screen.title) },
                selected = currentRoute == screen.route,
                onClick = { navController.navigate(screen.route) }
            )
        }
    }
}