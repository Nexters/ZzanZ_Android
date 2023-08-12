package com.example.zzanz_android.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.example.zzanz_android.common.Resource
import com.example.zzanz_android.data.mapper.SpendingMapper.toDto
import com.example.zzanz_android.data.mapper.SpendingMapper.toModel
import com.example.zzanz_android.data.remote.api.ChallengeService
import com.example.zzanz_android.data.remote.datasource.SpendingDatasource
import com.example.zzanz_android.data.remote.datasource.SpendingPagingSource
import com.example.zzanz_android.domain.model.SpendingModel
import com.example.zzanz_android.domain.repository.SpendingRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SpendingRepositoryImpl @Inject constructor(
    private val spendingDatasource: SpendingDatasource,
    private val spendingPagingSource: SpendingPagingSource
) : SpendingRepository {
    override suspend fun postSpending(
        planId: Int,
        spendingModel: SpendingModel
    ): Flow<Resource<Boolean>> {
        return try {
            val result = spendingDatasource.postSpending(planId, spendingModel.toDto())
            flow { emit(result) }
        } catch (e: Exception){
            flow { emit(Resource.Error(e)) }
        }
    }

    override suspend fun getSpendingList(planId: Int): Flow<Resource<PagingData<SpendingModel>>> {
        return Pager(
            config = PagingConfig(SPENDING_PAGE_SIZE),
            pagingSourceFactory = { spendingPagingSource }).flow.map { pagingData ->
            Resource.Success(pagingData.map { it.toModel() })
        }
    }

    companion object {
        const val SPENDING_PAGE_SIZE = 20
    }
}