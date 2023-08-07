package com.example.zzanz_android.data.remote.api

import com.example.zzanz_android.common.Resource
import com.example.zzanz_android.data.remote.dto.ChallengeDto
import com.example.zzanz_android.data.remote.dto.GoalAmountByCategoryDto
import com.example.zzanz_android.data.remote.dto.GoalAmountDto
import com.example.zzanz_android.data.remote.dto.SpendingListDto

interface ChallengeService {
    suspend fun getChallengeParticipate(cursor: Int?, page: Int): Resource<List<ChallengeDto>>
    
    suspend fun postGoalAmount(goalAmountDto: GoalAmountDto): Resource<Boolean>

    suspend fun postCategoryGoalAmount(goalAmountDtoList: List<GoalAmountByCategoryDto>): Resource<Boolean>

    suspend fun putGoalAmount(goalAmountDto: GoalAmountDto): Resource<Boolean>

    suspend fun getSpending(planId: Int, cursorId: Int?, size: Int): Resource<SpendingListDto>
}