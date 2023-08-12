package com.example.zzanz_android.data.remote.datasource

import com.example.zzanz_android.common.Resource
import com.example.zzanz_android.data.remote.api.NotificationService
import com.example.zzanz_android.data.remote.dto.FcmTokenDto
import javax.inject.Inject

class FcmTokenDataSource @Inject constructor(
    private val notificationApi: NotificationService
) {
    suspend fun load(fcmTokenDto: FcmTokenDto): Resource<Boolean> {
        return try {
            notificationApi.postFcmToken(fcmToken = fcmTokenDto)
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }

}