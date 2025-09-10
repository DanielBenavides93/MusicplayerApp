package com.dbtech.musicplayerapp.data.download

import android.content.Context
import com.dbtech.musicplayerapp.domain.repository.DownloadRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.net.URL

class DownloadRepositoryImpl(private val context: Context) : DownloadRepository {
    override suspend fun downloadSong(url: String, fileName: String): String? {
        return withContext(Dispatchers.IO) {
            try {
                val file = File(context.filesDir, fileName)
                URL(url).openStream().use { input ->
                    file.outputStream().use { output ->
                        input.copyTo(output)
                    }
                }
                file.absolutePath
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }
    }
}