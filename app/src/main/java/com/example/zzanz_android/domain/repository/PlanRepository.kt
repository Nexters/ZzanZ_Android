package com.example.zzanz_android.domain.repository

import com.example.zzanz_android.common.Resource
import com.example.zzanz_android.domain.model.PlanModel
import kotlinx.coroutines.flow.Flow

interface PlanRepository {
    suspend fun getPlan(planId: Int): Flow<Resource<PlanModel>>
}