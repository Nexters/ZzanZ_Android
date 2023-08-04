package com.example.zzanz_android.data.remote.dto


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SpendingDto(
    @SerialName("spendingId")
    val spendingId: Int,
    @SerialName("title")
    val title: String,
    @SerialName("memo")
    val memo: String,
    @SerialName("spendAmount")
    val spendAmount: Int
)