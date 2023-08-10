package com.example.zzanz_android.domain.usecase

import com.example.zzanz_android.common.Resource
import com.example.zzanz_android.data.di.IoDispatcher
import com.example.zzanz_android.domain.model.SpendingModel
import com.example.zzanz_android.domain.repository.SpendingRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class AddSpendingUseCase @Inject constructor(
    private val spendingRepository: SpendingRepository,
    @IoDispatcher val dispatcher: CoroutineDispatcher
) {
    suspend operator fun invoke(planId: Int, spendingModel: SpendingModel): Flow<Resource<Boolean>> {
        return try {
            spendingRepository.postSpending(planId, spendingModel)
        } catch (e: Exception){
            flow { emit(Resource.Error(e)) }
        }.flowOn(dispatcher)
    }
}