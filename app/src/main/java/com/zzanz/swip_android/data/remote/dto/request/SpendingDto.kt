package com.zzanz.swip_android.data.remote.dto.request

import kotlinx.serialization.Serializable

@Serializable
data class SpendingDto(
    val title: String,
    val memo: String,
    val spendAmount: Int
)
