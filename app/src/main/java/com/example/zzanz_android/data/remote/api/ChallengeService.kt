package com.example.zzanz_android.data.remote.api

import com.example.zzanz_android.data.remote.dto.ChallengeDto
import com.example.zzanz_android.data.remote.dto.GoalAmountDto

interface ChallengeService {
    suspend fun getChallengeParticipate(cursor: Int?, page: Int): List<ChallengeDto>
    suspend fun postGoalAmount(goalAmountDto: GoalAmountDto): Any

    suspend fun postCategoryGoalAmount(goalAmountListDto: List<GoalAmountDto>): Any

    suspend fun putGoalAmount(goalAmountDto: GoalAmountDto): Any
}