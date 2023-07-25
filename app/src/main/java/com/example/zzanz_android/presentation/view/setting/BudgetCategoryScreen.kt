package com.example.zzanz_android.presentation.view.setting

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.zzanz_android.R
import com.example.zzanz_android.domain.model.BudgetCategoryData
import com.example.zzanz_android.presentation.view.component.CustomCategoryButton
import com.example.zzanz_android.presentation.view.component.TitleText

@Composable
fun BudgetCategory() {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        TitleText(
            modifier = Modifier,
            text = stringResource(id = R.string.next_week_budget_category)
        )
        Spacer(modifier = Modifier.height(18.dp))
        // TODO - 카테고리 버튼 다시 클릭시 선택 해제 처리
        // TODO - 1개 이상 선택 시, 버튼 활성화 되도록
        LazyVerticalGrid(
            columns = GridCells.Adaptive(150.dp),
            content = {
                items(BudgetCategoryData.category) { category ->
                    if (category.categoryName != "NESTEGG") {
                        CustomCategoryButton(
                            modifier = Modifier
                                .height(56.dp)
                                .padding(horizontal = 6.dp, vertical = 10.dp),
                            text = stringResource(id = category.name),
                            onClick = {})
                    }
                }
            })


    }
}

@Preview
@Composable
fun BudgetCategoryPreview() {
    BudgetCategory()
}