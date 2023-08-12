package com.example.zzanz_android.data.remote.api

import com.example.zzanz_android.common.Resource
import com.example.zzanz_android.data.remote.dto.FcmTokenDto
import com.example.zzanz_android.data.remote.dto.NotificationTimeDto

interface NotificationService {
    suspend fun postNotificationTime(notificationTime: NotificationTimeDto): Resource<Boolean>
    suspend fun postFcmToken(fcmToken: FcmTokenDto): Resource<Boolean>

}