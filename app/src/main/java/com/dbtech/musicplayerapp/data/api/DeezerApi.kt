package com.dbtech.musicplayerapp.data.api

import retrofit2.http.GET

interface DeezerApi{
    @GET("chart")
    suspend fun getTopCharts(): ChartResponse
}