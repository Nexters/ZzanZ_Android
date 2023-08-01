package com.example.zzanz_android.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.example.zzanz_android.data.mapper.ChallengeMapper.toModel
import com.example.zzanz_android.data.remote.datasource.CategoryGoalAmountSource
import com.example.zzanz_android.data.remote.datasource.ChallengePagingSource
import com.example.zzanz_android.data.remote.datasource.GoalAmountSource
import com.example.zzanz_android.data.remote.dto.GoalAmountDto
import com.example.zzanz_android.domain.model.BudgetCategoryModel
import com.example.zzanz_android.domain.model.ChallengeModel
import com.example.zzanz_android.domain.repository.ChallengeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ChallengeRepositoryImpl @Inject constructor(
    private val challengePagingSource: ChallengePagingSource,
    private val goalAmountSource: GoalAmountSource,
    private val categoryGoalAmountSource: CategoryGoalAmountSource
) : ChallengeRepository {
    override suspend fun getChallengeList(): Flow<PagingData<ChallengeModel>> {
        return Pager(config = PagingConfig(pageSize = PAGE_SIZE), pagingSourceFactory = {
            challengePagingSource
        }).flow.map { pagingDate ->
            pagingDate.map { challengeDto -> challengeDto.toModel() }
        }
    }

    override suspend fun postGoalAmount(goalAmount: Int): Any {
        val goalAmountDto = GoalAmountDto(goalAmount = goalAmount)
        return goalAmountSource.postGoalAmount(goalAmountDto = goalAmountDto)
    }

    override suspend fun postCategoryGoalAmount(goalAmountList: List<BudgetCategoryModel>) {
        var goalAmountDtoList = mutableListOf<GoalAmountDto>()
        goalAmountList.map {
            goalAmountDtoList.add(GoalAmountDto(goalAmount = it.budget, category = it.categoryName))
        }
        return categoryGoalAmountSource.load(goalAmountListDto = goalAmountDtoList)
    }

    override suspend fun putGoalAmount(goalAmount: Int): Any {
        val goalAmountDto = GoalAmountDto(goalAmount = goalAmount)
        return goalAmountSource.putGoalAmount(goalAmountDto = goalAmountDto)
    }

    companion object {
        const val PAGE_SIZE = 5
    }
}