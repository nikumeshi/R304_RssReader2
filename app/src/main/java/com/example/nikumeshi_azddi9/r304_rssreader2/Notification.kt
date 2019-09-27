package com.example.nikumeshi_azddi9.r304_rssreader2

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

private const val CHANNEL_ID = "update_channel"
private const val NOTIFICATION_ID = 1
private const val REQUEST_CODE = 1

fun createChannel(context: Context){
    val channel = NotificationChannel(CHANNEL_ID, "新着記事",
        NotificationManager.IMPORTANCE_DEFAULT).apply {
        enableLights(false)
        enableVibration(true)
        setShowBadge(true)
    }

    val manager = context.getSystemService(NotificationManager::class.java)
    manager.createNotificationChannel(channel)
}

fun notifyUpdate(context: Context){
    val intent = Intent(context, MainActivity::class.java)
    val pendingIntent = PendingIntent.getActivity(context, REQUEST_CODE, intent, PendingIntent.FLAG_ONE_SHOT)

    val notification = NotificationCompat.Builder(context, CHANNEL_ID).apply {
        setContentTitle("記事が更新されました")
        setContentText("新しい記事をチェックしましょう")
        setContentIntent(pendingIntent)
        setSmallIcon(R.drawable.ic_notification)
        setAutoCancel(true)

    }.build()

    NotificationManagerCompat.from(context).notify(NOTIFICATION_ID, notification)
}