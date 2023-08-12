package com.example.zzanz_android.data.remote.datasource

import com.example.zzanz_android.common.Resource
import com.example.zzanz_android.data.remote.api.ChallengeService
import com.example.zzanz_android.data.remote.dto.request.SpendingDto
import javax.inject.Inject

class SpendingDatasource @Inject constructor(
    private val challengeService: ChallengeService
){
    suspend fun postSpending(planId: Int, spendingDto: SpendingDto): Resource<Boolean>{
        return try {
            challengeService.postSpending(planId, spendingDto)
        } catch (e: Exception){
            Resource.Error(e)
        }
    }
}