package com.zzanz.swip_android.data.remote.api

import com.zzanz.swip_android.common.Resource
import com.zzanz.swip_android.data.remote.dto.UserPrefDto

interface UserPreferenceService {
    suspend fun setUserPreference(userPrefDto: UserPrefDto): Resource<Boolean>

    suspend fun getUserPreference(): UserPrefDto?

    suspend fun clear() : Resource<Boolean>


    suspend fun setFcmToken(fcmToken: String): Resource<Boolean>
    suspend fun getFcmToken(): Resource<String?>

    suspend fun setLastRoute(route: String): Resource<Boolean>
    suspend fun getLastRoute(): Resource<String?>

    suspend fun setNotificationTime(hour: Int, minute: Int): Resource<Boolean>
    suspend fun getNotificationTime(): Resource<List<Int?>>

}