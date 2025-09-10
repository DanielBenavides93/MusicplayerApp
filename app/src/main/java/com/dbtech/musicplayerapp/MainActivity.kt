package com.dbtech.musicplayerapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.dbtech.musicplayerapp.presentation.navigation.AppNavigation
import com.dbtech.musicplayerapp.ui.theme.MusicplayerAPPTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MusicplayerAPPTheme {
                val navController = rememberNavController()
                AppNavigation(navController = navController)
            }
        }
    }
}