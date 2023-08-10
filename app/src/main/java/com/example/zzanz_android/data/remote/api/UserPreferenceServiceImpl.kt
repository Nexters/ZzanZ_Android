package com.example.zzanz_android.data.remote.api

import android.content.Context
import android.content.SharedPreferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.zzanz_android.common.Resource
import com.example.zzanz_android.data.remote.dto.UserPrefDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import java.io.IOException
import javax.inject.Inject

class UserPreferenceServiceImpl @Inject constructor(
    private val context: Context
) : UserPreferenceService {

    companion object {
        private const val USER_PREFERENCES_NAME = "user_preferences"
    }

    private val Context.userDataStore by preferencesDataStore(
        name = USER_PREFERENCES_NAME
    )

    private object PreferenceKeys {
        val KEY_USER_FCM_TOKEN = stringPreferencesKey("KEY_FCM_TOKEN")
        val KEY_LAST_ROUTE = stringPreferencesKey("KEY_LAST_ROUTE")
    }

    val userPrefFlow: Flow<UserPrefDto?> = context.userDataStore.data
        .catch {exception ->
            if (exception is IOException)
                emit(emptyPreferences())
            else
                throw exception
        }.map {preferences ->
            val fcmToken = preferences[PreferenceKeys.KEY_USER_FCM_TOKEN]
            val route = preferences[PreferenceKeys.KEY_LAST_ROUTE]
            UserPrefDto(
                fcmToken = fcmToken ?: return@map null,
                route = route ?: return@map null
            )
        }

    override suspend fun setUserPreference(userPrefDto: UserPrefDto): Resource<Boolean> {
        return Resource.Success(true)
    }

    override suspend fun getUserPreference(): Resource<UserPrefDto?> {
        var userPref: UserPrefDto? = null
        runBlocking {
            context.userDataStore.data.first { preferences ->
                val route = preferences[PreferenceKeys.KEY_LAST_ROUTE]
                val fcmToken = preferences[PreferenceKeys.KEY_USER_FCM_TOKEN]
                userPref = UserPrefDto(
                    route = route,
                    fcmToken = fcmToken
                )
                true
            }
        }
        return Resource.Success(userPref)
    }

    override suspend fun clear(): Resource<Boolean> {
        try {
            context.userDataStore.edit { preference -> preference.clear() }
        } catch (exception: Exception) {
            return Resource.Error(exception)
        }
        return Resource.Success(true)
    }

    override suspend fun setFcmToken(fcmToken: String): Resource<Boolean> {
        try {
            context.userDataStore.edit {preference ->
                preference[PreferenceKeys.KEY_USER_FCM_TOKEN] = fcmToken
            }
        } catch (exception: Exception) {
            return Resource.Error(exception)
        }
        return Resource.Success(true)
    }

    override suspend fun getFcmToken(): Resource<String?> {
        var userPref: UserPrefDto? = null
        runBlocking {
            context.userDataStore.data.first { preferences ->
                val route = preferences[PreferenceKeys.KEY_LAST_ROUTE]
                val fcmToken = preferences[PreferenceKeys.KEY_USER_FCM_TOKEN]
                userPref = UserPrefDto(
                    route = route,
                    fcmToken = fcmToken
                )
                true
            }
        }
        if (userPref == null) return Resource.Error(java.lang.Exception("userPrefData is Null"))
        return Resource.Success(userPref?.fcmToken)
    }

    override suspend fun setLastRoute(route: String): Resource<Boolean> {
        try {
            context.userDataStore.edit {preference ->
                preference[PreferenceKeys.KEY_LAST_ROUTE] = route
            }
        } catch (exception: Exception) {
            return Resource.Error(exception)
        }
        return Resource.Success(true)
    }

    override suspend fun getLastRoute(): Resource<String?> {
        var userPref: UserPrefDto? = null
        runBlocking {
            context.userDataStore.data.first { preferences ->
                val route = preferences[PreferenceKeys.KEY_LAST_ROUTE]
                val fcmToken = preferences[PreferenceKeys.KEY_USER_FCM_TOKEN]
                userPref = UserPrefDto(
                    route = route,
                    fcmToken = fcmToken
                )
                true
            }
        }
        if (userPref == null) return Resource.Error(java.lang.Exception("userPrefData is Null"))
        return Resource.Success(userPref?.route)
    }
}