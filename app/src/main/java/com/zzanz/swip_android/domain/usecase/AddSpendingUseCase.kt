package com.zzanz.swip_android.domain.usecase

import com.zzanz.swip_android.common.Resource
import com.zzanz.swip_android.data.di.IoDispatcher
import com.zzanz.swip_android.domain.model.SpendingModel
import com.zzanz.swip_android.domain.repository.SpendingRepository
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
            if(spendingModel.amount <= 0 || spendingModel.title.trim().isEmpty()){
                throw Exception("Invalid Value")
            }
            spendingRepository.postSpending(planId, spendingModel)
        } catch (e: Exception){
            flow { emit(Resource.Error(e)) }
        }.flowOn(dispatcher)
    }
}