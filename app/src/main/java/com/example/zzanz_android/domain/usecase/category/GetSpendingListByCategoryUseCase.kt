package com.example.zzanz_android.domain.usecase.category

import com.example.zzanz_android.common.Resource
import com.example.zzanz_android.domain.model.PlanModel
import com.example.zzanz_android.domain.model.SpendingModel
import com.example.zzanz_android.domain.repository.ChallengeRepository
import com.example.zzanz_android.domain.usecase.BaseUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSpendingListByCategoryUseCase @Inject constructor(
   private val challengeRepository: ChallengeRepository
): BaseUseCase<SpendingListByCategory, Int>() {
    override suspend fun buildRequest(params: Int?): Flow<Resource<SpendingListByCategory>> {
        //challengeRepository.getSpendingList()
    }
}

data class SpendingListByCategory(
    val plan: PlanModel,
    val spendingList: List<SpendingModel>
)