package com.zzanz.swip_android.data.remote.dto


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SpendingListDto(
    @SerialName("category")
    val category: String,
    @SerialName("goalAmount")
    val goalAmount: Int,
    @SerialName("spendAmount")
    val spendAmount: Int,
    @SerialName("hasNext")
    val hasNext: Boolean,
    @SerialName("spendingList")
    val spendingList: List<SpendingDto>
)