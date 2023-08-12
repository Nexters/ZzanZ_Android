package com.example.zzanz_android.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FcmTokenDto (
    @SerialName("fcmToken")
    val fcmToken: String,
    @SerialName("operatingSystem")
    val operatingSystem: String
)