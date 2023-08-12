package com.example.zzanz_android.domain.usecase

import com.example.zzanz_android.common.Resource
import com.example.zzanz_android.data.di.IoDispatcher
import com.example.zzanz_android.data.mapper.GoalAmountByCategoryMapper.toDto
import com.example.zzanz_android.domain.model.BudgetCategoryModel
import com.example.zzanz_android.domain.repository.ChallengeRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class PostBudgetByCategoryUseCase @Inject constructor(
    private val repository: ChallengeRepository,
    @IoDispatcher  val dispatcher: CoroutineDispatcher
) : BaseUseCase<Boolean, List<BudgetCategoryModel>>() {

    override suspend fun buildRequest(params: List<BudgetCategoryModel>?): Flow<Resource<Boolean>> {
        if (params.isNullOrEmpty()) {
            return flow {
                emit(Resource.Error(Exception("budgetList can not be null")))
            }.flowOn(dispatcher)
        }
        val goalAmountList = params.map {
            it.toDto()
        }
        return repository
            .postCategoryGoalAmount(goalAmountDtoList = goalAmountList)
            .flowOn(dispatcher)
    }
}