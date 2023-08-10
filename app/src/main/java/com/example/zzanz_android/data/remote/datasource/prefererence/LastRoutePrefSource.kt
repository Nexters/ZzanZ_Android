package com.example.zzanz_android.data.remote.datasource.prefererence

import com.example.zzanz_android.common.Resource
import com.example.zzanz_android.data.remote.api.UserPreferenceServiceImpl
import com.example.zzanz_android.data.remote.dto.UserPrefDto
import javax.inject.Inject

class LastRoutePrefSource @Inject constructor(
    private val userPreferenceService: UserPreferenceServiceImpl
) {
    suspend fun getLastRoute(): Resource<String?> {
        return try {
            userPreferenceService.getLastRoute()
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }


    suspend fun setLastRoute(route: String): Resource<Boolean> {
        return try {
            userPreferenceService.setLastRoute(route)
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }

}