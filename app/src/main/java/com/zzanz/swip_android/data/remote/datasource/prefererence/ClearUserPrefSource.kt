package com.zzanz.swip_android.data.remote.datasource.prefererence

import com.zzanz.swip_android.common.Resource
import com.zzanz.swip_android.data.remote.api.UserPreferenceServiceImpl
import com.zzanz.swip_android.data.remote.dto.UserPrefDto
import javax.inject.Inject

class ClearUserPrefSource @Inject constructor(
    private val userPreferenceService: UserPreferenceServiceImpl
) {
    suspend fun load(userPrefDto: UserPrefDto): Resource<Boolean> {
        return try {
            userPreferenceService.clear()
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }

}