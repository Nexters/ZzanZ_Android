package com.example.zzanz_android.data.remote.api

import com.example.zzanz_android.data.remote.dto.ChallengeDto
import com.example.zzanz_android.data.remote.dto.GoalAmountByCategoryDto
import com.example.zzanz_android.data.remote.dto.GoalAmountDto

interface ChallengeService {
    suspend fun getChallengeParticipate(cursor: Int?, page: Int): List<ChallengeDto>
    
    suspend fun postGoalAmount(goalAmountDto: GoalAmountDto): Boolean

    suspend fun postCategoryGoalAmount(goalAmountListDto: List<GoalAmountByCategoryDto>): Boolean

    suspend fun putGoalAmount(goalAmountDto: GoalAmountDto): Boolean
}