package com.example.zzanz_android.data.remote.api

import com.example.zzanz_android.common.Resource
import com.example.zzanz_android.data.remote.dto.NotificationDto

interface NotificationService {
    suspend fun postNotificationConfig(notificationDto: NotificationDto): Resource<Boolean>
}