package com.dbtech.musicplayerapp.domain.repository

interface DownloadRepository {
    suspend fun downloadSong(url: String, fileName:String): String?
}