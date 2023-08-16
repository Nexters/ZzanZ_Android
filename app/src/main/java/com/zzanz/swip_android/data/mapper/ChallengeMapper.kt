package com.zzanz.swip_android.data.mapper

import com.zzanz.swip_android.domain.model.ChallengeModel
import com.zzanz.swip_android.data.remote.dto.ChallengeDto
import com.zzanz.swip_android.domain.model.Category
import com.zzanz.swip_android.domain.model.ChallengeStatus
import com.zzanz.swip_android.domain.model.PlanModel

object ChallengeMapper: MapperToModel<ChallengeDto, ChallengeModel> {

    override fun ChallengeDto.toModel() = ChallengeModel(
        id = challengeId,
        startAt = startAt,
        endAt = endAt,
        month = month,
        week = week,
        state = ChallengeStatus.valueOf(state),
        goalAmount = goalAmount,
        remainAmount = goalAmount-currentAmount,
        planList = planList.map {
            PlanModel(
                id = it.planId,
                category = Category.valueOf(it.category).name,
                goalAmount = it.categoryGoalAmount,
                remainAmount = it.categoryGoalAmount - it.categorySpendAmount
            )
        }
    )
}