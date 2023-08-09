package com.example.zzanz_android.domain.usecase.category

import androidx.paging.PagingData
import com.example.zzanz_android.common.Resource
import com.example.zzanz_android.data.di.IoDispatcher
import com.example.zzanz_android.domain.model.SpendingModel
import com.example.zzanz_android.domain.repository.ChallengeRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetSpendingListByCategoryUseCase @Inject constructor(
    private val planId: Int,
    private val challengeRepository: ChallengeRepository,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) {
    suspend operator fun invoke(): Flow<Resource<PagingData<SpendingModel>>> {
        return try {
            challengeRepository.getSpendingList(planId).flowOn(dispatcher)
        } catch (e: Exception) {
            flow { emit(Resource.Error(e)) }
        }
    }
}