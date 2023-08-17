package com.zzanz.swip_android.domain.usecase

import com.zzanz.swip_android.common.Resource
import com.zzanz.swip_android.data.di.IoDispatcher
import com.zzanz.swip_android.data.mapper.GoalAmountMapper.toDto
import com.zzanz.swip_android.domain.model.BudgetModel
import com.zzanz.swip_android.domain.repository.ChallengeRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class PutBudgetUseCase @Inject constructor(
    private val repository: ChallengeRepository,
    @IoDispatcher  val dispatcher: CoroutineDispatcher
) : BaseUseCase<Boolean, Int>() {

    override suspend fun buildRequest(params: Int?): Flow<Resource<Boolean>> {
        if (params == null) {
            return flow {
                emit(Resource.Error(Exception("budget can not be null")))
            }.flowOn(dispatcher)
        }
        val budgetModel = BudgetModel(budget = params)
        return repository.putGoalAmount(goalAmountDto = budgetModel.toDto()).flowOn(dispatcher)
    }
}