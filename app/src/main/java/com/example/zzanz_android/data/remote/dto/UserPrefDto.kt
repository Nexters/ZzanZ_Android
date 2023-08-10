package com.example.zzanz_android.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

data class UserPrefDto (
    val fcmToken: String? = null,
    val route: String? = null,
)