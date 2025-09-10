package com.dbtech.musicplayerapp.data.repository

import com.dbtech.musicplayerapp.data.db.PlaylistDao
import com.dbtech.musicplayerapp.data.db.PlaylistEntity
import kotlinx.coroutines.flow.Flow

class PlaylistRepository(
    private val dao: PlaylistDao
){
    suspend fun addSong(song: PlaylistEntity) = dao.addSong(song)
    suspend fun removeSong(songId: Long) = dao.removeSong(songId)
    fun getAllSongs(): Flow<List<PlaylistEntity>> = dao.getAllSongs()
}