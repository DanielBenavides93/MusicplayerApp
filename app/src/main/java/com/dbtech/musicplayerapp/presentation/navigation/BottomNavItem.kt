package com.dbtech.musicplayerapp.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.LibraryMusic
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavItem (val screen: Screen, val icon: ImageVector){
    object Music : BottomNavItem(Screen.Music, Icons.Filled.LibraryMusic)
    object Playlist : BottomNavItem(Screen.Playlist, Icons.Filled.Favorite)
    object Offline : BottomNavItem(Screen.Offline, Icons.Filled.Download)
}