package com.zzanz.swip_android.common.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.zzanz.swip_android.presentation.view.onboarding.OnBoarding
import com.zzanz.swip_android.presentation.view.setting.Setting
import com.zzanz.swip_android.presentation.view.category.CategoryScreen
import com.zzanz.swip_android.presentation.view.home.HomeScreen
import com.zzanz.swip_android.presentation.view.notification.NotificationSetting
import com.zzanz.swip_android.presentation.view.spending.AddSpendingScreen
import com.zzanz.swip_android.presentation.viewmodel.PlanListViewModel

@Composable
fun NavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = NavRoutes.Splash.route,
    planListViewModel: PlanListViewModel = hiltViewModel()
) {
    androidx.navigation.compose.NavHost(
        modifier = modifier.fillMaxSize(),
        navController = navController,
        startDestination = startDestination
    ) {
        composable(NavRoutes.Splash.route) {
            OnBoarding(navController)
        }
        composable(NavRoutes.Home.route) {
            HomeScreen(navController, planListViewModel = planListViewModel)
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
        composable(
            route = NavRoutes.Notification.route + "?{${ArgumentKey.settingType}}",
            arguments = listOf(navArgument(ArgumentKey.settingType) {
                type = NavType.StringType
            })
        ) {
            NotificationSetting(
                navController = navController
            )
        }

        composable(
            route = SettingNavRoutes.Budget.route
        ) {
            Setting(
                navController = navController,
                route = SettingNavRoutes.Budget.route,
                planListViewModel = planListViewModel
            )
        }

        composable(
            route = NavRoutes.Notification.route
        ) {
            NotificationSetting(
                navController = navController
            )
        }

        composable(
            route = NavRoutes.Category.route + "/{${ArgumentKey.planId}}/{${ArgumentKey.challengeStatus}}",
            arguments = listOf(navArgument(ArgumentKey.planId) {
                type = NavType.IntType
            }, navArgument(ArgumentKey.challengeStatus) {
                type = NavType.StringType
            })
        ) {
            CategoryScreen(navController = navController)
        }
        settingGraph(navController = navController, planListViewModel = planListViewModel)
        splashGraph(navController = navController)
    }
}

fun NavGraphBuilder.splashGraph(
    navController: NavHostController
) {
    navigation(
        startDestination = SplashNavRoutes.ExplainService.route, route = NavRoutes.Splash.route
    ) {
        composable(SplashNavRoutes.ExplainService.route) {
            OnBoarding(navController = navController, SplashNavRoutes.ExplainService.route)
        }
        composable(SplashNavRoutes.ChallengeStart.route) {
            OnBoarding(navController = navController, SplashNavRoutes.ChallengeStart.route)
        }
    }
}

fun NavGraphBuilder.settingGraph(
    navController: NavHostController, planListViewModel: PlanListViewModel
) {
    navigation(
        startDestination = SettingNavRoutes.Budget.route, route = NavRoutes.Setting.route
    ) {
        composable(
            route = SettingNavRoutes.Budget.route + "?{${ArgumentKey.settingType}}",
            arguments = listOf(navArgument(ArgumentKey.settingType) {
                type = NavType.StringType
            })
        ) {
            Setting(
                navController = navController,
                route = SettingNavRoutes.Budget.route,
                planListViewModel = planListViewModel
            )
        }
        composable(
            SettingNavRoutes.BudgetByCategory.route + "?{${ArgumentKey.settingType}}",
            arguments = listOf(navArgument(ArgumentKey.settingType) {
                type = NavType.StringType
            })
        ) {
            Setting(
                navController = navController,
                route = SettingNavRoutes.BudgetByCategory.route,
                planListViewModel = planListViewModel
            )

        }
        composable(
            SettingNavRoutes.BudgetCategory.route + "?{${ArgumentKey.settingType}}",
            arguments = listOf(navArgument(ArgumentKey.settingType) {
                type = NavType.StringType
            })
        ) {
            Setting(
                navController = navController,
                route = SettingNavRoutes.BudgetCategory.route,
                planListViewModel = planListViewModel
            )

        }
    }
}