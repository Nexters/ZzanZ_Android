package com.example.zzanz_android.common

sealed class NavRoutes(val route: String) {
    object Splash : NavRoutes("splash")
    object Home : NavRoutes("home")
    object Setting : NavRoutes("setting")
}