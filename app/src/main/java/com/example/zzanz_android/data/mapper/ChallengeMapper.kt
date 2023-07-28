package com.example.zzanz_android.data.mapper

import com.example.zzanz_android.domain.model.ChallengeModel
import com.example.zzanz_android.data.remote.dto.ChallengeDto
import com.example.zzanz_android.domain.model.Category
import com.example.zzanz_android.domain.model.ChallengeStatus
import com.example.zzanz_android.domain.model.PlanModel

object ChallengeMapper: Mapper<ChallengeDto, ChallengeModel> {
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