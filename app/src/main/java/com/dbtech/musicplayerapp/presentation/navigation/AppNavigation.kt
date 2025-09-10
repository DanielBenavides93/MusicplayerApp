package com.dbtech.musicplayerapp.presentation.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.dbtech.musicplayerapp.presentation.ui.MusicScreen
import com.dbtech.musicplayerapp.presentation.ui.OfflineScreen
import com.dbtech.musicplayerapp.presentation.ui.PlaylistScreen

@Composable
fun AppNavigation(navController: NavHostController) {
    Scaffold(
        bottomBar = { BottomNavBar(navController) } // Barra inferior fija
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = Screen.Music.route,
            modifier = Modifier.padding(paddingValues)
        ) {
            composable("music") { MusicScreen(navController = navController) }
            composable("playlist") { PlaylistScreen() }
            composable("offline") { OfflineScreen() } // nueva pantalla
        }
    }
}