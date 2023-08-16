package com.zzanz.swip_android.domain.repository

import com.zzanz.swip_android.common.Resource
import com.zzanz.swip_android.data.remote.dto.UserPrefDto
import com.zzanz.swip_android.domain.model.UserPref
import kotlinx.coroutines.flow.Flow

interface UserPreferenceRepository {

    suspend fun setUserPref(userPref: UserPrefDto): Flow<Resource<Boolean>>
    suspend fun getUserPref(): Flow<Resource<UserPref?>>


    suspend fun setFcmToken(fcmToken: String): Flow<Resource<Boolean>>
    suspend fun getFcmToken(): Flow<Resource<String?>>

    suspend fun setLastNavRoute(route: String): Flow<Resource<Boolean>>
    suspend fun getLastNavRoute(): Flow<Resource<String?>>

    suspend fun setNotificationTime(hour: Int, minute: Int): Flow<Resource<Boolean>>

    suspend fun getNotificationTime(): Flow<Resource<List<Int?>>>


}