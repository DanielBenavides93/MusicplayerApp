package com.dbtech.musicplayerapp.data.repository

import com.dbtech.musicplayerapp.data.api.ChartResponse
import com.dbtech.musicplayerapp.data.api.DeezerApi

class MusicRepository (
    private val api: DeezerApi
){
    suspend fun getTopSongs(): ChartResponse{
        return api.getTopCharts()
    }
}