package com.zzanz.swip_android.data.remote.datasource.prefererence

import com.zzanz.swip_android.common.Resource
import com.zzanz.swip_android.data.remote.api.UserPreferenceServiceImpl
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