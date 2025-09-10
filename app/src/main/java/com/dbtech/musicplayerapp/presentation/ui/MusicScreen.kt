package com.dbtech.musicplayerapp.presentation.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.dbtech.musicplayerapp.presentation.viewmodel.MusicViewModel
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MusicScreen(viewModel: MusicViewModel = koinViewModel(), navController: NavController? = null) {
    val songs = viewModel.songs.collectAsState()
    val uiState = viewModel.uiState.collectAsState()

    when (val state = uiState.value) {
        is UiState.Loading -> {
            CircularProgressIndicator()
        }
        is UiState.Success -> {
            Snackbar { Text(state.data.toString()) }
        }
        is UiState.Error -> {
            Snackbar { Text(state.message) }
        }
        UiState.Idle -> {
            // no hacer nada
        }
    }

    LaunchedEffect(Unit) {
        viewModel.loadTopSongs()
    }
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Top Deezer Songs") })
        },
        bottomBar = {
            PlayerBar()
        }
    ) { padding ->
        LazyColumn(modifier = Modifier.padding(padding)) {
            items(songs.value) { song ->
                SongItem(song = song, onPlayClick = { context ->
                    viewModel.playSong(song, context)
                }
                )
            }
        }
    }
}