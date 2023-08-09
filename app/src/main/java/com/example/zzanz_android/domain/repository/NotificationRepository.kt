package com.example.zzanz_android.domain.repository

import com.example.zzanz_android.common.Resource
import com.example.zzanz_android.data.remote.dto.NotificationDto
import kotlinx.coroutines.flow.Flow


interface NotificationRepository {
    suspend fun postNotificationConfig(notificationDto: NotificationDto): Flow<Resource<Boolean>>
}