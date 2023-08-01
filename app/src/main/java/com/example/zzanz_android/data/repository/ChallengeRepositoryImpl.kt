package com.example.zzanz_android.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.example.zzanz_android.common.Resource
import com.example.zzanz_android.data.mapper.ChallengeMapper.toModel
import com.example.zzanz_android.data.remote.datasource.ChallengePagingSource
import com.example.zzanz_android.data.remote.datasource.GoalAmountByCategorySource
import com.example.zzanz_android.data.remote.datasource.GoalAmountSource
import com.example.zzanz_android.data.remote.dto.GoalAmountByCategoryDto
import com.example.zzanz_android.data.remote.dto.GoalAmountDto
import com.example.zzanz_android.domain.model.ChallengeModel
import com.example.zzanz_android.domain.repository.ChallengeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ChallengeRepositoryImpl @Inject constructor(
    private val challengePagingSource: ChallengePagingSource,
    private val goalAmountSource: GoalAmountSource,
    private val goalAmountByCategorySource: GoalAmountByCategorySource
) : ChallengeRepository {
    override suspend fun getChallengeList(): Flow<PagingData<ChallengeModel>> {
        return Pager(config = PagingConfig(pageSize = PAGE_SIZE), pagingSourceFactory = {
            challengePagingSource
        }).flow.map { pagingDate ->
            pagingDate.map { challengeDto -> challengeDto.toModel() }
        }
    }

    override suspend fun postGoalAmount(goalAmountDto: GoalAmountDto): Flow<Resource<Boolean>> {
        return flow {
            val result = goalAmountSource.postGoalAmount(goalAmountDto = goalAmountDto)
            emit(Resource.Success<Boolean>(data = result))
        }
    }

    override suspend fun postCategoryGoalAmount(goalAmountDtoList: List<GoalAmountByCategoryDto>): Flow<Resource<Boolean>> {
        return flow {
            val result = goalAmountByCategorySource.load(goalAmountListDto = goalAmountDtoList)
            emit(Resource.Success<Boolean>(data = result))
        }

    }

    override suspend fun putGoalAmount(goalAmountDto: GoalAmountDto): Flow<Resource<Boolean>> {
        return flow {
            val result = goalAmountSource.putGoalAmount(goalAmountDto = goalAmountDto)
            emit(Resource.Success<Boolean>(data = result))
        }
    }

    companion object {
        const val PAGE_SIZE = 5
    }
}