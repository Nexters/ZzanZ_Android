package com.zzanz.swip_android.domain.model

import com.zzanz.swip_android.common.navigation.SettingNavRoutes

data class UserPref(
    val lastRoute: String? = SettingNavRoutes.Budget.route,
    val fcmToken: String? = "",
    val notificationHour: String? = "22",
    val notificationMinute: String? = "0"
)
