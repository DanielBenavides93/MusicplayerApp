# MusicplayerApp

Android app project written in **Kotlin** using **Jetpack Compose**.

## Overview
- Compose-based entry point (`ComponentActivity` + `setContent { ... }`).
- Uses **Navigation Compose** for in-app navigation.
- Integrates with **Deezer API** (`https://api.deezer.com/`) via Retrofit.
- Uses **Room** for local persistence and **Koin** for dependency injection.

## Tech Stack
- Kotlin
- Gradle Kotlin DSL (`.kts`)
- Jetpack Compose
- Android SDK: `minSdk 24`, `targetSdk 35`, `compileSdk 35`

## Libraries
- **Retrofit** + **Gson Converter**
- **OkHttp** + **Logging Interceptor**
- **Koin** (`koin-android`, `koin-androidx-compose`)
- **Room** (`room-runtime`, `room-ktx`, `room-compiler`)
- **AndroidX Media3 (ExoPlayer)** (`media3-exoplayer`, `media3-ui`)
- **Navigation Compose**
- **Coil (Compose)** (image loading)
- Material Icons Extended

## Dependency Injection
Koin module wires:
- OkHttpClient (with BODY logging)
- Retrofit (baseUrl = Deezer)
- `MusicRepository`
- `MusicViewModel`
- `MusicPlayer`
- Room `MusicDatabase` + DAO (`playlistDao`)
- `PlaylistRepository`
- `DownloadRepositoryImpl`
