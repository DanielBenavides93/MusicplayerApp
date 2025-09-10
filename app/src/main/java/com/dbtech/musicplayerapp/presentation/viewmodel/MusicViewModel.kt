package com.dbtech.musicplayerapp.presentation.viewmodel

import android.content.Context
import android.content.Intent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dbtech.musicplayerapp.data.api.AlbumDto
import com.dbtech.musicplayerapp.data.api.ArtistDto
import com.dbtech.musicplayerapp.data.api.SongDto
import com.dbtech.musicplayerapp.data.db.PlaylistEntity
import com.dbtech.musicplayerapp.data.player.MusicPlayer
import com.dbtech.musicplayerapp.data.player.PlayerState
import com.dbtech.musicplayerapp.data.repository.MusicRepository
import com.dbtech.musicplayerapp.data.repository.PlaylistRepository
import com.dbtech.musicplayerapp.domain.repository.DownloadRepository
import com.dbtech.musicplayerapp.presentation.ui.UiState
import com.dbtech.musicplayerapp.service.MusicService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.io.File

class MusicViewModel(
    private val repository: MusicRepository,
    private val player: MusicPlayer,
    private val playlistRepository: PlaylistRepository,
    private val downloadRepository: DownloadRepository
) : ViewModel() {

    private val _songs = MutableStateFlow<List<SongDto>>(emptyList())
    val songs: StateFlow<List<SongDto>> = _songs

    val playerState: StateFlow<PlayerState> = player.playerState

    private val _currentSong = MutableStateFlow<SongDto?>(null)
    val currentSong: StateFlow<SongDto?> = _currentSong

    private val _uiState = MutableStateFlow<UiState<Any>>(UiState.Idle)
    val uiState: StateFlow<UiState<Any>> = _uiState

    fun loadTopSongs() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            try {
                val response = repository.getTopSongs()
                _songs.value = response.tracks.data
                _uiState.value = UiState.Success("Canciones cargadas con éxito")
            } catch (e: Exception) {
                _uiState.value = UiState.Error("Error al cargar canciones: ${e.message}")
            }
        }
    }

    fun playSong(song: SongDto, context: Context) {
        _currentSong.value = song

        val localFile = File(context.filesDir, "${song.id}.mp3")
        if (localFile.exists()){
            player.play(localFile.absolutePath)
        } else{
            player.play(song.preview)
        }

        val intent = Intent(context, MusicService::class.java)
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            context.startForegroundService(intent)
        } else {
            context.startService(intent)
        }

    }

    fun pauseSong() {
        player.pause()
    }

    fun stopSong(context: Context) {
        player.stop()
        _currentSong.value = null
        context.stopService(Intent(context, MusicService::class.java))
    }

    fun seekTo(position: Long) {
        player.seekTo(position)
    }

    override fun onCleared() {
        super.onCleared()
        player.release()
    }

    fun addToPlaylist(song: SongDto) {
        viewModelScope.launch {
            val entity = PlaylistEntity(
                songId = song.id,
                title = song.title,
                artist = song.artist.name,
                previewUrl = song.preview,
                coverUrl = song.album.coverUrl
            )
            playlistRepository.addSong(entity)
        }
    }

    fun removeFromPlaylist(songId: Long) {
        viewModelScope.launch {
            playlistRepository.removeSong(songId)
        }
    }

    fun getPlaylist(): Flow<List<PlaylistEntity>> {
        return playlistRepository.getAllSongs()
    }

    fun downloadSong(song: SongDto) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            try {
                val localPath = downloadRepository.downloadSong(song.preview, "${song.id}.mp3")
                if (localPath != null) {
                    val entity = PlaylistEntity(
                        songId = song.id,
                        title = song.title,
                        artist = song.artist.name,
                        previewUrl = song.preview,
                        coverUrl = song.album.coverUrl,
                        localPath = localPath
                    )
                    playlistRepository.addSong(entity)
                    _uiState.value = UiState.Success("Canción descargada: ${song.title}")
                } else {
                    _uiState.value = UiState.Error("No se pudo descargar ${song.title}")
                }
            } catch (e: Exception) {
                _uiState.value = UiState.Error("Error de descarga: ${e.message}")
            }
        }
    }

    fun playSongEntity(song: PlaylistEntity, context: Context) {
        _currentSong.value = SongDto( // opcional, si quieres reflejarlo en la UI
            id = song.songId,
            title = song.title,
            preview = song.previewUrl,
            artist = ArtistDto(song.artist),
            album = AlbumDto(song.coverUrl)
        )

        if (song.localPath != null && File(song.localPath).exists()) {
            player.play(song.localPath) // siempre offline
        } else {
            player.play(song.previewUrl) // fallback
        }

        val intent = Intent(context, MusicService::class.java)
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            context.startForegroundService(intent)
        } else {
            context.startService(intent)
        }
    }

}