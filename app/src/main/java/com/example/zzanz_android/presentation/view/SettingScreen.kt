package com.example.zzanz_android.presentation.view

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.zzanz_android.R
import com.example.zzanz_android.common.navigation.NavRoutes
import com.example.zzanz_android.common.navigation.SettingNavRoutes
import com.example.zzanz_android.common.ui.theme.ZzanZColorPalette
import com.example.zzanz_android.common.ui.theme.ZzanZDimen
import com.example.zzanz_android.common.ui.util.keyboardAsState
import com.example.zzanz_android.domain.model.Category
import com.example.zzanz_android.presentation.contract.BudgetContract
import com.example.zzanz_android.presentation.view.component.AppBarWithBackNavigation
import com.example.zzanz_android.presentation.view.component.BottomGreenButton
import com.example.zzanz_android.presentation.view.component.CategoryBottomSheet
import com.example.zzanz_android.presentation.view.setting.BudgetByCategory
import com.example.zzanz_android.presentation.view.setting.BudgetCategory
import com.example.zzanz_android.presentation.view.setting.NestEggExplainText
import com.example.zzanz_android.presentation.view.setting.SetBudget
import com.example.zzanz_android.presentation.viewmodel.BudgetViewModel
import kotlinx.coroutines.launch

data class SettingUiData(
    val currentRoute: String,
    @StringRes val titleText: Int,
    @StringRes var buttonText: Int,
    val nextRoute: String,
    val backRoute: String,
)

object SettingRoute {
    val data = listOf(
        SettingUiData(
            currentRoute = SettingNavRoutes.BudgetByCategory.route,
            titleText = R.string.budget_by_category_title,
            nextRoute = NavRoutes.Notification.route,
            backRoute = SettingNavRoutes.BudgetCategory.route,
            buttonText = R.string.next
        ), SettingUiData(
            currentRoute = SettingNavRoutes.Budget.route,
            titleText = R.string.next_week_budget_title,
            nextRoute = SettingNavRoutes.BudgetCategory.route,
            backRoute = NavRoutes.Splash.route,
            buttonText = R.string.next
        ), SettingUiData(
            currentRoute = SettingNavRoutes.BudgetCategory.route,
            titleText = R.string.next_week_budget_category,
            nextRoute = SettingNavRoutes.BudgetByCategory.route,
            backRoute = SettingNavRoutes.Budget.route,
            buttonText = R.string.next

        )
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Setting(
    navController: NavHostController,
    route: String = SettingNavRoutes.Budget.route,
    budgetViewModel: BudgetViewModel = hiltViewModel()
) {

    // TODO ViewModel에서 세팅할 수 있도록 변경하기
    val uiData: SettingUiData = SettingRoute.data.single {
        it.currentRoute == route
    }
    // TODO ViewModel에서 세팅할 수 있도록 변경하기
    val title = stringResource(id = uiData.titleText)
    var buttonTitle: String = ""
    val coroutineScope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val isKeyboardOpen by keyboardAsState()

    val screenType = budgetViewModel.screenType.collectAsState().value
    val buttonState = budgetViewModel.uiState.collectAsState().value.buttonState
    val budgetCategoryData = budgetViewModel.budgetData.collectAsState().value.category

    val budgetCategoryState =
        budgetViewModel.uiState.collectAsState().value.budgetByCategoryItemState
    val totalCategoryCnt = budgetCategoryState.value.totalCategory.value
    val enteredCategoryCnt = budgetCategoryState.value.enteredCategory.value

    val onNavRoutes = {
        navController.navigate(uiData.nextRoute) {
            popUpTo(NavRoutes.Setting.route) {
                inclusive = true
            }
        }
    }

    LaunchedEffect(key1 = Unit, block = {
        budgetViewModel.setEvent(BudgetContract.Event.SetSettingScreenType(uiData.currentRoute))
    })

    LaunchedEffect(key1 = screenType, block = {
        budgetViewModel.setEvent(BudgetContract.Event.SetScreenState(uiData.currentRoute))
    })

    LaunchedEffect(key1 = Unit) {
        budgetViewModel.effect.collect {
            when (it) {
                BudgetContract.Effect.NextRoutes -> {
                    onNavRoutes.invoke()
                }

            }
        }
    }

    LaunchedEffect(key1 = buttonState, block = {})
    LaunchedEffect(key1 = isKeyboardOpen, block = {})
    LaunchedEffect(key1 = budgetCategoryData, block = {})
    LaunchedEffect(key1 = totalCategoryCnt, key2 = enteredCategoryCnt, block = {})

    if (route == SettingNavRoutes.BudgetByCategory.route) {
        if (isKeyboardOpen) {
            if (totalCategoryCnt != enteredCategoryCnt) {
                uiData.buttonText = R.string.budget_by_category_write_btn_title
            } else {
                uiData.buttonText = R.string.budget_by_category_complete_btn_title
            }
        } else {
            uiData.buttonText = R.string.next
        }
    }

    buttonTitle = if (uiData.buttonText == R.string.budget_by_category_write_btn_title) {
        stringResource(
            id = uiData.buttonText,
            enteredCategoryCnt.toString(),
            totalCategoryCnt.toString()
        )
    } else {
        stringResource(id = uiData.buttonText)
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
                    BudgetByCategory(titleText = title,
                        budgetViewModel = budgetViewModel,
                        onAddCategoryClicked = {
                            coroutineScope.launch {
                                sheetState.show()
                            }
                        })
                }

                SettingNavRoutes.Budget.route -> {
                    SetBudget(
                        budgetViewModel = budgetViewModel, titleText = title
                    )
                }

                SettingNavRoutes.BudgetCategory.route -> {
                    BudgetCategory(
                        modifier = Modifier
                            .padding(horizontal = 24.dp)
                            .padding(bottom = 18.dp),
                        categoryModifier = Modifier.padding(horizontal = 18.dp, vertical = 18.dp),
                        titleText = title
                    )
                }
            }
            Spacer(modifier = Modifier.weight(1f))
            if (route == SettingNavRoutes.BudgetByCategory.route && (enteredCategoryCnt == totalCategoryCnt)) {
                if (!isKeyboardOpen) {
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
                            }.budget
                        )
                    }
                }
            }
            BottomGreenButton(
                buttonText = buttonTitle,
                onClick = {
                    if (buttonState.value) {
                        budgetViewModel.setEvent(
                            BudgetContract.Event.OnNextButtonClicked
                        )
                    }
                },
                isButtonEnabled = buttonState.value,
                isKeyboardOpen = isKeyboardOpen,
                horizontalWidth = if (isKeyboardOpen) 0 else 24
            )

            if (sheetState.isVisible) {
                CategoryBottomSheet(
                    budgetViewModel = budgetViewModel,
                    coroutineScope = coroutineScope,
                    sheetState = sheetState
                )
            }
        }
    }
}

@Composable
fun TopBar(navController: NavHostController, route: String, backRoute: String) {
    AppBarWithBackNavigation(
        onBackButtonAction = {
            if (backRoute.isNotEmpty()) {
                navController.navigate(backRoute) {
                    popUpTo(NavRoutes.Setting.route) {
                        inclusive = true
                    }
                }
            } else {
                navController.popBackStack()
            }
        },
        isBackIconVisible = route != SettingNavRoutes.Budget.route
    )
}

@Preview
@Composable
fun SettingPreview() {
    Setting(navController = rememberNavController())
}