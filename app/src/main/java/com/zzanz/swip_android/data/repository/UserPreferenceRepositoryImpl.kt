package com.zzanz.swip_android.data.repository

import com.zzanz.swip_android.common.Resource
import com.zzanz.swip_android.data.mapper.UserPrefMapper.toModel
import com.zzanz.swip_android.data.remote.datasource.prefererence.FcmTokenUserPrefSource
import com.zzanz.swip_android.data.remote.datasource.prefererence.LastRoutePrefSource
import com.zzanz.swip_android.data.remote.datasource.prefererence.NotificationTimeUserPrefSource
import com.zzanz.swip_android.data.remote.datasource.prefererence.UserPrefSource
import com.zzanz.swip_android.data.remote.dto.UserPrefDto
import com.zzanz.swip_android.domain.model.UserPref
import com.zzanz.swip_android.domain.repository.UserPreferenceRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class UserPreferenceRepositoryImpl @Inject constructor(
    private val userPrefSource: UserPrefSource,
    private val fcmTokenUserPrefSource: FcmTokenUserPrefSource,
    private val routePrefSource: LastRoutePrefSource,
    private val notificationTimeUserPrefSource: NotificationTimeUserPrefSource
) : UserPreferenceRepository {
    override suspend fun setUserPref(userPref: UserPrefDto): Flow<Resource<Boolean>> {
        return flow {
            val result = userPrefSource.setUserPreference(userPrefDto = userPref)
            emit(result)
        }
    }

    override suspend fun getUserPref(): Flow<Resource<UserPref?>> {
        return flow {
            try {
                emit(Resource.Success(userPrefSource.getUserPreference()?.toModel()))
            } catch (exception: Exception) {
                emit(Resource.Error(exception))
            }
        }
    }

    override suspend fun setFcmToken(fcmToken: String): Flow<Resource<Boolean>> {
        return flow {
            val result = fcmTokenUserPrefSource.setFcmToken(fcmToken = fcmToken)
            emit(result)
        }
    }

    override suspend fun getFcmToken(): Flow<Resource<String?>> {
        return flow {
            val result = fcmTokenUserPrefSource.getFcmToken()
            emit(result)
        }
    }

    override suspend fun setLastNavRoute(route: String): Flow<Resource<Boolean>> {
        return flow {
            val result = routePrefSource.setLastRoute(route = route)
            emit(result)
        }
    }

    override suspend fun getLastNavRoute(): Flow<Resource<String?>> {
        return flow {
            val result = routePrefSource.getLastRoute()
            emit(result)
        }
    }

    override suspend fun setNotificationTime(
        hour: Int, minute: Int
    ): Flow<Resource<Boolean>> {
        return flow {
            val result = notificationTimeUserPrefSource.setNotificationTime(hour, minute)
            emit(result)
        }
    }

    override suspend fun getNotificationTime(): Flow<Resource<List<Int?>>> {
        return flow {
            val result = notificationTimeUserPrefSource.getNotificationTime()
            emit(result)
        }
    }

}