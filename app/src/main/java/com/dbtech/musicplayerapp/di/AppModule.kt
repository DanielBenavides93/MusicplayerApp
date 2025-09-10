package com.dbtech.musicplayerapp.di

import androidx.room.Room
import com.dbtech.musicplayerapp.data.api.DeezerApi
import com.dbtech.musicplayerapp.data.db.MusicDatabase
import com.dbtech.musicplayerapp.data.download.DownloadRepositoryImpl
import com.dbtech.musicplayerapp.data.player.MusicPlayer
import com.dbtech.musicplayerapp.data.repository.MusicRepository
import com.dbtech.musicplayerapp.data.repository.PlaylistRepository
import com.dbtech.musicplayerapp.domain.repository.DownloadRepository
import com.dbtech.musicplayerapp.presentation.viewmodel.MusicViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val appModule = module {
    single {
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()
    }
    single {
        Retrofit.Builder()
            .baseUrl("https://api.deezer.com/") // base url Deezer
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
            .create(DeezerApi::class.java)
    }
    single { MusicRepository(get()) }

    viewModel { MusicViewModel(get(), get(), get(), get()) }

    single { MusicPlayer(get()) }

    single {
        Room.databaseBuilder(
            get(),
            MusicDatabase::class.java,
            "music_db"
        ).build()
    }

    single { get<MusicDatabase>().playlistDao() }

    single { PlaylistRepository(get()) }

    single<DownloadRepository>{DownloadRepositoryImpl(get())}

}