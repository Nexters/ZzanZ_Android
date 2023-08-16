package com.zzanz.swip_android.data.remote.api

import com.zzanz.swip_android.common.Resource
import com.zzanz.swip_android.data.remote.dto.FcmTokenDto
import com.zzanz.swip_android.data.remote.dto.NotificationTimeDto

interface NotificationService {
    suspend fun postNotificationTime(notificationTime: NotificationTimeDto): Resource<Boolean>
    suspend fun postFcmToken(fcmToken: FcmTokenDto): Resource<Boolean>

}