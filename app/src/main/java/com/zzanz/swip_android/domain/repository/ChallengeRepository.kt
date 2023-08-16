package com.zzanz.swip_android.domain.repository

import androidx.paging.PagingData
import com.zzanz.swip_android.common.Resource
import com.zzanz.swip_android.data.remote.dto.GoalAmountByCategoryDto
import com.zzanz.swip_android.data.remote.dto.GoalAmountDto
import com.zzanz.swip_android.domain.model.ChallengeModel
import kotlinx.coroutines.flow.Flow


interface ChallengeRepository {
    suspend fun getChallengeList(): Flow<Resource<PagingData<ChallengeModel>>>

    suspend fun postGoalAmount(goalAmountDto: GoalAmountDto): Flow<Resource<Boolean>>

    suspend fun postCategoryGoalAmount(goalAmountDtoList: List<GoalAmountByCategoryDto>): Flow<Resource<Boolean>>

    suspend fun putGoalAmount(goalAmountDto: GoalAmountDto): Flow<Resource<Boolean>>

    suspend fun putCategoryGoalAmount(goalAmountDtoList: List<GoalAmountByCategoryDto>): Flow<Resource<Boolean>>


}