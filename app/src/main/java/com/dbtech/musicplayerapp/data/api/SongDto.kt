package com.dbtech.musicplayerapp.data.api

import com.google.gson.annotations.SerializedName

data class SongDto(
    val id: Long,
    val title: String,
    val preview: String,
    val artist: ArtistDto,
    val album: AlbumDto
)

data class ArtistDto(
    val name: String
)

data class AlbumDto(
    @SerializedName("cover") val coverUrl: String
)

data class ChartResponse(
    val tracks: TrackData
)

data class TrackData(
    val data: List<SongDto>
)