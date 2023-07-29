package com.example.zzanz_android.data.remote.dto


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ChallengeDto(
    @SerialName("challengeId")
    val challengeId: Int,
    @SerialName("startAt")
    val startAt: String,
    @SerialName("endAt")
    val endAt: String,
    @SerialName("month")
    val month: Int,
    @SerialName("week")
    val week: Int,
    @SerialName("state")
    val state: String,
    @SerialName("participationId")
    val participationId: Double,
    @SerialName("goalAmount")
    val goalAmount: Int,
    @SerialName("currentAmount")
    val currentAmount: Int,
    @SerialName("planList")
    val planList: List<Plan>
) {
    @Serializable
    data class Plan(
        @SerialName("planId")
        val planId: Int,
        @SerialName("category")
        val category: String,
        @SerialName("categoryGoalAmount")
        val categoryGoalAmount: Int,
        @SerialName("categorySpendAmount")
        val categorySpendAmount: Int
    )
}
