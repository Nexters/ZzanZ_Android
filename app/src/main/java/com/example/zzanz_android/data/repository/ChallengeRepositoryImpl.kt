package com.example.zzanz_android.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.example.zzanz_android.data.mapper.ChallengeMapper.toModel
import com.example.zzanz_android.data.remote.datasource.ChallengePagingSource
import com.example.zzanz_android.domain.model.ChallengeModel
import com.example.zzanz_android.domain.repository.ChallengeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ChallengeRepositoryImpl @Inject constructor(
    private val challengePagingSource: ChallengePagingSource
) : ChallengeRepository {
    override suspend fun getChallengeList(): Flow<PagingData<ChallengeModel>> {
        return Pager(config = PagingConfig(pageSize = PAGE_SIZE), pagingSourceFactory = {
            challengePagingSource
        }).flow.map { pagingDate ->
            pagingDate.map { challengeDto -> challengeDto.toModel() }
        }
    }

    companion object {
        const val PAGE_SIZE = 5
    }
}