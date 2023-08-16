package com.zzanz.swip_android.data.remote.datasource

import com.zzanz.swip_android.common.Resource
import com.zzanz.swip_android.data.remote.api.NotificationService
import com.zzanz.swip_android.data.remote.dto.NotificationTimeDto
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