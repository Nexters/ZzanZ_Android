package com.example.zzanz_android.domain.repository

import androidx.paging.PagingData
import com.example.zzanz_android.common.Resource
import com.example.zzanz_android.data.remote.dto.GoalAmountByCategoryDto
import com.example.zzanz_android.data.remote.dto.GoalAmountDto
import com.example.zzanz_android.domain.model.ChallengeModel
import kotlinx.coroutines.flow.Flow

interface ChallengeRepository {
    suspend fun getChallengeList(): Flow<PagingData<ChallengeModel>>

    suspend fun postGoalAmount(goalAmountDto: GoalAmountDto): Flow<Resource<Boolean>>

    suspend fun postCategoryGoalAmount(goalAmountDtoList: List<GoalAmountByCategoryDto>): Flow<Resource<Boolean>>

    suspend fun putGoalAmount(goalAmountDto: GoalAmountDto): Flow<Resource<Boolean>>
}