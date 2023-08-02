package com.example.zzanz_android.domain.usecase

import com.example.zzanz_android.common.Resource
import com.example.zzanz_android.data.mapper.GoalAmountByCategoryMapper.toDto
import com.example.zzanz_android.domain.model.BudgetCategoryModel
import com.example.zzanz_android.domain.repository.ChallengeRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class BudgetByCategoryUseCase @Inject constructor(
    private val repository: ChallengeRepository,
    private val dispatcher: CoroutineDispatcher
) : BaseUseCase<Boolean, List<BudgetCategoryModel>>() {

    override suspend fun buildRequest(budgetList: List<BudgetCategoryModel>?): Flow<Resource<Boolean>> {
        if (budgetList.isNullOrEmpty()) {
            return flow {
                emit(Resource.Error(Exception("budgetList can not be null")))
            }.flowOn(dispatcher)
        }
        val goalAmountList = budgetList.map {
            it.toDto()
        }
        return repository
            .postCategoryGoalAmount(goalAmountDtoList = goalAmountList)
            .flowOn(dispatcher)
    }
}