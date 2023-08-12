package com.example.zzanz_android.data.remote.api

import com.example.zzanz_android.common.Resource
import com.example.zzanz_android.data.remote.dto.ChallengeDto
import com.example.zzanz_android.data.remote.dto.GoalAmountByCategoryDto
import com.example.zzanz_android.data.remote.dto.GoalAmountDto
import com.example.zzanz_android.data.remote.dto.SpendingListDto
import com.example.zzanz_android.data.remote.dto.request.SpendingDto as SpendingRequestDto

interface ChallengeService {
    suspend fun getChallengeParticipate(cursor: Int?, page: Int): Resource<List<ChallengeDto>>
    
    suspend fun postGoalAmount(goalAmountDto: GoalAmountDto): Resource<Boolean>

    suspend fun postCategoryGoalAmount(goalAmountDtoList: List<GoalAmountByCategoryDto>): Resource<Boolean>
    suspend fun putCategoryGoalAmount(goalAmountDtoList: List<GoalAmountByCategoryDto>): Resource<Boolean>


    suspend fun putGoalAmount(goalAmountDto: GoalAmountDto): Resource<Boolean>

    suspend fun getSpending(planId: Int, cursorId: Int?, size: Int): Resource<SpendingListDto>

    suspend fun postSpending(planId: Int, spendingDto: SpendingRequestDto): Resource<Boolean>
}