package com.example.zzanz_android.data.remote.datasource

import com.example.zzanz_android.common.Resource
import com.example.zzanz_android.data.remote.api.NotificationService
import com.example.zzanz_android.data.remote.dto.NotificationDto
import javax.inject.Inject

class NotificationSource @Inject constructor(
    private val notificationApi: NotificationService
) {
    suspend fun load(notificationDto: NotificationDto): Resource<Boolean> {
        return try {
            notificationApi.postNotificationConfig(notificationDto = notificationDto)
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }

}