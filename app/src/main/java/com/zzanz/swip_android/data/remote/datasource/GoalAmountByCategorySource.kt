package com.zzanz.swip_android.data.remote.datasource

import com.zzanz.swip_android.common.Resource
import com.zzanz.swip_android.data.remote.api.ChallengeService
import com.zzanz.swip_android.data.remote.dto.GoalAmountByCategoryDto
import javax.inject.Inject

class GoalAmountByCategorySource @Inject constructor(
    private val challengeApi: ChallengeService
) {


    suspend fun postGoalAmountByCategory(goalAmountDtoList: List<GoalAmountByCategoryDto>): Resource<Boolean> {
        return try {
            challengeApi.postCategoryGoalAmount(goalAmountDtoList = goalAmountDtoList)
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }

    suspend fun putGoalAmountByCategory(goalAmountDtoList: List<GoalAmountByCategoryDto>): Resource<Boolean> {
        return try {
            challengeApi.putCategoryGoalAmount(goalAmountDtoList = goalAmountDtoList)
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }

}