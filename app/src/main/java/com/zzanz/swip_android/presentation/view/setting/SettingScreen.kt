package com.zzanz.swip_android.presentation.view.setting

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
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
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.zzanz.swip_android.R
import com.zzanz.swip_android.common.navigation.ArgumentKey
import com.zzanz.swip_android.common.navigation.NavRoutes
import com.zzanz.swip_android.common.navigation.SettingNavRoutes
import com.zzanz.swip_android.common.navigation.SettingType
import com.zzanz.swip_android.common.ui.theme.ZzanZColorPalette
import com.zzanz.swip_android.common.ui.theme.ZzanZDimen
import com.zzanz.swip_android.common.ui.util.keyboardAsState
import com.zzanz.swip_android.domain.model.BudgetCategoryModel
import com.zzanz.swip_android.domain.model.Category
import com.zzanz.swip_android.domain.model.PlanModel
import com.zzanz.swip_android.presentation.view.component.AppBarWithBackNavigation
import com.zzanz.swip_android.presentation.view.component.BottomGreenButton
import com.zzanz.swip_android.presentation.view.component.CategoryBottomSheet
import com.zzanz.swip_android.presentation.view.component.contract.BudgetContract
import com.zzanz.swip_android.presentation.viewmodel.BudgetViewModel
import com.zzanz.swip_android.presentation.viewmodel.PlanListLoadingState
import com.zzanz.swip_android.presentation.viewmodel.PlanListUiEvent
import com.zzanz.swip_android.presentation.viewmodel.PlanListViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Setting(
    navController: NavHostController,
    route: String = SettingNavRoutes.Budget.route,
    budgetViewModel: BudgetViewModel = hiltViewModel(),
    planListViewModel: PlanListViewModel = hiltViewModel(),
) {
    val planListState by planListViewModel.uiState.collectAsState()
    val uiData = budgetViewModel.uiData.collectAsState().value
    val settingType =
        navController.currentBackStackEntry?.arguments?.getString(ArgumentKey.settingType)
            ?: SettingType.onBoarding

    LaunchedEffect(key1 = true, block = {
        budgetViewModel.setEvent(BudgetContract.Event.GetSettingUiData(route, settingType))
        if (route == SettingNavRoutes.Budget.route) {
            if (planListState.planListLoadingState is PlanListLoadingState.Loaded) {
                val planList =
                    (planListState.planListLoadingState as PlanListLoadingState.Loaded).planList
                budgetViewModel.setEvent(BudgetContract.Event.SetBudgetCategoryList(planList))
            }
        }
        if (route == SettingNavRoutes.BudgetByCategory.route) {
            budgetViewModel.setEvent(BudgetContract.Event.OnFetchNestEggItem)
        }
    })

    if (uiData == null) return

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
        navController.navigate(uiData.nextRoute + "?${settingType}")
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
                    if (route == SettingNavRoutes.BudgetByCategory.route && settingType == SettingType.home) {
                        var planList: List<PlanModel>? = null
                        val newPlanList: MutableList<PlanModel> = mutableListOf()
                        if (planListState.planListLoadingState is PlanListLoadingState.Loaded) {
                            budgetViewModel.setEvent(BudgetContract.Event.ClearBudgetCategoryItem)
                            planList =
                                (planListState.planListLoadingState as PlanListLoadingState.Loaded).planList
                        }
                        budgetViewModel.budgetData.value.category.value.map { budget ->
                            planList?.map { plan ->
                                if (budget.categoryId.toString() == plan.category) {
                                    newPlanList.add(
                                        plan.copy(goalAmount = budget.budget.toInt())
                                    )
                                }
                            }
                        }
                        planListViewModel.setEvent(PlanListUiEvent.SetPlanList(newPlanList))
                        navController.navigate(NavRoutes.Home.route) {
                            popUpTo(NavRoutes.Home.route) {
                                inclusive = true
                            }
                        }
                    } else {
                        onNavRoutes.invoke()
                    }
                }
            }
        }
    }
    if (route == SettingNavRoutes.BudgetByCategory.route) {
        if (isKeyboardOpen) {
            if (totalCategoryCnt != enteredCategoryCnt) {
                uiData.buttonText = R.string.budget_by_category_write_btn_title
            } else {
                uiData.buttonText = R.string.budget_by_category_complete_btn_title
            }
        } else {
            uiData.buttonText =
                if (settingType == SettingType.home) R.string.edit_complete else R.string.next
        }
    }

    buttonTitle = if (uiData.buttonText == R.string.budget_by_category_write_btn_title) {
        stringResource(
            id = uiData.buttonText, enteredCategoryCnt.toString(), totalCategoryCnt.toString()
        )
    } else {
        stringResource(id = uiData.buttonText)
    }

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(color = ZzanZColorPalette.current.White)
    ) {
        val (topBarRef, contentRef, bottomRef) = createRefs()
        TopBar(navController = navController,
            route = route,
            modifier = Modifier.constrainAs(topBarRef) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                width = Dimension.fillToConstraints
            })
        val contentModifier = Modifier.constrainAs(contentRef) {
            top.linkTo(topBarRef.bottom)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            bottom.linkTo(bottomRef.top)
            width = Dimension.fillToConstraints
            height = Dimension.fillToConstraints
        }
        Column(
            modifier = contentModifier
        ) {
            when (route) {
                SettingNavRoutes.BudgetByCategory.route -> {
                    BudgetByCategory(
                        titleText = title,
                        budgetViewModel = budgetViewModel,
                        onAddCategoryClicked = {
                            coroutineScope.launch {
                                sheetState.show()
                            }
                        },
                        modifier = contentModifier
                    )
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
        }
        Column(modifier = Modifier.constrainAs(bottomRef) {
            bottom.linkTo(parent.bottom)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            width = Dimension.fillToConstraints
            centerHorizontallyTo(parent)
        }) {
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
                horizontalWidth = if (isKeyboardOpen) 0.dp else 24.dp
            )
        }
        if (sheetState.isVisible) {
            CategoryBottomSheet(
                budgetViewModel = budgetViewModel,
                coroutineScope = coroutineScope,
                sheetState = sheetState
            )
        }
    }
}

@Composable
fun TopBar(navController: NavHostController, route: String, modifier: Modifier) {
    AppBarWithBackNavigation(
        modifier = modifier, appbarColor = ZzanZColorPalette.current.White, onBackButtonAction = {
            navController.popBackStack()
        }, isBackIconVisible = route != SettingNavRoutes.Budget.route
    )
}

@Preview
@Composable
fun SettingPreview() {
    Setting(navController = rememberNavController())
}