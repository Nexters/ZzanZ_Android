package com.example.zzanz_android.presentation.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.textInputServiceFactory
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.zzanz_android.R
import com.example.zzanz_android.common.NavRoutes
import com.example.zzanz_android.common.SettingNavRoutes
import com.example.zzanz_android.common.ui.theme.Dimen
import com.example.zzanz_android.common.ui.theme.ZzanZColorPalette
import com.example.zzanz_android.presentation.view.component.CategoryBottomSheet
import com.example.zzanz_android.presentation.view.component.GreenRectButton
import com.example.zzanz_android.presentation.view.component.GreenRoundButton
import com.example.zzanz_android.common.ui.util.keyboardAsState
import com.example.zzanz_android.presentation.view.component.BottomGreenButton
import com.example.zzanz_android.presentation.view.component.GreenButton
import com.example.zzanz_android.presentation.view.setting.AlarmSetting
import com.example.zzanz_android.presentation.view.setting.BudgetByCategory
import com.example.zzanz_android.presentation.view.setting.BudgetCategory
import com.example.zzanz_android.presentation.view.setting.SetBudget
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Setting(navController: NavHostController, route: String = SettingNavRoutes.Budget.route) {
    var nextRoute: String = ""
    var backRoute: String = ""
    var buttonText: String = ""
    var isButtonEnabled by remember { mutableStateOf(true) }
    val coroutineScope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState()
    val isKeyboardOpen by keyboardAsState()

    when (route) {
        SettingNavRoutes.BudgetByCategory.route -> {
            nextRoute = SettingNavRoutes.AlarmSetting.route
            backRoute = SettingNavRoutes.BudgetCategory.route
            buttonText = stringResource(id = R.string.complete)
        }

        SettingNavRoutes.AlarmSetting.route -> {
            nextRoute = NavRoutes.Home.route
            backRoute = SettingNavRoutes.BudgetByCategory.route
            buttonText = stringResource(id = R.string.set_alarm_time_btn_title)
        }

        SettingNavRoutes.Budget.route -> {
            nextRoute = SettingNavRoutes.BudgetCategory.route
            backRoute = NavRoutes.Splash.route
            buttonText = stringResource(id = R.string.next)
        }

        SettingNavRoutes.BudgetCategory.route -> {
            nextRoute = SettingNavRoutes.BudgetByCategory.route
            backRoute = SettingNavRoutes.Budget.route
            buttonText = stringResource(id = R.string.next)
        }
    }
    LaunchedEffect(key1 = isButtonEnabled, key2 = isKeyboardOpen, block = {})
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = ZzanZColorPalette.current.White)
    ) {
        TopBar(navController = navController, route = route, backRoute = backRoute)
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
        ) {
            when (route) {
                SettingNavRoutes.BudgetByCategory.route -> {
                    BudgetByCategory(onAddClicked = {
                        coroutineScope.launch {
                            sheetState.show()
                        }
                    })
                }

                SettingNavRoutes.AlarmSetting.route -> {
                    AlarmSetting()
                }

                SettingNavRoutes.Budget.route -> {
                    SetBudget { budget ->
                        isButtonEnabled = budget.isNotEmpty()
                    }
                }

                SettingNavRoutes.BudgetCategory.route -> {
                    BudgetCategory(textModifier = Modifier.padding(horizontal = 24.dp),
                        categoryModifier = Modifier.padding(horizontal = 18.dp, vertical = 18.dp),
                        titleText = R.string.budget_by_category_title,
                        onAddClicked = {

                        })
                }
            }
            Spacer(modifier = Modifier.weight(1f))
            BottomGreenButton(
                buttonText = buttonText,
                onClick = {
                    if (isButtonEnabled) {
                        navController.navigate(nextRoute) {
                            popUpTo(NavRoutes.Setting.route) {
                                inclusive = true
                            }
                        }
                    }
                },
                isButtonEnabled = isButtonEnabled,
                isKeyboardOpen = isKeyboardOpen,
                horizontalWidth = if (isKeyboardOpen) 0 else 24
            )

            if (sheetState.isVisible) {
                CategoryBottomSheet(coroutineScope = coroutineScope, sheetState = sheetState)
            }
        }
    }
}

@Composable
fun TopBar(navController: NavHostController, route: String, backRoute: String) {
    Row(
        modifier = Modifier
            .padding()
            .height(56.dp)
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        if (route != SettingNavRoutes.AlarmSetting.route || route != SettingNavRoutes.Budget.route) {
            Image(painter = painterResource(id = R.drawable.icon_left),
                contentDescription = "back to before frame",
                contentScale = ContentScale.None,
                modifier = Modifier
                    .size(28.dp)
                    .clickable {
                        if (!backRoute.isNullOrEmpty()) {
                            navController.navigate(backRoute) {
                                popUpTo(NavRoutes.Setting.route) {
                                    inclusive = true
                                }
                            }
                        } else {
                            navController.popBackStack()
                        }
                    })
        }
    }
}

@Preview
@Composable
fun SettingPreview() {
    Setting(navController = rememberNavController())
}