package com.zzanz.swip_android.domain.repository

import com.zzanz.swip_android.common.Resource
import com.zzanz.swip_android.data.remote.dto.FcmTokenDto
import com.zzanz.swip_android.data.remote.dto.NotificationTimeDto
import kotlinx.coroutines.flow.Flow


interface NotificationRepository {
    suspend fun postNotificationTime(notificationTime: NotificationTimeDto): Flow<Resource<Boolean>>
    suspend fun postFcmTokenData(fcmToken: FcmTokenDto): Flow<Resource<Boolean>>

}