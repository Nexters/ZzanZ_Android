package com.example.zzanz_android.common.navigation

sealed class NavRoutes(val route: String) {
    object Splash : NavRoutes("splash")
    object Home : NavRoutes("home")
    object Setting : NavRoutes("setting")
    object Notification : NavRoutes("notification")
    object Category : NavRoutes("category")
    object Spending : NavRoutes("spending")
}

sealed class SettingNavRoutes {
    object Budget : NavRoutes("BudgetSetting")
    object BudgetCategory : NavRoutes("BudgetCategorySetting")
    object BudgetByCategory : NavRoutes("BudgetByCategory")
}

object SettingType {
    const val home = "home"
    const val onBoarding = "onBoarding"
}

object ArgumentKey {
    const val planId = "planId"
    const val categoryName = "categoryName"
    const val remainAmount = "remainAmount"
    const val challengeStatus = "challengeStatus"
    const val settingType = "settingType"
}