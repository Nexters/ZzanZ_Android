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
import com.example.zzanz_android.presentation.view.category.CategoryScreen
import com.example.zzanz_android.presentation.view.home.HomeScreen
import com.example.zzanz_android.presentation.view.notification.NotificationSetting
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
            route = NavRoutes.Spending.route + "/{${ArgumentKey.planId}}/{${ArgumentKey.remainAmount}}/{${ArgumentKey.categoryName}}",
            arguments = listOf(
                navArgument(ArgumentKey.planId) {
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
        composable(NavRoutes.Notification.route + "/{${ArgumentKey.settingType}}") { backStackEntry ->
            val settingType = backStackEntry.arguments?.getString(ArgumentKey.settingType)
            NotificationSetting(navController, settingType)
        }
        composable(route = NavRoutes.Category.route + "/{${ArgumentKey.planId}}/{${ArgumentKey.challengeStatus}}",
            arguments = listOf(navArgument(ArgumentKey.planId) {
                type = NavType.IntType
            }, navArgument(ArgumentKey.challengeStatus) {
                type = NavType.StringType
            })) {
            CategoryScreen(navController = navController)
        }
        settingGraph(navController = navController)
        splashGraph(navController = navController)
    }
}

fun NavGraphBuilder.splashGraph(navController: NavHostController) {
    navigation(
        startDestination = SplashNavRoutes.ExplainService.route,
        route = NavRoutes.Splash.route
    ) {
        composable(SplashNavRoutes.ExplainService.route) {
            Splash(navController = navController, SplashNavRoutes.ExplainService.route)
        }
        composable(SplashNavRoutes.ChallengeStart.route) {
            Splash(navController = navController, SplashNavRoutes.ChallengeStart.route)
        }
    }
}

fun NavGraphBuilder.settingGraph(navController: NavHostController) {
    navigation(
        startDestination = SettingNavRoutes.Budget.route, route = NavRoutes.Setting.route
    ) {
        composable(SettingNavRoutes.Budget.route + "/{${ArgumentKey.settingType}}") { backStackEntry ->
            val settingType = backStackEntry.arguments?.getString(ArgumentKey.settingType)
            Setting(
                navController = navController,
                route = SettingNavRoutes.Budget.route,
                settingType = settingType
            )
        }
        composable(SettingNavRoutes.BudgetByCategory.route + "/{${ArgumentKey.settingType}}") { backStackEntry ->
            val settingType = backStackEntry.arguments?.getString(ArgumentKey.settingType)
            Setting(
                navController = navController,
                route = SettingNavRoutes.BudgetByCategory.route,
                settingType = settingType
            )
        }
        composable(SettingNavRoutes.BudgetCategory.route + "/{${ArgumentKey.settingType}}") { backStackEntry ->
            val settingType = backStackEntry.arguments?.getString(ArgumentKey.settingType)
            Setting(
                navController = navController,
                route = SettingNavRoutes.BudgetCategory.route,
                settingType = settingType
            )
        }
    }
}