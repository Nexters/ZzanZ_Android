package com.example.zzanz_android.domain.usecase.home

import androidx.paging.PagingData
import com.example.zzanz_android.common.Resource
import com.example.zzanz_android.data.di.IoDispatcher
import com.example.zzanz_android.domain.model.ChallengeModel
import com.example.zzanz_android.domain.repository.ChallengeRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetChallengeListUseCase @Inject constructor(
    private val challengeRepository: ChallengeRepository,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) {
    suspend operator fun invoke(): Flow<Resource<PagingData<ChallengeModel>>> {
        return try {
            // TODO : gowoon - domain level error handling 있으면 처리, 지금은 없어 보임.
            challengeRepository.getChallengeList().flowOn(dispatcher)
        } catch (e: Exception) {
            flow { emit(Resource.Error(e)) }
        }
    }
}