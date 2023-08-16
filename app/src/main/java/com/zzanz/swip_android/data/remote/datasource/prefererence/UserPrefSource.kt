package com.zzanz.swip_android.data.remote.datasource.prefererence

import com.zzanz.swip_android.common.Resource
import com.zzanz.swip_android.data.remote.api.UserPreferenceServiceImpl
import com.zzanz.swip_android.data.remote.dto.UserPrefDto
import javax.inject.Inject

class UserPrefSource @Inject constructor(
    private val userPrefApi: UserPreferenceServiceImpl
) {
    suspend fun getUserPreference(): UserPrefDto? {
        return userPrefApi.getUserPreference()
    }

    suspend fun setUserPreference(userPrefDto: UserPrefDto): Resource<Boolean> {
        return try {
            userPrefApi.setUserPreference(userPrefDto)
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }

}