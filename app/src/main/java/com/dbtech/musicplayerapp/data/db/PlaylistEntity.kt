package com.dbtech.musicplayerapp.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "playlist")
data class PlaylistEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val songId: Long,
    val title: String,
    val artist: String,
    val previewUrl: String,
    val coverUrl: String,
    val localPath: String? = null
)