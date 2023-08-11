package com.example.zzanz_android.data.remote.api

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import androidx.core.app.NotificationCompat
import com.example.zzanz_android.MainActivity
import com.example.zzanz_android.R
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import timber.log.Timber


class FirebaseMessagingService : FirebaseMessagingService() {
    private val TAG = "Notification -"
    override fun onNewToken(token: String) {
        Timber.d("FirebaseMessagingService New token :: $token")
        suspend {
            sendTokenToServer(token)
        }
    }

    private fun sendTokenToServer(token: String) {
        // TODO - Server에 수정된 토큰 보낼 수 있도록 연결하기
        Timber.d("$TAG sendTokenToServer: $token")
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        Timber.e("$TAG ${remoteMessage.from}")
        Timber.e("$TAG Title :: ${remoteMessage.notification?.title}")
        Timber.e("$TAG Body :: ${remoteMessage.notification?.body}")
        Timber.e("$TAG Data :: ${remoteMessage.data}")

        remoteMessage.notification?.let {
            showNotification(it)
        }
    }

    private fun showNotification(notification: RemoteMessage.Notification) {
        val intent = Intent(this, MainActivity::class.java)
        val pIntent = PendingIntent.getActivity(
            this, 0, intent, PendingIntent.FLAG_IMMUTABLE
        )
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channelId = getString(R.string.app_name)
        val defaultSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

        val channel = NotificationChannel(
            "0", getString(R.string.app_name), NotificationManager.IMPORTANCE_HIGH,
        )
        channel.setShowBadge(true)
        channel.canShowBadge()
        channel.enableLights(true)
        channel.enableVibration(true)
        notificationManager.createNotificationChannel(channel)

        val notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle(notification.title)
            .setContentText(notification.body)
            .setSound(defaultSound)
            .setContentIntent(pIntent)
        notificationManager.notify(0, notificationBuilder.build())

    }
}