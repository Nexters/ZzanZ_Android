package com.example.zzanz_android.domain.repository

import com.example.zzanz_android.common.Resource
import com.example.zzanz_android.data.remote.dto.FcmTokenDto
import com.example.zzanz_android.data.remote.dto.NotificationTimeDto
import kotlinx.coroutines.flow.Flow


interface NotificationRepository {
    suspend fun postNotificationTime(notificationTime: NotificationTimeDto): Flow<Resource<Boolean>>
    suspend fun postFcmTokenData(fcmToken: FcmTokenDto): Flow<Resource<Boolean>>

}