package com.example.zzanz_android.domain.usecase.home

import androidx.paging.PagingData
import com.example.zzanz_android.common.Resource
import com.example.zzanz_android.data.di.IoDispatcher
import com.example.zzanz_android.domain.model.ChallengeModel
import com.example.zzanz_android.domain.repository.ChallengeRepository
import com.example.zzanz_android.domain.usecase.BaseUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetChallengeListUseCase @Inject constructor(
    private val challengeRepository: ChallengeRepository,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
): BaseUseCase<PagingData<ChallengeModel>, Nothing>() {
    override suspend fun buildRequest(params: Nothing?): Flow<Resource<PagingData<ChallengeModel>>> {
        return challengeRepository.getChallengeList().map {

            Resource.Success(it)
        }.flowOn(dispatcher)
    }
}