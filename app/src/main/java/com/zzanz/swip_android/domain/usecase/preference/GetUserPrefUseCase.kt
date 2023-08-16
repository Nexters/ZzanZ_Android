package com.zzanz.swip_android.domain.usecase.preference

import com.zzanz.swip_android.common.Resource
import com.zzanz.swip_android.data.di.IoDispatcher
import com.zzanz.swip_android.domain.model.UserPref
import com.zzanz.swip_android.domain.repository.UserPreferenceRepository
import com.zzanz.swip_android.domain.usecase.BaseUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetUserPrefUseCase @Inject constructor(
    private val repository: UserPreferenceRepository,
    @IoDispatcher  val dispatcher: CoroutineDispatcher
) : BaseUseCase<UserPref?, Nothing>() {
    override suspend fun buildRequest(params: Nothing?): Flow<Resource<UserPref?>> {
        return repository.getUserPref().flowOn(dispatcher)
    }
}