package com.example.zzanz_android.presentation.view.setting

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.zzanz_android.R
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
        Spacer(modifier = Modifier.height(28.dp))
        // TODO - 카테고리 List 만들기
        // TODO - 카테고리 버튼 다시 클릭시 선택 해제 처리
        // TODO - 1개 이상 선택 시, 버튼 활성화 되도록

    }
}

@Preview
@Composable
fun BudgetCategoryPreview() {
    BudgetCategory()
}