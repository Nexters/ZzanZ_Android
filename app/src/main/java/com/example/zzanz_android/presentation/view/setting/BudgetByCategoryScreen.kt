package com.example.zzanz_android.presentation.view.setting

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.zzanz_android.R
import com.example.zzanz_android.common.ui.theme.ZzanZColorPalette
import com.example.zzanz_android.common.ui.theme.ZzanZTypo
import com.example.zzanz_android.domain.model.BudgetCategoryData
import com.example.zzanz_android.domain.model.BudgetCategoryModel
import com.example.zzanz_android.domain.model.Category
import com.example.zzanz_android.presentation.view.component.BudgetTextField
import com.example.zzanz_android.presentation.view.component.CategoryIcon
import com.example.zzanz_android.presentation.view.component.InfoIcon
import com.example.zzanz_android.presentation.view.component.PlusIcon
import com.example.zzanz_android.presentation.view.component.TitleText

@Composable
fun BudgetByCategory(
    titleText: String,
    budgetCategoryData: MutableState<List<BudgetCategoryModel>>, onAddClicked: () -> Unit
) {
    val windowInfo = LocalWindowInfo.current
    val focusRequester = remember {
        FocusRequester()
    }

    val textState = remember {
        mutableStateOf("")
    }
    LaunchedEffect(key1 = budgetCategoryData, block = {})

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(max = 560.dp)
            .background(ZzanZColorPalette.current.White)
    ) {
        // TODO - TextField 안써지는 이슈 해결하기
        LazyColumn {
            item {
                TitleText(
                    modifier = Modifier.padding(horizontal = 24.dp),
                    text = titleText
                )
                Spacer(modifier = Modifier.height(28.dp))
                ExplainTotalBudget(totalBudget = 10000)
            }
            items(budgetCategoryData.value.size) { idx ->
                val item = budgetCategoryData.value[idx]
                if (item.isChecked && item.categoryId != Category.NESTEGG) {
                    BudgetByCategoryItem(
                        budgetCategoryData = budgetCategoryData,
                        budgetCategoryItem = item,
                        textState = textState,
                        modifier = Modifier.focusRequester(focusRequester)
                    )
                }
                if (item.categoryId == Category.NESTEGG) {
                    BudgetByCategoryItem(
                        budgetCategoryData = budgetCategoryData,
                        budgetCategoryItem = item,
                        textState = textState,
                        modifier = Modifier.focusRequester(focusRequester)
                    )
                }
            }
        }
        AddBudgetByCategoryItemBtn(onAddClicked = onAddClicked)
        Spacer(modifier = Modifier.height(12.dp))
    }

//    LaunchedEffect(windowInfo) {
//        snapshotFlow { windowInfo.isWindowFocused }.collect { isWindowFocused ->
//            if (isWindowFocused) {
//                focusRequester.requestFocus()
//            }
//        }
//    }
}

@Composable
fun ExplainTotalBudget(totalBudget: Int) {
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
            text = stringResource(id = R.string.total_budget_title_1) + " ",
            style = ZzanZTypo.current.Body03,
            color = ZzanZColorPalette.current.Gray08
        )
        Text(
            text = stringResource(id = R.string.money_krw, totalBudget),
            style = ZzanZTypo.current.Body03.copy(fontWeight = FontWeight.Bold),
            color = ZzanZColorPalette.current.Gray08
        )
        Text(
            text = stringResource(id = R.string.total_budget_title_2),
            style = ZzanZTypo.current.Body03,
            color = ZzanZColorPalette.current.Gray08
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BudgetByCategoryItem(
    budgetCategoryData: MutableState<List<BudgetCategoryModel>>,
    budgetCategoryItem: BudgetCategoryModel,
    textState: MutableState<String>,
    modifier: Modifier,
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
        CategoryIcon(budgetCategoryItem.categoryImage)
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
                        text = budgetCategoryItem.budget.toString(),
                        style = ZzanZTypo.current.Body01.copy(
                            fontWeight = FontWeight.SemiBold
                        ),
                        color = ZzanZColorPalette.current.Gray03
                    )
                } else {
                    BudgetTextField(
                        textState = textState,
                        modifier = Modifier
                            .width(54.dp)
                            .height(28.dp),
                        strExplain = "10,000",
                        onTextChange = { text: String ->
                            Log.d("BudgetByCategory", text)
                            budgetCategoryData.value = budgetCategoryData.value.map {
                                if (it.name == budgetCategoryItem.name) {
                                    it.copy(budget = text.toInt())
                                } else it
                            }
                        },
                        keyboardType = KeyboardType.Number,
                        won = ""
                    )
                }
                Text(
                    text = " " + stringResource(id = R.string.money_unit),
                    style = ZzanZTypo.current.Body02.copy(
                        fontWeight = FontWeight.SemiBold
                    ),
                    color = ZzanZColorPalette.current.Gray05
                )
            }
        }
        Spacer(modifier = Modifier.weight(1f))
        DeleteCategory {
            budgetCategoryData.value = budgetCategoryData.value.map {
                if (it.name == budgetCategoryItem.name) {
                    it.copy(isChecked = false)
                } else it
            }
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
    prefix: String,
    suffix: String,
    amount: String,
    amountColor: Color
) {
    Text(
        buildAnnotatedString {
            withStyle(SpanStyle(color = ZzanZColorPalette.current.Gray06)) {
                append(prefix)
            }
            withStyle(SpanStyle(color = ZzanZColorPalette.current.Green04)) {
                append("${stringResource(R.string.money_krw, amount)}")
            }
            withStyle(SpanStyle(color = ZzanZColorPalette.current.Gray06)) {
                append(suffix)
            }
        },
        style = ZzanZTypo.current.Body02
    )
}

@Preview
@Composable
fun BudgetByCategoryPreview() {
    BudgetByCategory(
        titleText = "Text",
        budgetCategoryData = remember {
            mutableStateOf(BudgetCategoryData.category)
        }, onAddClicked = {})
}