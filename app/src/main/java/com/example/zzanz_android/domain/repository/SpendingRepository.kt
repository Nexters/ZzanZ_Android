package com.example.zzanz_android.domain.repository

import androidx.paging.PagingData
import com.example.zzanz_android.common.Resource
import com.example.zzanz_android.domain.model.SpendingModel
import kotlinx.coroutines.flow.Flow

interface SpendingRepository {
    suspend fun postSpending(planId: Int, spendingModel: SpendingModel): Flow<Resource<Boolean>>
    suspend fun getSpendingList(planId: Int): Flow<Resource<PagingData<SpendingModel>>>
}