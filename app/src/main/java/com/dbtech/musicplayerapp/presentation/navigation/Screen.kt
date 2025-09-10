package com.dbtech.musicplayerapp.presentation.navigation

sealed class Screen(val route: String, val title: String){
    object Music : Screen("music", "Canciones")
    object Playlist : Screen("playlist", "Favoritos")
    object Offline : Screen("offline", "Descargas")
}