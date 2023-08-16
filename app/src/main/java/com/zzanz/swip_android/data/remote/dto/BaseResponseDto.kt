package com.zzanz.swip_android.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BaseResponseDto(
    @SerialName("message")
    val message: String
)