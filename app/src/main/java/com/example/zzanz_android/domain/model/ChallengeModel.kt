package com.example.zzanz_android.domain.model

data class ChallengeModel(
    val id: Int,
    val startAt: String,
    val endAt: String,
    val month: Int,
    val week: Int,
    val state: ChallengeStatus,
    val goalAmount: Int,
    val remainAmount: Int,
    val planList: List<PlanModel>
)
