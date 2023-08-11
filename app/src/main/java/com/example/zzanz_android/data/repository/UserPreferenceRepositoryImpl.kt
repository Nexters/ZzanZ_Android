package com.example.zzanz_android.data.repository

import com.example.zzanz_android.common.Resource
import com.example.zzanz_android.data.mapper.UserPrefMapper.toModel
import com.example.zzanz_android.data.remote.datasource.prefererence.FcmTokenUserPrefSource
import com.example.zzanz_android.data.remote.datasource.prefererence.LastRoutePrefSource
import com.example.zzanz_android.data.remote.datasource.prefererence.UserPrefSource
import com.example.zzanz_android.data.remote.dto.UserPrefDto
import com.example.zzanz_android.domain.model.UserPref
import com.example.zzanz_android.domain.repository.UserPreferenceRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class UserPreferenceRepositoryImpl @Inject constructor(
    private val userPrefSource: UserPrefSource,
    private val fcmTokenUserPrefSource: FcmTokenUserPrefSource,
    private val routePrefSource: LastRoutePrefSource,
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

}