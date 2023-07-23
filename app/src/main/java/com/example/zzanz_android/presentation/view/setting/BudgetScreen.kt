package com.example.zzanz_android.presentation.view.setting

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.zzanz_android.R
import com.example.zzanz_android.common.ui.theme.ZzanZColorPalette
import com.example.zzanz_android.common.ui.theme.ZzanZTypo
import com.example.zzanz_android.presentation.view.component.BudgetTextField
import com.example.zzanz_android.presentation.view.component.TitleText

@Composable
fun SetBudget() {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        TitleText(
            modifier = Modifier, text = stringResource(id = R.string.next_week_budget_title)
        )
        Text(
            modifier = Modifier,
            text = stringResource(id = R.string.next_week_budget_explain),
            style = ZzanZTypo.current.Body01,
            color = ZzanZColorPalette.current.Gray06
        )
        Spacer(modifier = Modifier.height(24.dp))
        // TODO - TextFiled 손보기
        // TODO - 진입 시, textfield에 포커스 되도록
        // TODO - 1글자 이상 입력시 버튼 활성화
        BudgetTextField(
            modifier = Modifier.fillMaxWidth(1f),
            strExplain = stringResource(id = R.string.budget_example),
            onTextChange = { text: String -> Log.d("Budget", text) },
            keyboardType = KeyboardType.Number
        )
    }
}

@Preview
@Composable
fun SetBudgetPreview() {
    SetBudget()
}