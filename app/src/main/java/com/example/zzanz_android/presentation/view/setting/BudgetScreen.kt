package com.example.zzanz_android.presentation.view.setting

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.zzanz_android.R
import com.example.zzanz_android.common.ui.theme.ZzanZColorPalette
import com.example.zzanz_android.common.ui.theme.ZzanZTypo
import com.example.zzanz_android.presentation.view.component.BudgetTextField
import com.example.zzanz_android.presentation.view.component.TitleText

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SetBudget(titleText: String, onButtonChange: (String) -> Unit) {
    val windowInfo = LocalWindowInfo.current
    val focusRequester = remember {
        FocusRequester()
    }
    val budgetState = remember {
        mutableStateOf("")
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
    ) {
        TitleText(
            modifier = Modifier, text = titleText
        )
        Text(
            modifier = Modifier,
            text = stringResource(id = R.string.next_week_budget_explain),
            style = ZzanZTypo.current.Body01,
            color = ZzanZColorPalette.current.Gray06
        )
        Spacer(modifier = Modifier.height(24.dp))
        BudgetTextField(
            textState = budgetState,
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(focusRequester),
            fontSize = 18,
            textHolder = stringResource(id = R.string.budget_example),
            onTextChange = { text: String ->
                Log.d("Budget", text)
                onButtonChange(text)
            },
            keyboardType = KeyboardType.Number,
            won = stringResource(id = R.string.money_unit)
        )

        LaunchedEffect(windowInfo) {
            snapshotFlow { windowInfo.isWindowFocused }.collect { isWindowFocused ->
                if (isWindowFocused) {
                    focusRequester.requestFocus()
                }
            }
        }
    }
}

@Preview
@Composable
fun SetBudgetPreview() {
    SetBudget(titleText = "Text") {}
}