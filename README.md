# MusicplayerApp

An Android music app written in **Kotlin** using **Jetpack Compose**.  
It fetches music charts from **Deezer**, plays preview tracks using **Media3 ExoPlayer**, lets you save favorites to a **Room** playlist, and supports **offline downloads** stored in internal storage.


## Technical Sheet

### UI & Navigation
- **Jetpack Compose** (Material3)
- **Navigation Compose**
- Bottom navigation routes:
  - `music` → Charts / discovery
  - `playlist` → Favorites (Room)
  - `offline` → Downloaded songs

### Architecture
- **MVVM**
  - `MusicViewModel` orchestrates data loading, playback state, favorites, and downloads
- Layered packaging:
  - `data/` (API, DB, player, repositories, downloads)
  - `domain/` (interfaces)
  - `presentation/` (screens, navigation, UI state)
  - `service/` (foreground playback service + media session)

### Dependency Injection
- **Koin**
  - Started in `MainApplication`
  - Provides:
    - OkHttpClient (BODY logging)
    - Retrofit (base URL Deezer)
    - Repositories (music, playlist, download)
    - Room DB + DAO
    - Player (`MusicPlayer`)
    - ViewModel (`MusicViewModel`)

### Async / Reactive
- **Kotlin Coroutines**
- **Flow / StateFlow**
  - Playlist is streamed from Room via `Flow<List<PlaylistEntity>>`
  - Player state is exposed via Flow from `MusicPlayer`


## Features

### Deezer Charts
- Fetches charts via Deezer:
  - Base URL: `https://api.deezer.com/`
  - Endpoint used: `GET /chart`
- Displays song list and metadata (title/artist/album cover)

### Playback (Media3 ExoPlayer)
- Plays track previews using **Media3 ExoPlayer**
- Persistent player UI via `PlayerBar`
- Controls:
  - Play / Pause / Stop
- Tracks current song and playback state

### Favorites Playlist (Room)
- Save a song to favorites (“Fav”)
- Remove a song from favorites (“Quitar”)
- Playlist persists locally in Room:
  - Entity: `PlaylistEntity`
  - Table: `playlist`
- Playlist list is reactive via Flow (updates UI automatically)

### Offline Downloads
- Downloads preview audio into internal storage (`filesDir`)
- File naming: `<songId>.mp3`
- Offline screen lists downloaded songs and allows playback
- Playback logic:
  - If local file exists → play local file
  - Else → play remote preview URL

### Background Playback (Foreground Service)
- Foreground service: `MusicService`
- Uses Media3 `MediaSession`
- Provides a media notification with Play/Pause actions


## API
- **Base URL:** `https://api.deezer.com/`
- **Endpoint:** `GET /chart`


## Permissions (AndroidManifest)
- `INTERNET`
- `FOREGROUND_SERVICE`
- `FOREGROUND_SERVICE_MEDIA_PLAYBACK`
