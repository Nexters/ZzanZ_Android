package com.example.zzanz_android.data.mapper

import com.example.zzanz_android.data.remote.dto.ChallengeDto
import com.example.zzanz_android.domain.model.PlanModel

object PlanMapper: MapperToModel<ChallengeDto.Plan, PlanModel> {
    override fun ChallengeDto.Plan.toModel() = PlanModel(
        id = planId,
        category = category,
        goalAmount = categoryGoalAmount,
        remainAmount = categoryGoalAmount - categorySpendAmount
    )
}