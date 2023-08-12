package com.example.zzanz_android.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NotificationTimeDto(
    @SerialName("notificationHour")
    val notificationHour: Int,
    @SerialName("notificationMinute")
    val notificationMinute: Int
)