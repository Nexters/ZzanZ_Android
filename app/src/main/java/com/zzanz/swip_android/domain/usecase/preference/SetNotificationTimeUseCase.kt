package com.zzanz.swip_android.domain.usecase.preference

import com.zzanz.swip_android.common.Resource
import com.zzanz.swip_android.data.di.IoDispatcher
import com.zzanz.swip_android.domain.repository.UserPreferenceRepository
import com.zzanz.swip_android.domain.usecase.BaseUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class SetNotificationTimeUseCase @Inject constructor(
    private val repository: UserPreferenceRepository,
    @IoDispatcher  val dispatcher: CoroutineDispatcher
) : BaseUseCase<Boolean, List<Int>?>() {
    override suspend fun buildRequest(params: List<Int>?): Flow<Resource<Boolean>> {
        if (params.isNullOrEmpty()) {
            return flow {
                emit(Resource.Error(Exception("time can not be null")))
            }.flowOn(dispatcher)
        }
        return repository.setNotificationTime(params[0], params[1]).flowOn(dispatcher)
    }
}