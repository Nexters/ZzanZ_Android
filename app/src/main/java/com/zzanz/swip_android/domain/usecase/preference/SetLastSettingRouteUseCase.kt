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

class SetLastSettingRouteUseCase @Inject constructor(
    private val repository: UserPreferenceRepository,
    @IoDispatcher  val dispatcher: CoroutineDispatcher
) : BaseUseCase<Boolean, String>() {
    override suspend fun buildRequest(route: String?): Flow<Resource<Boolean>> {
        if (route == null) {
            return flow {
                emit(Resource.Error(Exception("route can not be null")))
            }.flowOn(dispatcher)
        }
        return repository.setLastNavRoute(route = route).flowOn(dispatcher)
    }
}