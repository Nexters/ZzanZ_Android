package com.zzanz.swip_android.presentation.view.setting

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.zzanz.swip_android.R
import com.zzanz.swip_android.common.navigation.SettingType
import com.zzanz.swip_android.common.ui.theme.ZzanZColorPalette
import com.zzanz.swip_android.common.ui.theme.ZzanZTypo
import com.zzanz.swip_android.presentation.view.component.contract.BudgetContract
import com.zzanz.swip_android.presentation.view.component.MoneyInputTextField
import com.zzanz.swip_android.presentation.view.component.TitleText
import com.zzanz.swip_android.presentation.viewmodel.BudgetViewModel


@Composable
fun SetBudget(
    budgetViewModel: BudgetViewModel = hiltViewModel(),
    titleText: String,
) {
    val windowInfo = LocalWindowInfo.current
    val focusRequester = remember {
        FocusRequester()
    }
    val budget = budgetViewModel.budgetData.collectAsState().value.totalBudget.value
    val buttonState = budgetViewModel.uiState.collectAsState().value.buttonState.value
    val settingType = budgetViewModel.settingType.collectAsState().value

    Column(
        modifier = Modifier
            .background(ZzanZColorPalette.current.White)
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
    ) {
        TitleText(
            modifier = Modifier, text = titleText
        )
        if (settingType == SettingType.onBoarding) {
            Text(
                modifier = Modifier,
                text = stringResource(id = R.string.next_week_budget_explain),
                style = ZzanZTypo.current.Body01,
                color = ZzanZColorPalette.current.Gray06
            )
        }
        Spacer(modifier = Modifier.height(24.dp))

        MoneyInputTextField(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .focusRequester(focusRequester),
            text = TextFieldValue(budget, selection = TextRange(budget.length)),
            onClickAction = {
                if (buttonState) {
                    budgetViewModel.setEvent(
                        BudgetContract.Event.OnNextButtonClicked
                    )
                }
            },
            onTextChanged = { text: TextFieldValue ->
                budgetViewModel.setEvent(
                    BudgetContract.Event.OnFetchBudget(text.text)
                )
            },
            textSize = 18
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
    SetBudget(titleText = "Text")
}