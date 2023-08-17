package com.zzanz.swip_android.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.zzanz.swip_android.common.Resource
import com.zzanz.swip_android.data.mapper.ChallengeMapper.toModel
import com.zzanz.swip_android.data.remote.datasource.ChallengePagingSource
import com.zzanz.swip_android.data.remote.datasource.GoalAmountByCategorySource
import com.zzanz.swip_android.data.remote.datasource.GoalAmountSource
import com.zzanz.swip_android.data.remote.dto.GoalAmountByCategoryDto
import com.zzanz.swip_android.data.remote.dto.GoalAmountDto
import com.zzanz.swip_android.domain.model.ChallengeModel
import com.zzanz.swip_android.domain.repository.ChallengeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject


class ChallengeRepositoryImpl @Inject constructor(
    private val challengePagingSource: ChallengePagingSource,
    private val goalAmountSource: GoalAmountSource,
    private val goalAmountByCategorySource: GoalAmountByCategorySource,
) : ChallengeRepository {
    override suspend fun getChallengeList(): Flow<Resource<PagingData<ChallengeModel>>> {
        return Pager(config = PagingConfig(pageSize = CHALLENGE_PAGE_SIZE), pagingSourceFactory = {
            challengePagingSource
        }).flow.map { pagingData ->
            Resource.Success(pagingData.map {
                it.toModel()
            })
        }
    }

    override suspend fun postGoalAmount(goalAmountDto: GoalAmountDto): Flow<Resource<Boolean>> {
        return flow {
            val result = goalAmountSource.postGoalAmount(goalAmountDto = goalAmountDto)
            emit(result)
        }
    }

    override suspend fun postCategoryGoalAmount(goalAmountDtoList: List<GoalAmountByCategoryDto>): Flow<Resource<Boolean>> {
        return flow {
            val result =
                goalAmountByCategorySource.postGoalAmountByCategory(goalAmountDtoList = goalAmountDtoList)
            emit(result)
        }
    }

    override suspend fun putGoalAmount(goalAmountDto: GoalAmountDto): Flow<Resource<Boolean>> {
        return flow {
            val result = goalAmountSource.putGoalAmount(goalAmountDto = goalAmountDto)
            emit(result)
        }
    }

    override suspend fun putCategoryGoalAmount(goalAmountDtoList: List<GoalAmountByCategoryDto>): Flow<Resource<Boolean>> {
        return flow {
            val result =
                goalAmountByCategorySource.putGoalAmountByCategory(goalAmountDtoList = goalAmountDtoList)
            emit(result)
        }
    }

    companion object {
        const val CHALLENGE_PAGE_SIZE = 5
    }
}