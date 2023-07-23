package com.example.zzanz_android.common

sealed class NavRoutes(val route: String) {
    object Splash : NavRoutes("splash")
    object Home : NavRoutes("home")
    object Setting : NavRoutes("setting")
}

sealed class SettingNavRoutes(val route: String) {
    object  Budget : NavRoutes("BudgetSetting")
    object BudgetCategory: NavRoutes("BudgetCategorySetting")
    object BudgetByCategory : NavRoutes("BudgetByCategory")
    object AlarmSetting : NavRoutes("AlarmSetting")
}