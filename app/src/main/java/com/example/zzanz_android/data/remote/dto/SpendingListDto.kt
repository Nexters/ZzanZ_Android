package com.example.zzanz_android.data.remote.dto


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SpendingListDto(
    @SerialName("goalAmount")
    val goalAmount: Int,
    @SerialName("spendAmount")
    val spendAmount: Int,
    @SerialName("hasNext")
    val hasNext: Boolean,
    @SerialName("spendingList")
    val spendingList: List<SpendingDto>
)