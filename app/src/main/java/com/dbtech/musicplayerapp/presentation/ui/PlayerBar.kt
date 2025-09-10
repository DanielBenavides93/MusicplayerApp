package com.dbtech.musicplayerapp.presentation.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.dbtech.musicplayerapp.presentation.viewmodel.MusicViewModel
import org.koin.androidx.compose.koinViewModel


@Composable
fun PlayerBar(viewModel: MusicViewModel = koinViewModel()) {
    val currentSong = viewModel.currentSong.collectAsState()
    val playerState = viewModel.playerState.collectAsState()
    val context = LocalContext.current

    currentSong.value?.let { song ->
        Surface(
            color = MaterialTheme.colorScheme.primaryContainer,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(8.dp)) {
                Text(song.title, style = MaterialTheme.typography.titleMedium)
                Text(song.artist.name, style = MaterialTheme.typography.bodySmall)

                // Slider de progreso
                val progress = if (playerState.value.duration > 0) {
                    playerState.value.currentPosition.toFloat() / playerState.value.duration.toFloat()
                } else 0f

                Slider(
                    value = progress,
                    onValueChange = { newValue ->
                        val newPosition = (newValue * playerState.value.duration).toLong()
                        viewModel.seekTo(newPosition)
                    }
                )

                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(formatTime(playerState.value.currentPosition))
                    Text(formatTime(playerState.value.duration))
                }

                Row(horizontalArrangement = Arrangement.SpaceEvenly) {
                    Button(onClick = { viewModel.playSong(song, context) }) {
                        Text("Play")
                    }
                    Button(onClick = { viewModel.pauseSong() }) {
                        Text("Pause")
                    }
                    Button(onClick = { viewModel.stopSong(context) }) {
                        Text("Stop")
                    }
                }
            }
        }
    }
}

fun formatTime(ms: Long): String {
    val totalSeconds = ms / 1000
    val minutes = totalSeconds / 60
    val seconds = totalSeconds % 60
    return "%d:%02d".format(minutes, seconds)
}