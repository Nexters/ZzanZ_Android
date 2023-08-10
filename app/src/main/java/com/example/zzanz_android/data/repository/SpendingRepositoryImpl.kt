package com.example.zzanz_android.data.repository

import com.example.zzanz_android.common.Resource
import com.example.zzanz_android.data.remote.api.ChallengeService
import com.example.zzanz_android.data.remote.dto.request.SpendingDto
import com.example.zzanz_android.domain.repository.SpendingRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SpendingRepositoryImpl @Inject constructor(
    private val challengeService: ChallengeService
) : SpendingRepository {
    override suspend fun postSpending(
        planId: Int,
        spendingDto: SpendingDto
    ): Flow<Resource<Boolean>> {
        return flow {
            emit(
                try {
                    challengeService.postSpending(planId, spendingDto)
                } catch (e: Exception) {
                    Resource.Error(e)
                }
            )
        }
    }
}