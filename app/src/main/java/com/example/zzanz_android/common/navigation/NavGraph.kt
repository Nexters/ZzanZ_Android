package com.example.zzanz_android.common.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.example.zzanz_android.presentation.view.Setting
import com.example.zzanz_android.presentation.view.Splash
import com.example.zzanz_android.presentation.view.home.HomeScreen
import com.example.zzanz_android.presentation.view.spending.AddSpendingScreen

@Composable
fun NavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = NavRoutes.Splash.route
) {
    androidx.navigation.compose.NavHost(
        modifier = modifier.fillMaxSize(),
        navController = navController,
        startDestination = startDestination
    ) {
        composable(NavRoutes.Splash.route) {
            Splash(navController)
        }
        composable(NavRoutes.Home.route) {
            HomeScreen(navController)
        }
        composable(
            route = NavRoutes.Spending.route + "/{${ArgumentKey.planIn}}/{${ArgumentKey.remainAmount}}/{${ArgumentKey.categoryName}}",
            arguments = listOf(
                navArgument(ArgumentKey.planIn) {
                    type = NavType.IntType
                },
                navArgument(ArgumentKey.remainAmount) {
                    type = NavType.IntType
                },
                navArgument(ArgumentKey.categoryName) {
                    type = NavType.StringType
                },
            )
        ) {
            AddSpendingScreen(navController = navController)
        }
        settingGraph(navController = navController)
    }
}

fun NavGraphBuilder.settingGraph(navController: NavHostController) {
    navigation(
        startDestination = SettingNavRoutes.Budget.route,
        route = NavRoutes.Setting.route
    ) {
        composable(SettingNavRoutes.Budget.route) {
            Setting(navController = navController, route = SettingNavRoutes.Budget.route)
        }
        composable(SettingNavRoutes.BudgetByCategory.route) {
            Setting(navController = navController, route = SettingNavRoutes.BudgetByCategory.route)
        }
        composable(SettingNavRoutes.BudgetCategory.route) {
            Setting(navController = navController, route = SettingNavRoutes.BudgetCategory.route)
        }
        composable(SettingNavRoutes.AlarmSetting.route) {
            Setting(navController = navController, route = SettingNavRoutes.AlarmSetting.route)
        }
    }
}