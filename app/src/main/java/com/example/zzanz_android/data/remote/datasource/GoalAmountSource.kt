package com.example.zzanz_android.data.remote.datasource

import com.example.zzanz_android.data.remote.api.ChallengeServiceImpl
import com.example.zzanz_android.data.remote.dto.GoalAmountDto
import javax.inject.Inject

class GoalAmountSource @Inject constructor(
    private val challengeApi: ChallengeServiceImpl
) {
    suspend fun postGoalAmount(goalAmountDto: GoalAmountDto) : Boolean {
        return try {
            challengeApi.postGoalAmount(goalAmountDto = goalAmountDto)
        } catch (e: Exception) {
            Error(e)
            return false
        }
    }

    suspend fun putGoalAmount(goalAmountDto: GoalAmountDto): Boolean {
        return try {
            challengeApi.putGoalAmount(goalAmountDto = goalAmountDto)
        } catch (e: Exception) {
            Error(e)
            return false
        }
    }

}