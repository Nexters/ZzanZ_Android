package com.example.zzanz_android.presentation.view.setting

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.NativeKeyEvent
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onPreviewKeyEvent
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.zzanz_android.R
import com.example.zzanz_android.common.ui.theme.ZzanZColorPalette
import com.example.zzanz_android.common.ui.theme.ZzanZTypo
import com.example.zzanz_android.domain.model.BudgetCategoryModel
import com.example.zzanz_android.domain.model.Category
import com.example.zzanz_android.domain.model.PlanModel
import com.example.zzanz_android.presentation.view.component.CategoryIcon
import com.example.zzanz_android.presentation.view.component.InfoIcon
import com.example.zzanz_android.presentation.view.component.MoneyInputTextField
import com.example.zzanz_android.presentation.view.component.PlusIcon
import com.example.zzanz_android.presentation.view.component.TitleText
import com.example.zzanz_android.presentation.view.component.contract.BudgetContract
import com.example.zzanz_android.presentation.viewmodel.BudgetViewModel
import com.example.zzanz_android.presentation.viewmodel.PlanListLoadingState
import com.example.zzanz_android.presentation.viewmodel.PlanListViewModel

@Composable
fun BudgetByCategory(
    titleText: String,
    budgetViewModel: BudgetViewModel = hiltViewModel(),
    planListViewModel: PlanListViewModel = hiltViewModel(),
    onAddCategoryClicked: () -> Unit,
    modifier: Modifier
) {
    val focusRequester = remember {
        FocusRequester()
    }
    val focusManager = LocalFocusManager.current
    val keyboardAction = KeyboardActions(onNext = {
        focusManager.moveFocus(FocusDirection.Down)
    })
    val keyboardOptions = KeyboardOptions(
        keyboardType = KeyboardType.Number, imeAction = ImeAction.Next
    )
    val budgetCategoryState =
        budgetViewModel.uiState.collectAsState().value.budgetByCategoryItemState.value
    val remainingBudget = budgetCategoryState.remainingBudget
    val budgetCategoryData = budgetViewModel.budgetData.collectAsState().value.category

    var focusEnabled = true

    Column(
        modifier = modifier
    ) {
        LazyColumn {
            item {
                TitleText(
                    modifier = Modifier.padding(horizontal = 24.dp), text = titleText
                )
                Spacer(modifier = Modifier.height(28.dp))
                val isRemainingBudgetEmpty =
                    remainingBudget.value.isNotEmpty() && remainingBudget.value.toInt() == 0
                ExplainRemainingBudget(
                    totalBudget = remainingBudget.value,
                    isRemainingBudgetEmpty = isRemainingBudgetEmpty
                )
            }
            items(budgetCategoryData.value.size) { idx ->
                val item = budgetCategoryData.value[idx]
                val itemModifier = Modifier
                if (item.isChecked && item.categoryId != Category.NESTEGG) {
                    if (focusEnabled) {
                        itemModifier.focusRequester(focusRequester)
                        focusEnabled = false
                    }
                    BudgetByCategoryItem(
                        budgetViewModel = budgetViewModel,
                        budgetCategoryItem = item,
                        modifier = itemModifier,
                        keyboardAction = keyboardAction,
                        keyboardOptions = keyboardOptions,
                        focusManager = focusManager
                    )
                }
                if (item.categoryId == Category.NESTEGG) {
                    BudgetByCategoryItem(
                        budgetViewModel = budgetViewModel,
                        budgetCategoryItem = item,
                        modifier = itemModifier,
                        focusManager = focusManager,
                        keyboardOptions = keyboardOptions,
                        keyboardAction = keyboardAction
                    )
                }
            }
            item {
                AddBudgetByCategoryItemBtn(onAddClicked = onAddCategoryClicked)
                Spacer(modifier = Modifier.height(12.dp))
            }
        }
    }
}

@Composable
fun ExplainRemainingBudget(
    isRemainingBudgetEmpty: Boolean = false, totalBudget: String
) {
    var isRemainingBudgetMinus: Boolean = false
    val budgetTitle = if (totalBudget.isEmpty()) "0" else {
        if (totalBudget.toInt() < 0) {
            isRemainingBudgetMinus = true
            (totalBudget.toInt() * -1).toString()
        } else totalBudget
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(0.dp)
            .background(
                color = ZzanZColorPalette.current.Gray01, shape = RectangleShape
            )
            .padding(horizontal = 24.dp, vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        InfoIcon(color = ZzanZColorPalette.current.Gray08)
        Spacer(modifier = Modifier.width(7.dp))
        Text(
            text = if (isRemainingBudgetEmpty) stringResource(id = R.string.week_budget_complete_title)
            else {
                if (isRemainingBudgetMinus) stringResource(
                    id = R.string.over_budget_title,
                    budgetTitle
                )
                else stringResource(id = R.string.remaining_budget_title, budgetTitle)
            }, style = ZzanZTypo.current.Body03, color = ZzanZColorPalette.current.Gray08
        )
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun BudgetByCategoryItem(
    budgetViewModel: BudgetViewModel = hiltViewModel(),
    budgetCategoryItem: BudgetCategoryModel,
    modifier: Modifier,
    focusManager: FocusManager,
    keyboardAction: KeyboardActions,
    keyboardOptions: KeyboardOptions
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding()
            .background(color = Color.Transparent)
            .padding(horizontal = 24.dp, vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        CategoryIcon(
            modifier = Modifier.size(32.dp), resId = budgetCategoryItem.categoryImage
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(
                text = stringResource(id = budgetCategoryItem.name),
                color = ZzanZColorPalette.current.Gray05,
                style = ZzanZTypo.current.Body03.copy(fontWeight = FontWeight.Medium)
            )
            Row {
                if (budgetCategoryItem.categoryId == Category.NESTEGG) {
                    Text(
                        text = budgetCategoryItem.budget, style = ZzanZTypo.current.Body01.copy(
                            fontWeight = FontWeight.SemiBold
                        ), color = ZzanZColorPalette.current.Gray03
                    )
                } else {
                    MoneyInputTextField(
                        modifier = Modifier
                            .wrapContentWidth()
                            .height(24.dp)
                            .onPreviewKeyEvent {
                                if (it.key == Key.Enter && it.nativeKeyEvent.action == NativeKeyEvent.ACTION_DOWN) {
                                    focusManager.moveFocus(FocusDirection.Down)
                                } else {
                                    false
                                }
                            },
                        innerTextModifier = Modifier
                            .widthIn(max = 60.dp)
                            .height(24.dp),
                        text = TextFieldValue(
                            text = budgetCategoryItem.budget,
                            selection = TextRange(budgetCategoryItem.budget.length)
                        ),
                        onClickAction = {},
                        onTextChanged = { text: TextFieldValue ->
                            budgetViewModel.setEvent(
                                BudgetContract.Event.OnFetchBudgetCategoryItem(
                                    budgetCategoryItem.copy(budget = text.text)
                                )
                            )
                        },
                        textSize = 16,
                        isShowUnit = false,
                        keyboardActions = keyboardAction,
                        keyboardOptions = keyboardOptions
                    )
                }
                Text(
                    text = " " + stringResource(id = R.string.money_unit),
                    style = ZzanZTypo.current.Body02.copy(
                        fontWeight = FontWeight.SemiBold
                    ),
                    color = ZzanZColorPalette.current.Gray08
                )
            }
        }
        Spacer(modifier = Modifier.weight(1f))
        DeleteCategory {
            budgetViewModel.setEvent(
                BudgetContract.Event.OnFetchBudgetCategoryItem(
                    budgetCategoryItem.copy(
                        budget = "", isChecked = false
                    )
                )
            )
        }
    }
}

@Composable
fun DeleteCategory(
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .height(56.dp)
            .clickable { onClick() },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            modifier = Modifier
                .padding(0.dp)
                .height(56.dp)
                .padding(vertical = 17.dp),
            textAlign = TextAlign.Center,
            text = stringResource(id = R.string.delete),
            color = ZzanZColorPalette.current.Red04,
            style = ZzanZTypo.current.Body03.copy(fontWeight = FontWeight.Medium)
        )
    }

    Spacer(modifier = Modifier.width(24.dp))

}

@Composable
fun AddBudgetByCategoryItemBtn(onAddClicked: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .padding()
            .background(color = Color.Transparent)
            .padding(horizontal = 24.dp)
            .clickable { onAddClicked() }, verticalAlignment = Alignment.CenterVertically
    ) {
        PlusIcon()
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = stringResource(id = R.string.add_category),
            style = ZzanZTypo.current.Body02.copy(fontWeight = FontWeight.SemiBold),
            color = ZzanZColorPalette.current.Gray08
        )
    }
}

@Composable
fun NestEggExplainText(
    prefix: String, suffix: String, amount: String
) {
    Text(
        buildAnnotatedString {
            withStyle(SpanStyle(color = ZzanZColorPalette.current.Gray06)) {
                append(prefix)
            }
            withStyle(SpanStyle(color = ZzanZColorPalette.current.Green04)) {
                append(stringResource(R.string.money_krw, amount))
            }
            withStyle(SpanStyle(color = ZzanZColorPalette.current.Gray06)) {
                append(suffix)
            }
        }, style = ZzanZTypo.current.Body02
    )
}

@Preview
@Composable
fun BudgetByCategoryPreview() {
    BudgetByCategory(titleText = "Text", onAddCategoryClicked = {}, modifier = Modifier)
}