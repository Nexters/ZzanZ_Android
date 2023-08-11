package com.example.zzanz_android.data.remote.api

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

        /**
         * 앱이 포그라운드에 있는 상태에서 굳이 노티를 보여줄 필요가 있을까? 해서 일단은 노티 호출 함수 제거
         */
//        remoteMessage.notification?.let {
//            showNotification(it)
//        }
    }

    private fun showNotification(notification: RemoteMessage.Notification) {
        val intent = Intent(this, MainActivity::class.java)
        val pIntent = PendingIntent.getActivity(
            this, 0, intent, PendingIntent.FLAG_IMMUTABLE
        )
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val defaultSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val channelId = getString(R.string.app_name)
        val notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setPriority(NotificationCompat.PRIORITY_HIGH).setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle(notification.title).setContentText(notification.body)
            .setSound(defaultSound).setContentIntent(pIntent)

        notificationManager.notify(0, notificationBuilder.build())

    }
}