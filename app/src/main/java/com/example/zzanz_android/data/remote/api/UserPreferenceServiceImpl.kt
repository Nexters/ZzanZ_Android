package com.example.zzanz_android.data.remote.api

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.zzanz_android.common.Resource
import com.example.zzanz_android.data.remote.dto.UserPrefDto
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import java.io.IOException
import javax.inject.Inject

private val Context.userDataStore by preferencesDataStore(
    name = PreferenceName.USER_PREFERENCES_NAME
)

object PreferenceName {
    const val USER_PREFERENCES_NAME = "user_preferences"
}

@ActivityRetainedScoped
class UserPreferenceServiceImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : UserPreferenceService {

    private object PreferenceKeys {
        val KEY_USER_FCM_TOKEN = stringPreferencesKey("KEY_FCM_TOKEN")
        val KEY_LAST_ROUTE = stringPreferencesKey("KEY_LAST_ROUTE")
        val KEY_NOTI_HOUR = stringPreferencesKey("KEY_NOTI_HOUR")
        val KEY_NOTI_MINUTE = stringPreferencesKey("KEY_NOTI_MINUTE")
    }

    val userPrefFlow: Flow<UserPrefDto?> = context.userDataStore.data
        .catch { exception ->
            if (exception is IOException)
                emit(emptyPreferences())
            else
                throw exception
        }.map { preferences ->
            val fcmToken = preferences[PreferenceKeys.KEY_USER_FCM_TOKEN]
            val route = preferences[PreferenceKeys.KEY_LAST_ROUTE]
            val hour = preferences[PreferenceKeys.KEY_NOTI_HOUR]
            val minute = preferences[PreferenceKeys.KEY_NOTI_MINUTE]
            UserPrefDto(
                fcmToken = fcmToken ?: return@map null,
                route = route ?: return@map null,
                notificationHour = hour ?: return@map null,
                notificationMinute = minute ?: return@map null
            )
        }

    override suspend fun setUserPreference(userPrefDto: UserPrefDto): Resource<Boolean> {
        return Resource.Success(true)
    }

    override suspend fun getUserPreference(): UserPrefDto? {
        var userPref: UserPrefDto? = null
        runBlocking {
            context.userDataStore.data.first { preferences ->
                val route = preferences[PreferenceKeys.KEY_LAST_ROUTE]
                val fcmToken = preferences[PreferenceKeys.KEY_USER_FCM_TOKEN]
                val hour = preferences[PreferenceKeys.KEY_NOTI_HOUR]
                val minute = preferences[PreferenceKeys.KEY_NOTI_MINUTE]
                userPref = UserPrefDto(
                    route = route,
                    fcmToken = fcmToken,
                    notificationHour = hour,
                    notificationMinute = minute
                )
                true
            }
        }
        return userPref
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
            context.userDataStore.edit { preference ->
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
                val hour = preferences[PreferenceKeys.KEY_NOTI_HOUR]
                val minute = preferences[PreferenceKeys.KEY_NOTI_MINUTE]
                userPref = UserPrefDto(
                    route = route,
                    fcmToken = fcmToken,
                    notificationHour = hour,
                    notificationMinute = minute
                )
                true
            }
        }
        if (userPref == null) return Resource.Error(java.lang.Exception("userPrefData is Null"))
        return Resource.Success(userPref?.fcmToken)
    }

    override suspend fun setLastRoute(route: String): Resource<Boolean> {
        try {
            context.userDataStore.edit { preference ->
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
                val hour = preferences[PreferenceKeys.KEY_NOTI_HOUR]
                val minute = preferences[PreferenceKeys.KEY_NOTI_MINUTE]
                userPref = UserPrefDto(
                    route = route,
                    fcmToken = fcmToken,
                    notificationHour = hour,
                    notificationMinute = minute
                )
                true
            }
        }
        if (userPref == null) return Resource.Error(java.lang.Exception("userPrefData is Null"))
        return Resource.Success(userPref?.route)
    }

    override suspend fun setNotificationTime(hour: Int, minute: Int): Resource<Boolean> {
        try {
            context.userDataStore.edit { preference ->
                preference[PreferenceKeys.KEY_NOTI_HOUR] = hour.toString()
                preference[PreferenceKeys.KEY_NOTI_MINUTE] = minute.toString()
            }
        } catch (exception: Exception) {
            return Resource.Error(exception)
        }
        return Resource.Success(true)
    }

    override suspend fun getNotificationTime(): Resource<List<Int?>> {
        var userPref: UserPrefDto? = null
        runBlocking {
            context.userDataStore.data.first { preferences ->
                val route = preferences[PreferenceKeys.KEY_LAST_ROUTE]
                val fcmToken = preferences[PreferenceKeys.KEY_USER_FCM_TOKEN]
                val hour = preferences[PreferenceKeys.KEY_NOTI_HOUR]
                val minute = preferences[PreferenceKeys.KEY_NOTI_MINUTE]
                userPref = UserPrefDto(
                    route = route,
                    fcmToken = fcmToken,
                    notificationHour = hour,
                    notificationMinute = minute
                )
                true
            }
        }
        if (userPref == null) return Resource.Error(java.lang.Exception("userPrefData is Null"))
        return Resource.Success(
            listOf<Int?>(
                userPref?.notificationHour?.toIntOrNull(),
                userPref?.notificationMinute?.toIntOrNull()
            )
        )
    }
}