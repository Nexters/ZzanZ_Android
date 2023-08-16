package com.zzanz.swip_android.domain.repository

import com.zzanz.swip_android.common.Resource
import com.zzanz.swip_android.domain.model.SpendingModel
import com.zzanz.swip_android.domain.usecase.category.SpendingListWithPlan
import kotlinx.coroutines.flow.Flow

interface SpendingRepository {
    suspend fun postSpending(planId: Int, spendingModel: SpendingModel): Flow<Resource<Boolean>>
    suspend fun getSpendingList(planId: Int): Flow<Resource<SpendingListWithPlan>>
}