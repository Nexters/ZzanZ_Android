package com.example.zzanz_android.data.remote.datasource

import com.example.zzanz_android.data.remote.api.ChallengeServiceImpl
import com.example.zzanz_android.data.remote.dto.GoalAmountDto
import javax.inject.Inject

class CategoryGoalAmountSource @Inject constructor(
    private val challengeApi: ChallengeServiceImpl
) {
    suspend fun load(goalAmountListDto: List<GoalAmountDto>) {
        try {
            challengeApi.postCategoryGoalAmount(goalAmountListDto = goalAmountListDto)
        } catch (e: Exception) {
            Error(e)
        }
    }

}