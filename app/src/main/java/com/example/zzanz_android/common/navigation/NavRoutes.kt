package com.example.zzanz_android.common.navigation

sealed class NavRoutes(val route: String) {
    object Splash : NavRoutes("splash")
    object Home : NavRoutes("home")
    object Setting : NavRoutes("setting")
    object Alarm : NavRoutes("alarm")
    object Spending : NavRoutes("spending")
}

sealed class SettingNavRoutes {
    object  Budget : NavRoutes("BudgetSetting")
    object BudgetCategory: NavRoutes("BudgetCategorySetting")
    object BudgetByCategory : NavRoutes("BudgetByCategory")
}