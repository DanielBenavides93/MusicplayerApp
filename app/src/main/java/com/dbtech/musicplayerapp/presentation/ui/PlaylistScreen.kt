package com.dbtech.musicplayerapp.presentation.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import com.dbtech.musicplayerapp.presentation.viewmodel.MusicViewModel
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlaylistScreen(viewModel: MusicViewModel = koinViewModel()) {
    val playlist = viewModel.getPlaylist().collectAsState(initial = emptyList())

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Mis Favoritos") })
        }
    ) { padding ->
        LazyColumn(modifier = Modifier.padding(padding)) {
            items(playlist.value) { song ->
                PlaylistItem(song, viewModel)
            }
        }
    }
}