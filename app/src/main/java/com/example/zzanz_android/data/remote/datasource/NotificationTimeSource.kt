package com.example.zzanz_android.data.remote.datasource

import com.example.zzanz_android.common.Resource
import com.example.zzanz_android.data.remote.api.NotificationService
import com.example.zzanz_android.data.remote.dto.NotificationTimeDto
import javax.inject.Inject

class NotificationTimeSource @Inject constructor(
    private val notificationApi: NotificationService
) {
    suspend fun load(notificationTime: NotificationTimeDto): Resource<Boolean> {
        return try {
            notificationApi.postNotificationTime(notificationTime = notificationTime)
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }

}