package com.example.zzanz_android.presentation.view.setting

import android.util.Log
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.zzanz_android.R
import com.example.zzanz_android.domain.model.BudgetCategoryData
import com.example.zzanz_android.presentation.view.component.CustomCategoryButton
import com.example.zzanz_android.presentation.view.component.TitleText

@Composable
fun BudgetCategory(
    textModifier: Modifier = Modifier,
    categoryModifier: Modifier = Modifier,
    @StringRes titleText: Int,
    onAddClicked: () -> Unit
) {
    val budgetCategoryData = remember {
        mutableStateOf(BudgetCategoryData.category)
    }
    LaunchedEffect(key1 = budgetCategoryData, block = {})
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        TitleText(
            modifier = textModifier, text = stringResource(id = titleText)
        )
        Spacer(modifier = Modifier.height(18.dp))
        // TODO - 카테고리 버튼 다시 클릭시 선택 해제 처리
        // TODO - 1개 이상 선택 시, 버튼 활성화 되도록
        LazyVerticalGrid(
            modifier = categoryModifier, columns = GridCells.Fixed(2)
        ) {
            items(budgetCategoryData.value.size) { idx ->
                val item = budgetCategoryData.value[idx]
                if (item.name != R.string.category_nestegg) {
                    CustomCategoryButton(
                        modifier = Modifier
                            .height(56.dp)
                            .padding(horizontal = 6.dp, vertical = 10.dp),
                        text = stringResource(id = item.name),
                        onClick = {
                            Log.d(
                                "### BudgetCategory", "clicked ! item ${item.categoryName}"
                            )
                            // TODO isChecked 값 으로 budgetCateogryData 버튼 색상 수정되도록 변경

                        },
                        isChecked = item.isChecked
                    )
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
        titleText = R.string.next_week_budget_category
    ) {

    }
}