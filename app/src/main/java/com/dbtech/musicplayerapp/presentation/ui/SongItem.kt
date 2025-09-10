package com.dbtech.musicplayerapp.presentation.ui

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.dbtech.musicplayerapp.data.api.SongDto
import com.dbtech.musicplayerapp.presentation.viewmodel.MusicViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun SongItem(song: SongDto, onPlayClick: (Context) -> Unit, viewModel: MusicViewModel = koinViewModel()) {

    val context = LocalContext.current

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onPlayClick(context) }
    ) {
        Image(
            painter = rememberAsyncImagePainter(song.album.coverUrl),
            contentDescription = null,
            modifier = Modifier.size(60.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column(Modifier.weight(1f)) {
            Text(song.title, style = MaterialTheme.typography.titleMedium)
            Text(song.artist.name, style = MaterialTheme.typography.bodyMedium)
        }
        Button(onClick = { viewModel.addToPlaylist(song) }) {
            Text("Fav")
        }

        Button(onClick = { viewModel.downloadSong(song) }) {
            Text("Descargar")
        }

    }
}