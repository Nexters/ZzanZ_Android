package com.example.zzanz_android.domain.model

data class FcmTokenModel(
    val fcmToken: String, val operatingSystem: String = "ANDROID"
)