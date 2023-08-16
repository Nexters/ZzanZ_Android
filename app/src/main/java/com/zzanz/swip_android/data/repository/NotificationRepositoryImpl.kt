package com.zzanz.swip_android.data.repository

import com.zzanz.swip_android.common.Resource
import com.zzanz.swip_android.data.remote.datasource.FcmTokenDataSource
import com.zzanz.swip_android.data.remote.datasource.NotificationTimeSource
import com.zzanz.swip_android.data.remote.dto.FcmTokenDto
import com.zzanz.swip_android.data.remote.dto.NotificationTimeDto
import com.zzanz.swip_android.domain.repository.NotificationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class NotificationRepositoryImpl @Inject constructor(
    private val notificationTimeSource: NotificationTimeSource,
    private val fcmTokenDataSource: FcmTokenDataSource
) : NotificationRepository {
    override suspend fun postNotificationTime(notificationTime: NotificationTimeDto): Flow<Resource<Boolean>> {
        return flow {
            val result = notificationTimeSource.load(notificationTime = notificationTime)
            emit(result)
        }
    }

    override suspend fun postFcmTokenData(fcmToken: FcmTokenDto): Flow<Resource<Boolean>> {
        return flow {
            val result = fcmTokenDataSource.load(fcmTokenDto = fcmToken)
            emit(result)
        }
    }

}