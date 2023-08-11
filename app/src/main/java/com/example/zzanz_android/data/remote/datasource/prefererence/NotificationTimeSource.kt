package com.example.zzanz_android.data.remote.datasource.prefererence

import com.example.zzanz_android.common.Resource
import com.example.zzanz_android.data.remote.api.UserPreferenceServiceImpl
import javax.inject.Inject

class NotificationTimeSource @Inject constructor(
    private val userPreferenceService: UserPreferenceServiceImpl
) {
    suspend fun getNotificationTime(): Resource<List<Int?>> {
        return try {
            userPreferenceService.getNotificationTime()
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }


    suspend fun setNotificationTime(hour: Int, minute: Int): Resource<Boolean> {
        return try {
            userPreferenceService.setNotificationTime(hour, minute)
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }

}