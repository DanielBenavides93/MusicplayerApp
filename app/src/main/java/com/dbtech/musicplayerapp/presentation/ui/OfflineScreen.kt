package com.dbtech.musicplayerapp.presentation.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.dbtech.musicplayerapp.data.db.PlaylistEntity
import com.dbtech.musicplayerapp.presentation.viewmodel.MusicViewModel
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OfflineScreen(viewModel: MusicViewModel = koinViewModel()) {
    val playlist = viewModel.getPlaylist().collectAsState(initial = emptyList())
    val context = LocalContext.current

    val downloadedSongs = playlist.value.filter { it.localPath != null }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Descargas Offline") })
        }
    ) { padding ->
        if (downloadedSongs.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = androidx.compose.ui.Alignment.Center
            ) {
                Text("No tienes canciones descargadas")
            }
        } else {
            LazyColumn(modifier = Modifier.padding(padding)) {
                items(downloadedSongs) { song ->
                    OfflineSongItem(
                        song = song,
                        onPlay = { viewModel.playSongEntity(song, context) },
                        onRemove = { viewModel.removeFromPlaylist(song.songId) }
                    )
                }
            }
        }
    }
}

@Composable
fun OfflineSongItem(
    song: PlaylistEntity,
    onPlay: () -> Unit,
    onRemove: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onPlay() }
    ) {
        Image(
            painter = rememberAsyncImagePainter(song.coverUrl),
            contentDescription = null,
            modifier = Modifier.size(60.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column(Modifier.weight(1f)) {
            Text(song.title, style = MaterialTheme.typography.titleMedium)
            Text(song.artist, style = MaterialTheme.typography.bodyMedium)
        }
        Button(onClick = { onRemove() }) {
            Text("Eliminar")
        }
    }
}