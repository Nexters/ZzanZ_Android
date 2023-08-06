package com.example.zzanz_android.presentation.view

import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.zzanz_android.R
import com.example.zzanz_android.common.NavRoutes
import com.example.zzanz_android.common.SettingNavRoutes
import com.example.zzanz_android.common.ui.theme.ZzanZColorPalette
import com.example.zzanz_android.common.ui.theme.ZzanZDimen
import com.example.zzanz_android.common.ui.util.keyboardAsState
import com.example.zzanz_android.domain.model.BudgetCategoryData
import com.example.zzanz_android.domain.model.Category
import com.example.zzanz_android.presentation.view.component.BottomGreenButton
import com.example.zzanz_android.presentation.view.component.CategoryBottomSheet
import com.example.zzanz_android.presentation.view.setting.AlarmSetting
import com.example.zzanz_android.presentation.view.setting.BudgetByCategory
import com.example.zzanz_android.presentation.view.setting.BudgetCategory
import com.example.zzanz_android.presentation.view.setting.NestEggExplainText
import com.example.zzanz_android.presentation.view.setting.SetBudget
import kotlinx.coroutines.launch

data class SettingUiData(
    val currentRoute: String,
    @StringRes val TitleText: Int,
    @StringRes var buttonText: Int,
    val nextRoute: String,
    val backRoute: String,
)

object SettingRoute {
    val data = listOf(
        SettingUiData(
            currentRoute = SettingNavRoutes.BudgetByCategory.route,
            TitleText = R.string.budget_by_category_title,
            nextRoute = SettingNavRoutes.AlarmSetting.route,
            backRoute = SettingNavRoutes.BudgetCategory.route,
            buttonText = R.string.next
        ), SettingUiData(
            currentRoute = SettingNavRoutes.AlarmSetting.route,
            TitleText = R.string.set_alarm_time_title,
            nextRoute = NavRoutes.Home.route,
            backRoute = SettingNavRoutes.BudgetByCategory.route,
            buttonText = R.string.set_alarm_time_btn_title
        ), SettingUiData(
            currentRoute = SettingNavRoutes.Budget.route,
            TitleText = R.string.next_week_budget_title,
            nextRoute = SettingNavRoutes.BudgetCategory.route,
            backRoute = NavRoutes.Splash.route,
            buttonText = R.string.next
        ), SettingUiData(
            currentRoute = SettingNavRoutes.BudgetCategory.route,
            TitleText = R.string.next_week_budget_category,
            nextRoute = SettingNavRoutes.BudgetByCategory.route,
            backRoute = SettingNavRoutes.Budget.route,
            buttonText = R.string.next

        )
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Setting(navController: NavHostController, route: String = SettingNavRoutes.Budget.route) {
    val uiData: SettingUiData = SettingRoute.data.single {
        it.currentRoute == route
    }
    val title = stringResource(id = uiData.TitleText)
    var isButtonEnabled by remember { mutableStateOf(true) }
    val coroutineScope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val isKeyboardOpen by keyboardAsState()
    val budgetCategoryData = remember {
        mutableStateOf(BudgetCategoryData.category)
    }

    LaunchedEffect(key1 = isButtonEnabled, key2 = isKeyboardOpen, block = {})
    if (route == SettingNavRoutes.BudgetByCategory.route && isKeyboardOpen) {
        uiData.buttonText = R.string.budget_by_category_write_btn_title
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = ZzanZColorPalette.current.White)
    ) {
        TopBar(navController = navController, route = route, backRoute = uiData.backRoute)
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
        ) {
            when (route) {
                SettingNavRoutes.BudgetByCategory.route -> {
                    // TODO -  BudgetCategory 와 budgetCategoryData 공유하도록 ViewModel 연결하기
                    BudgetByCategory(titleText = title,
                        budgetCategoryData = budgetCategoryData,
                        onAddCategoryClicked = {
                            coroutineScope.launch {
                                sheetState.show()
                            }
                        })
                }

                SettingNavRoutes.AlarmSetting.route -> {
                    AlarmSetting(title)
                }

                SettingNavRoutes.Budget.route -> {
                    SetBudget(titleText = title) { budget ->
                        isButtonEnabled = if (budget.isNullOrEmpty()) false
                        else budget.all { Character.isDigit(it) }
                    }
                }

                SettingNavRoutes.BudgetCategory.route -> {
                    isButtonEnabled = budgetCategoryData.value.any {
                        it.isChecked
                    }
                    BudgetCategory(
                        textModifier = Modifier
                            .padding(horizontal = 24.dp)
                            .padding(bottom = 18.dp),
                        categoryModifier = Modifier.padding(horizontal = 18.dp, vertical = 18.dp),
                        titleText = title,
                        budgetCategoryData = budgetCategoryData
                    )
                }
            }
            Spacer(modifier = Modifier.weight(1f))
            if (route == SettingNavRoutes.BudgetByCategory.route &&
                budgetCategoryData.value.any {
                    it.categoryId == Category.NESTEGG && it.budget.value.toString() != "0"
                }) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = ZzanZDimen.current.defaultHorizontal)
                        .padding(top = 8.dp, bottom = 12.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    NestEggExplainText(
                        prefix = stringResource(id = R.string.budget_save_money_title_1),
                        suffix = stringResource(id = R.string.budget_save_money_title_2),
                        amount = budgetCategoryData.value.single {
                            it.categoryId == Category.NESTEGG
                        }.budget.value.text,
                        amountColor = ZzanZColorPalette.current.Gray04
                    )
                }
            }
            BottomGreenButton(
                buttonText = stringResource(id = uiData.buttonText),
                onClick = {
                    if (isButtonEnabled) {
                        navController.navigate(uiData.nextRoute) {
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
                // TODO - CategoryBottomSheet ViewModel 연결하기
                CategoryBottomSheet(
                    coroutineScope = coroutineScope,
                    sheetState = sheetState,
                    budgetCategoryData = budgetCategoryData
                )
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