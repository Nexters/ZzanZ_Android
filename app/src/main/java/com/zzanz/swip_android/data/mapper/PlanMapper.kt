package com.zzanz.swip_android.data.mapper

import com.zzanz.swip_android.data.remote.dto.ChallengeDto
import com.zzanz.swip_android.domain.model.PlanModel

object PlanMapper: MapperToModel<ChallengeDto.Plan, PlanModel> {
    override fun ChallengeDto.Plan.toModel() = PlanModel(
        id = planId,
        category = category,
        goalAmount = categoryGoalAmount,
        remainAmount = categoryGoalAmount - categorySpendAmount
    )
}