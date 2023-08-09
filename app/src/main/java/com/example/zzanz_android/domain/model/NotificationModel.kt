package com.example.zzanz_android.domain.model

data class NotificationModel (
    val fcmToken: String,
    val operatingSystem: String = "Android",
    val notificationHour: Int = 22,
    val notificationMinute: Int = 0
)