package com.example.zzanz_android.data.remote.datasource.prefererence

import com.example.zzanz_android.common.Resource
import com.example.zzanz_android.data.remote.api.UserPreferenceServiceImpl
import com.example.zzanz_android.data.remote.dto.UserPrefDto
import javax.inject.Inject

class FcmTokenUserPrefSource @Inject constructor(
    private val userPreferenceService: UserPreferenceServiceImpl
) {
    suspend fun getFcmToken(): Resource<String?> {
        return try {
            userPreferenceService.getFcmToken()
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }


    suspend fun setFcmToken(fcmToken: String): Resource<Boolean> {
        return try {
            userPreferenceService.setFcmToken(fcmToken)
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }

}