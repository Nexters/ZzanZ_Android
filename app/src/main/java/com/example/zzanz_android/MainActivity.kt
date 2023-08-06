package com.example.zzanz_android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.example.zzanz_android.common.NavRoutes
import com.example.zzanz_android.common.SettingNavRoutes
import com.example.zzanz_android.common.SpendingNavRoutes
import com.example.zzanz_android.common.ui.theme.ZzanZ_AndroidTheme
import com.example.zzanz_android.common.ui.util.keyboardAsState
import com.example.zzanz_android.presentation.view.Home
import com.example.zzanz_android.presentation.view.Setting
import com.example.zzanz_android.presentation.view.Splash
import com.example.zzanz_android.presentation.view.spending.AddSpendingScreen
import dagger.hilt.android.AndroidEntryPoint
import io.ktor.client.HttpClient
import kotlinx.coroutines.runBlocking

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val isKeyboardOpen by keyboardAsState()
            ZzanZ_AndroidTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    NavHost()
                    LaunchedEffect(key1 = isKeyboardOpen, block = {})
                    if (isKeyboardOpen) {
                        Spacer(modifier = Modifier.padding(WindowInsets.ime.getBottom(LocalDensity.current).dp))
                    }
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

// TODO : gowoon - Navigation Graph 파일 생성
fun NavGraphBuilder.spendingGraph(navController: NavHostController){
    navigation(
        startDestination = SpendingNavRoutes.AddSpending.route,
        route = NavRoutes.Spending.route
    ){
        composable(SpendingNavRoutes.AddSpending.route){
            AddSpendingScreen(navController)
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
        spendingGraph(navController = navController)
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