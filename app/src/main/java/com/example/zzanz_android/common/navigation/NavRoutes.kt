package com.example.zzanz_android.common.navigation

sealed class NavRoutes(val route: String) {
    object Splash : NavRoutes("splash")
    object Home : NavRoutes("home")
    object Setting : NavRoutes("setting")
    object Spending : NavRoutes("spending")
}

sealed class SettingNavRoutes {
    object  Budget : NavRoutes("BudgetSetting")
    object BudgetCategory: NavRoutes("BudgetCategorySetting")
    object BudgetByCategory : NavRoutes("BudgetByCategory")
    object AlarmSetting : NavRoutes("AlarmSetting")
}

object ArgumentKey{
    const val planIn = "planId"
    const val categoryName = "categoryName"
    const val remainAmount = "remainAmount"
}