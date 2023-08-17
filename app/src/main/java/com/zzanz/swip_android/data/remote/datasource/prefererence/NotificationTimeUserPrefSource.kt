package com.zzanz.swip_android.data.remote.datasource.prefererence

import com.zzanz.swip_android.common.Resource
import com.zzanz.swip_android.data.remote.api.UserPreferenceServiceImpl
import javax.inject.Inject

class NotificationTimeUserPrefSource @Inject constructor(
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