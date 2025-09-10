package com.dbtech.musicplayerapp.data.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [PlaylistEntity::class],
    version = 1,
    exportSchema = false
)

abstract class MusicDatabase : RoomDatabase(){
    abstract fun playlistDao(): PlaylistDao
}