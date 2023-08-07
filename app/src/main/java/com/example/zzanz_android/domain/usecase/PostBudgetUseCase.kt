package com.example.zzanz_android.domain.usecase

import com.example.zzanz_android.common.Resource
import com.example.zzanz_android.data.di.IoDispatcher
import com.example.zzanz_android.data.mapper.GoalAmountMapper.toDto
import com.example.zzanz_android.domain.model.BudgetModel
import com.example.zzanz_android.domain.repository.ChallengeRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class PostBudgetUseCase @Inject constructor(
    private val repository: ChallengeRepository,
    @IoDispatcher  val dispatcher: CoroutineDispatcher
) : BaseUseCase<Boolean, Int>() {

    override suspend fun buildRequest(budget: Int?): Flow<Resource<Boolean>> {
        if (budget == null) {
            return flow {
                emit(Resource.Error(Exception("budget can not be null")))
            }.flowOn(dispatcher)
        }
        val budgetModel = BudgetModel(budget = budget)
        return repository.postGoalAmount(goalAmountDto = budgetModel.toDto()).flowOn(dispatcher)
    }
}