package com.zzanz.swip_android.data.remote.datasource

import com.zzanz.swip_android.common.Resource
import com.zzanz.swip_android.data.remote.api.ChallengeServiceImpl
import com.zzanz.swip_android.data.remote.dto.GoalAmountDto
import javax.inject.Inject

class GoalAmountSource @Inject constructor(
    private val challengeApi: ChallengeServiceImpl
) {
    suspend fun postGoalAmount(goalAmountDto: GoalAmountDto) : Resource<Boolean> {
        return try {
            challengeApi.postGoalAmount(goalAmountDto = goalAmountDto)
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }

    suspend fun putGoalAmount(goalAmountDto: GoalAmountDto): Resource<Boolean> {
        return try {
            challengeApi.putGoalAmount(goalAmountDto = goalAmountDto)
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }

}