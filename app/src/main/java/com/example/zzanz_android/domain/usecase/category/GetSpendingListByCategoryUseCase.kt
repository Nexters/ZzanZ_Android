package com.example.zzanz_android.domain.usecase.category

import androidx.paging.PagingData
import com.example.zzanz_android.common.Resource
import com.example.zzanz_android.data.di.IoDispatcher
import com.example.zzanz_android.domain.model.PlanModel
import com.example.zzanz_android.domain.model.SpendingModel
import com.example.zzanz_android.domain.repository.ChallengeRepository
import com.example.zzanz_android.domain.repository.SpendingRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetSpendingListByCategoryUseCase @Inject constructor(
    private val spendingRepository: SpendingRepository,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) {
    suspend operator fun invoke(planId: Int): Flow<Resource<SpendingListWithPlan>> {
        return try {
            spendingRepository.getSpendingList(planId).flowOn(dispatcher)
        } catch (e: Exception) {
            flow { emit(Resource.Error(e)) }
        }
    }
}

data class SpendingListWithPlan(
    val plan: Flow<PlanModel>,
    val spendingList: Flow<PagingData<SpendingModel>>
)