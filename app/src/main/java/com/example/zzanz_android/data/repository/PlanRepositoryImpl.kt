package com.example.zzanz_android.data.repository

import com.example.zzanz_android.common.Resource
import com.example.zzanz_android.data.mapper.PlanMapper.toModel
import com.example.zzanz_android.data.remote.datasource.SpendingPagingSource
import com.example.zzanz_android.domain.model.PlanModel
import com.example.zzanz_android.domain.repository.PlanRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PlanRepositoryImpl @Inject constructor(
    private val spendingPagingSource: SpendingPagingSource
): PlanRepository {
    override suspend fun getPlan(planId: Int): Flow<Resource<PlanModel>> {
        return try {
            spendingPagingSource.planInfo.map {
                Resource.Success(it.toModel())
            }
        } catch (e: Exception){
            flow { emit(Resource.Error(e)) }
        }
    }
}