package com.zzanz.swip_android.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GoalAmountByCategoryDto(
    @SerialName("goalAmount")
    val goalAmount: Int,
    @SerialName("category")
    val category: String
)