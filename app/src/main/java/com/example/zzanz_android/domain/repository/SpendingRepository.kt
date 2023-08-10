package com.example.zzanz_android.domain.repository

import com.example.zzanz_android.common.Resource
import com.example.zzanz_android.data.remote.dto.request.SpendingDto
import kotlinx.coroutines.flow.Flow

interface SpendingRepository {
    suspend fun postSpending(planId: Int, spendingDto: SpendingDto): Flow<Resource<Boolean>>
}