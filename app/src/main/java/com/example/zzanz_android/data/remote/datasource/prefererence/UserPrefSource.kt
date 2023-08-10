package com.example.zzanz_android.data.remote.datasource.prefererence

import com.example.zzanz_android.common.Resource
import com.example.zzanz_android.data.remote.api.UserPreferenceServiceImpl
import com.example.zzanz_android.data.remote.dto.UserPrefDto
import javax.inject.Inject

class UserPrefSource @Inject constructor(
    private val userPreferenceService: UserPreferenceServiceImpl
) {
    suspend fun getUserPreference(): Resource<UserPrefDto?> {
        return try {
            userPreferenceService.getUserPreference()
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }

    suspend fun setUserPreference(userPrefDto: UserPrefDto): Resource<Boolean> {
        return try {
            userPreferenceService.setUserPreference(userPrefDto)
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }

}