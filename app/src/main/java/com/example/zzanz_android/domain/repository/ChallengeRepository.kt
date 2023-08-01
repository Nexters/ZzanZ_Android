package com.example.zzanz_android.domain.repository

import androidx.paging.PagingData
import com.example.zzanz_android.domain.model.BudgetCategoryModel
import com.example.zzanz_android.domain.model.ChallengeModel
import kotlinx.coroutines.flow.Flow

interface ChallengeRepository {
    suspend fun getChallengeList(): Flow<PagingData<ChallengeModel>>

    suspend fun postGoalAmount(goalAmount: Int): Any

    suspend fun postCategoryGoalAmount(goalAmountList: List<BudgetCategoryModel>)

    suspend fun putGoalAmount(goalAmount: Int): Any
}