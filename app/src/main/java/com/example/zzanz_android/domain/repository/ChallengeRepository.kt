package com.example.zzanz_android.domain.repository

import androidx.paging.PagingData
import com.example.zzanz_android.domain.model.ChallengeModel
import kotlinx.coroutines.flow.Flow

interface ChallengeRepository {
    suspend fun getChallengeList(): Flow<PagingData<ChallengeModel>>
}