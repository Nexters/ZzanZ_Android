package com.example.zzanz_android.data.repository

import com.example.zzanz_android.common.Resource
import com.example.zzanz_android.data.mapper.SpendingMapper.toDto
import com.example.zzanz_android.data.remote.api.ChallengeService
import com.example.zzanz_android.domain.model.SpendingModel
import com.example.zzanz_android.domain.repository.SpendingRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SpendingRepositoryImpl @Inject constructor(
    private val challengeService: ChallengeService
) : SpendingRepository {
    override suspend fun postSpending(
        planId: Int,
        spendingModel: SpendingModel
    ): Flow<Resource<Boolean>> {
        return try {
            val result = challengeService.postSpending(planId, spendingModel.toDto())
            flow { emit(result) }
        } catch (e: Exception){
            flow { emit(Resource.Error(e)) }
        }
    }
}