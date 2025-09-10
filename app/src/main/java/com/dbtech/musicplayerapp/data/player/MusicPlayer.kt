package com.dbtech.musicplayerapp.data.player

import android.content.Context
import android.net.Uri
import androidx.media3.common.MediaItem
import androidx.media3.common.PlaybackException
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MusicPlayer(context: Context) {
    private val exoPlayer: ExoPlayer = ExoPlayer.Builder(context).build()

    private val _playerState = MutableStateFlow(PlayerState())
    val playerState: StateFlow<PlayerState> = _playerState

    fun play(url: String) {
        val mediaItem = MediaItem.fromUri(Uri.parse(url))
        exoPlayer.setMediaItem(mediaItem)
        exoPlayer.prepare()
        exoPlayer.play()

        _playerState.value = _playerState.value.copy(
            isPlaying = true,
            duration = exoPlayer.duration
        )
    }

    fun pause() {
        exoPlayer.pause()
        _playerState.value = _playerState.value.copy(isPlaying = false)
    }

    fun stop() {
        exoPlayer.stop()
        _playerState.value = PlayerState()
    }

    fun seekTo(position: Long) {
        exoPlayer.seekTo(position)
        _playerState.value = _playerState.value.copy(currentPosition = position)
    }

    fun release() {
        exoPlayer.release()
    }

    private fun addListener() {
        exoPlayer.addListener(object : Player.Listener {
            override fun onPlaybackStateChanged(playbackState: Int) {
                val isPlaying = exoPlayer.isPlaying
                val duration = if (exoPlayer.duration > 0) exoPlayer.duration else 0L
                val position = exoPlayer.currentPosition

                _playerState.value = _playerState.value.copy(
                    isPlaying = isPlaying,
                    duration = duration,
                    currentPosition = position
                )
            }

            override fun onIsPlayingChanged(isPlaying: Boolean) {
                _playerState.value = _playerState.value.copy(isPlaying = isPlaying)
            }

            override fun onPlayerError(error: PlaybackException) {
                // Podrías lanzar un estado de error aquí
                _playerState.value = PlayerState(
                    isPlaying = false,
                    currentPosition = 0L,
                    duration = 0L
                )
            }
        })
    }

}