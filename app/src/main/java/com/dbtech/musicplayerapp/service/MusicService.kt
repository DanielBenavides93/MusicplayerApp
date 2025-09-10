package com.dbtech.musicplayerapp.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import androidx.media.session.MediaButtonReceiver
import com.dbtech.musicplayerapp.R

class MusicService : Service(){
    companion object{
        const val CHANNEL_ID = "music_player_channel"
        const val NOTIFICATION_ID = 1
    }

    private lateinit var mediaSessionManager: MediaSessionManager

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
        mediaSessionManager = MediaSessionManager(this)
    }
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        MediaButtonReceiver.handleIntent(mediaSessionManager.mediaSession, intent)
        val notification: Notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Reproduciendo música")
            .setContentText("Tu canción favorita")
            .setSmallIcon(R.drawable.ic_music_note) // crea este ícono en res/drawable
            .setContentIntent(mediaSessionManager.mediaSession.controller.sessionActivity)
            .setStyle(
                androidx.media.app.NotificationCompat.MediaStyle()
                    .setMediaSession(mediaSessionManager.mediaSession.sessionToken)
                    .setShowActionsInCompactView(0, 1) // play y pause
            )
            .addAction(
                R.drawable.ic_play,
                "Play",
                MediaButtonReceiver.buildMediaButtonPendingIntent(
                    this,
                    android.support.v4.media.session.PlaybackStateCompat.ACTION_PLAY
                )
            )
            .addAction(
                R.drawable.ic_pause,
                "Pause",
                MediaButtonReceiver.buildMediaButtonPendingIntent(
                    this,
                    android.support.v4.media.session.PlaybackStateCompat.ACTION_PAUSE
                )
            )
            .addAction(
                R.drawable.ic_stop,
                "Stop",
                MediaButtonReceiver.buildMediaButtonPendingIntent(
                    this,
                    android.support.v4.media.session.PlaybackStateCompat.ACTION_STOP
                )
            )
            .build()

        startForeground(NOTIFICATION_ID, notification)

        return START_NOT_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaSessionManager.release()
    }

    override fun onBind(intent: Intent?): IBinder? = null

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Music Player",
                NotificationManager.IMPORTANCE_LOW
            )
            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(channel)
        }
    }
}