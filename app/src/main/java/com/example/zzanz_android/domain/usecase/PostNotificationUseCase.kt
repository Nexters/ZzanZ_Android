package com.example.zzanz_android.domain.usecase

import com.example.zzanz_android.common.Resource
import com.example.zzanz_android.data.di.IoDispatcher
import com.example.zzanz_android.data.mapper.NotificationMapper.toDto
import com.example.zzanz_android.domain.model.NotificationModel
import com.example.zzanz_android.domain.repository.NotificationRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class PostNotificationUseCase @Inject constructor(
    private val repository: NotificationRepository,
    @IoDispatcher val dispatcher: CoroutineDispatcher
) : BaseUseCase<Boolean, NotificationModel>() {
    override suspend fun buildRequest(params: NotificationModel?): Flow<Resource<Boolean>> {
        if (params == null) {
            return flow {
                emit(Resource.Error(Exception("NotificationModel can not be null")))
            }.flowOn(dispatcher)
        }
        return repository
            .postNotificationConfig(notificationDto = params.toDto())
            .flowOn(dispatcher)
    }

}