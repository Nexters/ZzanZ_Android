package com.example.zzanz_android.presentation.view.setting

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.zzanz_android.R
import com.example.zzanz_android.domain.model.Category
import com.example.zzanz_android.presentation.contract.BudgetContract
import com.example.zzanz_android.presentation.view.component.CustomCategoryButton
import com.example.zzanz_android.presentation.view.component.TitleText
import com.example.zzanz_android.presentation.viewmodel.BudgetViewModel

@Composable
fun BudgetCategory(
    budgetViewModel: BudgetViewModel = hiltViewModel(),
    @SuppressLint("ModifierParameter") modifier: Modifier = Modifier,
    categoryModifier: Modifier = Modifier,
    titleText: String,
) {
    val budgetCategoryData = budgetViewModel.budgetData.collectAsState().value.category

    LaunchedEffect(key1 = budgetCategoryData, block = {})

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        TitleText(
            modifier = modifier, text = titleText
        )
        LazyVerticalGrid(
            modifier = categoryModifier, columns = GridCells.Fixed(2)
        ) {
            items(budgetCategoryData.value.size) { idx ->
                val item = budgetCategoryData.value[idx]
                if (item.categoryId != Category.NESTEGG) {
                    val categoryPaddingValues = PaddingValues(horizontal = 6.dp, vertical = 10.dp)
                    Box(modifier = Modifier.padding(categoryPaddingValues)) {
                        CustomCategoryButton(
                            modifier = Modifier,
                            text = stringResource(id = item.name),
                            onClick = {
                                budgetViewModel.setEvent(
                                    BudgetContract.Event.OnFetchBudgetCategoryItem(
                                        item.copy(
                                            isChecked = !item.isChecked
                                        )
                                    )
                                )
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
        modifier = Modifier,
        categoryModifier = Modifier,
        titleText = stringResource(id = R.string.next_week_budget_category)
    )
}