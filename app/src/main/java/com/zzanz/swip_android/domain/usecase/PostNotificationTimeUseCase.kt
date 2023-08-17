package com.zzanz.swip_android.domain.usecase

import com.zzanz.swip_android.common.Resource
import com.zzanz.swip_android.data.di.IoDispatcher
import com.zzanz.swip_android.data.mapper.NotificationTimeMapper.toDto
import com.zzanz.swip_android.domain.model.NotificationTimeModel
import com.zzanz.swip_android.domain.repository.NotificationRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class PostNotificationTimeUseCase @Inject constructor(
    private val repository: NotificationRepository,
    @IoDispatcher val dispatcher: CoroutineDispatcher
) : BaseUseCase<Boolean, NotificationTimeModel>() {
    override suspend fun buildRequest(params: NotificationTimeModel?): Flow<Resource<Boolean>> {
        if (params == null) {
            return flow {
                emit(Resource.Error(Exception("NotificationModel can not be null")))
            }.flowOn(dispatcher)
        }
        return repository.postNotificationTime(notificationTime = params.toDto()).flowOn(dispatcher)
    }

}