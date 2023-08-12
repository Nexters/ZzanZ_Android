package com.example.zzanz_android.domain.usecase

import com.example.zzanz_android.common.Resource
import com.example.zzanz_android.data.di.IoDispatcher
import com.example.zzanz_android.data.mapper.FcmTokenMapper.toDto
import com.example.zzanz_android.domain.model.FcmTokenModel
import com.example.zzanz_android.domain.repository.NotificationRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class PostFcmTokenUseCase @Inject constructor(
    private val repository: NotificationRepository,
    @IoDispatcher val dispatcher: CoroutineDispatcher
) : BaseUseCase<Boolean, FcmTokenModel>() {
    override suspend fun buildRequest(params: FcmTokenModel?): Flow<Resource<Boolean>> {
        if (params == null) {
            return flow {
                emit(Resource.Error(Exception("NotificationModel can not be null")))
            }.flowOn(dispatcher)
        }
        return repository
            .postFcmTokenData(fcmToken = params.toDto())
            .flowOn(dispatcher)
    }

}