package com.zzanz.swip_android.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.map
import com.zzanz.swip_android.common.Resource
import com.zzanz.swip_android.data.mapper.PlanMapper.toModel
import com.zzanz.swip_android.data.mapper.SpendingMapper.toDto
import com.zzanz.swip_android.data.mapper.SpendingMapper.toModel
import com.zzanz.swip_android.data.remote.api.ChallengeService
import com.zzanz.swip_android.data.remote.datasource.SpendingDatasource
import com.zzanz.swip_android.data.remote.datasource.SpendingPagingSource
import com.zzanz.swip_android.domain.model.SpendingModel
import com.zzanz.swip_android.domain.repository.SpendingRepository
import com.zzanz.swip_android.domain.usecase.category.SpendingListWithPlan
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SpendingRepositoryImpl @Inject constructor(
    private val challengeService: ChallengeService,
    private val spendingDatasource: SpendingDatasource
) : SpendingRepository {
    override suspend fun postSpending(
        planId: Int,
        spendingModel: SpendingModel
    ): Flow<Resource<Boolean>> {
        return try {
            val result = spendingDatasource.postSpending(planId, spendingModel.toDto())
            flow { emit(result) }
        } catch (e: Exception) {
            flow { emit(Resource.Error(e)) }
        }
    }

    override suspend fun getSpendingList(planId: Int): Flow<Resource<SpendingListWithPlan>> {
        val pagingSource = SpendingPagingSource(challengeService, planId)
        try {
            return flow {
                emit(
                    Resource.Success(
                        SpendingListWithPlan(
                            pagingSource.planInfo.map { it.toModel() },
                            Pager(
                                config = PagingConfig(SPENDING_PAGE_SIZE),
                                pagingSourceFactory = { pagingSource }
                            ).flow.map { pagingData ->
                                pagingData.map { dto ->
                                    dto.toModel()
                                }
                            }
                        )
                    )
                )
            }
        } catch (e: Exception) {
            return flow { emit(Resource.Error(e)) }
        }
    }

    companion object {
        const val SPENDING_PAGE_SIZE = 20
    }
}