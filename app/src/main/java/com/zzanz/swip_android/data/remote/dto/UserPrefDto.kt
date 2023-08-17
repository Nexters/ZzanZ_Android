package com.zzanz.swip_android.data.remote.dto

data class UserPrefDto (
    val fcmToken: String? = null,
    val route: String? = null,
    val notificationHour: String? = null,
    val notificationMinute: String? = null,
)