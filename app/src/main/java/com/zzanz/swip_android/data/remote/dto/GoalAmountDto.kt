package com.zzanz.swip_android.data.remote.dto


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GoalAmountDto(
    @SerialName("goalAmount")
    val goalAmount: Int
)