package com.dbtech.musicplayerapp.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface PlaylistDao{

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addSong(song: PlaylistEntity)

    @Query("DELETE FROM playlist WHERE songId = :songId")
    suspend fun removeSong(songId: Long)

    @Query("SELECT * FROM playlist")
    fun getAllSongs(): Flow<List<PlaylistEntity>>
}