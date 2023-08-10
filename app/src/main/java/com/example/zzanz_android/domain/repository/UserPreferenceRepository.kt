package com.example.zzanz_android.domain.repository

import com.example.zzanz_android.common.Resource
import com.example.zzanz_android.data.remote.dto.NotificationDto
import kotlinx.coroutines.flow.Flow

interface UserPreferenceRepository {

    suspend fun setFcmToken(fcmToken: String): Flow<Resource<Boolean>>
    suspend fun getFcmToken(): Flow<Resource<String?>>

    suspend fun setLastNavRoute(route: String) : Flow<Resource<Boolean>>
    suspend fun getLastNavRoute(): Flow<Resource<String?>>


}