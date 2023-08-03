package com.example.zzanz_android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.WindowCompat
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.example.zzanz_android.common.NavRoutes
import com.example.zzanz_android.common.SettingNavRoutes
import com.example.zzanz_android.common.ui.theme.ZzanZ_AndroidTheme
import com.example.zzanz_android.presentation.view.Home
import com.example.zzanz_android.presentation.view.Setting
import com.example.zzanz_android.presentation.view.Splash
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ZzanZ_AndroidTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    NavHost()
                }
            }
        }
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

@Composable
fun NavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = NavRoutes.Splash.route
) {
    NavHost(
        modifier = modifier.fillMaxSize(),
        navController = navController,
        startDestination = startDestination
    ) {
        settingGraph(navController = navController)

        composable(NavRoutes.Home.route) {
            Home(navController)
        }
        composable(NavRoutes.Splash.route) {
            Splash(navController)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainPreview() {
    ZzanZ_AndroidTheme {
        NavHost()
    }
}