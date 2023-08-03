package com.example.zzanz_android.presentation.view.setting

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.zzanz_android.R
import com.example.zzanz_android.domain.model.BudgetCategoryData
import com.example.zzanz_android.domain.model.BudgetCategoryModel
import com.example.zzanz_android.presentation.view.component.CustomCategoryButton
import com.example.zzanz_android.presentation.view.component.TitleText

@Composable
fun BudgetCategory(
    textModifier: Modifier = Modifier,
    categoryModifier: Modifier = Modifier,
    @StringRes titleText: Int,
    budgetCategoryData: MutableState<List<BudgetCategoryModel>>
) {
    LaunchedEffect(key1 = budgetCategoryData, block = {})
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        TitleText(
            modifier = textModifier, text = stringResource(id = titleText)
        )
        Spacer(modifier = Modifier.height(18.dp))
        LaunchedEffect(key1 = budgetCategoryData, block = {})
        LazyVerticalGrid(
            modifier = categoryModifier, columns = GridCells.Fixed(2)
        ) {
            items(budgetCategoryData.value.size) { idx ->
                val item = budgetCategoryData.value[idx]
                if (item.name != R.string.category_nestegg) {
                    val categoryPaddingValues = PaddingValues(horizontal = 6.dp, vertical = 10.dp)
                    Box(modifier = Modifier.padding(categoryPaddingValues)) {
                        CustomCategoryButton(
                            modifier = Modifier,
                            text = stringResource(id = item.name),
                            onClick = {
                                budgetCategoryData.value = budgetCategoryData.value.map {
                                    if (it.name == item.name) {
                                        it.copy(isChecked = !item.isChecked)
                                    } else it
                                }

                            },
                            isChecked = item.isChecked
                        )
                    }
                }
            }
        }


    }
}

@Preview
@Composable
fun BudgetCategoryPreview() {
    BudgetCategory(
        textModifier = Modifier,
        categoryModifier = Modifier,
        titleText = R.string.next_week_budget_category,
        budgetCategoryData = remember {
            mutableStateOf(BudgetCategoryData.category)
        }
    )
}