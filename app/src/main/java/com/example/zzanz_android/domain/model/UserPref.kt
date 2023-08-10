package com.example.zzanz_android.domain.model

import com.example.zzanz_android.common.navigation.SettingNavRoutes

data class UserPref(
    val lastRoute: String = SettingNavRoutes.Budget.route,
    val fcmToken: String = ""
)