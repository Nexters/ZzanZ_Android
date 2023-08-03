package com.example.zzanz_android.data.remote.datasource

import com.example.zzanz_android.data.remote.api.ChallengeServiceImpl
import com.example.zzanz_android.data.remote.dto.GoalAmountByCategoryDto
import javax.inject.Inject

class GoalAmountByCategorySource @Inject constructor(
    private val challengeApi: ChallengeServiceImpl
) {
    suspend fun load(goalAmountDtoList: List<GoalAmountByCategoryDto>): Boolean {
        return try {
            challengeApi.postCategoryGoalAmount(goalAmountDtoList = goalAmountDtoList)
        } catch (e: Exception) {
            Error(e)
            return false
        }
    }

}