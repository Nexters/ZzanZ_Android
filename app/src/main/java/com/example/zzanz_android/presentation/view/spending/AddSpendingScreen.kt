package com.example.zzanz_android.presentation.view.spending

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.zzanz_android.R
import com.example.zzanz_android.common.ui.theme.ZzanZColorPalette
import com.example.zzanz_android.common.ui.theme.ZzanZDimen
import com.example.zzanz_android.common.ui.theme.ZzanZTypo
import com.example.zzanz_android.presentation.view.component.AppBarWithBackNavigation
import com.example.zzanz_android.presentation.view.component.GreenRoundButton
import com.example.zzanz_android.presentation.view.component.InformationComponent
import com.example.zzanz_android.presentation.view.component.MoneyInputTextField
import com.example.zzanz_android.presentation.view.component.PlainInputTextField

enum class STEP {
    AMOUNT, TITLE, MEMO, DONE
}

@Composable
fun AddSpendingScreen(navController: NavController) {
    val focusManager = LocalFocusManager.current
    val titleFocusRequester = remember { FocusRequester() }
    val amountFocusRequester = remember { FocusRequester() }
    val memoFocusRequester = remember { FocusRequester() }
    var currentStep by remember { mutableStateOf(STEP.AMOUNT) }

    LaunchedEffect(currentStep){
        when(currentStep){
            STEP.AMOUNT -> amountFocusRequester.requestFocus()
            STEP.TITLE -> titleFocusRequester.requestFocus()
            else -> focusManager.clearFocus()
        }
    }

    val title = remember { mutableStateOf(TextFieldValue("")) }
    val amount = remember { mutableStateOf(TextFieldValue("")) }
    val diffAmount = "10,000"
    val isOver = true
    val category = "식비"
    val memo = remember { mutableStateOf(TextFieldValue("")) }
    val btnEnabled = amount.value.text.isNotEmpty() && title.value.text.isNotEmpty()

    Scaffold(topBar = {
        AppBarWithBackNavigation()
    }) {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()

        ) {
            AddSpendingContent(
                modifier = Modifier.weight(1f),
                currentStep = currentStep,
                titleFocusRequester = titleFocusRequester,
                amountFocusRequester = amountFocusRequester,
                memoFocusRequester = memoFocusRequester,
                titleValue = title.value,
                onTitleChanged = { newText -> title.value = newText },
                amountValue = amount.value,
                diffAmount = diffAmount,
                isOver = isOver,
                category = category,
                onAmountChanged = { newText -> amount.value = newText },
                memoValue = memo.value,
                onMemoChanged = { newText -> memo.value = newText },
                onClickAction = {
                    when(currentStep){
                        STEP.AMOUNT -> {
                            if(amount.value.text.isEmpty()){
                                focusManager.clearFocus()
                            }else{
                                currentStep = STEP.TITLE
                            }
                        }
                        STEP.TITLE -> {
                            if(title.value.text.isEmpty()){
                                focusManager.clearFocus()
                            }else{
                                currentStep = STEP.MEMO
                            }
                        }
                        else -> {
                            focusManager.clearFocus()
                        }
                    }
                }
            )
            GreenRoundButton(
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 25.dp),
                text = stringResource(id = R.string.spending_done_btn_title),
                onClick = { /*TODO*/ },
                enabled = btnEnabled
            )
        }
    }
}

@Composable
fun AddSpendingContent(
    modifier: Modifier = Modifier,
    currentStep: STEP,
    titleFocusRequester: FocusRequester,
    amountFocusRequester: FocusRequester,
    memoFocusRequester: FocusRequester,
    titleValue: TextFieldValue,
    onTitleChanged: (TextFieldValue) -> Unit,
    amountValue: TextFieldValue,
    diffAmount: String,
    isOver: Boolean,
    category: String,
    onAmountChanged: (TextFieldValue) -> Unit,
    memoValue: TextFieldValue,
    onMemoChanged: (TextFieldValue) -> Unit,
    onClickAction: () -> Unit
) {
    LazyColumn(
        modifier = modifier
            .padding(horizontal = ZzanZDimen.current.defaultHorizontal)
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        item { TitleText(Modifier.padding(top = 8.dp, bottom = 4.dp)) }
        if(currentStep.ordinal >= 1){
            item {
                SpendingTitle(
                    title = titleValue,
                    onTextChanged = onTitleChanged,
                    onClickAction = onClickAction,
                    focusRequester = titleFocusRequester
                )
            }
        }
        if(currentStep.ordinal >= 0){
            item {
                SpendingAmount(
                    amount = amountValue,
                    diffAmount = diffAmount,
                    isOver = isOver,
                    category = category,
                    onTextChanged = onAmountChanged,
                    onClickAction = onClickAction,
                    focusRequester = amountFocusRequester
                )
            }
        }
        if(currentStep.ordinal >= 2){
            item {
                SpendingMemo(
                    memo = memoValue,
                    onMemoChanged = onMemoChanged,
                    onClickAction = onClickAction,
                    focusRequester = memoFocusRequester
                )
            }
        }
    }
}

@Composable
fun SpendingTitle(
    title: TextFieldValue,
    onTextChanged: (TextFieldValue) -> Unit,
    onClickAction: () -> Unit,
    focusRequester: FocusRequester
) {
    PlainInputTextField(
        modifier = Modifier.focusRequester(focusRequester),
        text = title,
        hint = stringResource(id = R.string.spending_title_hint),
        onClickAction = onClickAction,
        onTextChanged = onTextChanged,

    )
}

@Composable
fun SpendingAmount(
    amount: TextFieldValue,
    diffAmount: String,
    isOver: Boolean,
    category: String,
    onTextChanged: (TextFieldValue) -> Unit,
    onClickAction: () -> Unit,
    focusRequester: FocusRequester
) {
    val (infoMsgId, color) =
        if (isOver) Pair(R.string.spending_amount_over_message, ZzanZColorPalette.current.Red04)
        else Pair(R.string.spending_amount_remain_message, ZzanZColorPalette.current.Gray06)
    Column {
        MoneyInputTextField(
            modifier = Modifier.focusRequester(focusRequester),
            text = amount,
            hint = stringResource(id = R.string.spending_amount_hint),
            onClickAction = onClickAction,
            onTextChanged = onTextChanged,
        )
        Spacer(modifier = Modifier.height(12.dp))
        InformationComponent(
            iconColor = color,
            textColor = color,
            message = stringResource(id = infoMsgId, category, diffAmount)
        )
    }
}

@Composable
fun SpendingMemo(
    memo: TextFieldValue,
    onMemoChanged: (TextFieldValue) -> Unit,
    onClickAction: () -> Unit,
    focusRequester: FocusRequester
) {
    PlainInputTextField(
        modifier = Modifier.focusRequester(focusRequester),
        text = memo,
        hint = stringResource(id = R.string.spending_memo_hint),
        onClickAction = onClickAction,
        onTextChanged = onMemoChanged
    )
}

@Composable
fun TitleText(modifier: Modifier = Modifier) {
    Text(
        modifier = modifier,
        text = stringResource(id = R.string.spending_title),
        style = ZzanZTypo.current.H1,
        color = ZzanZColorPalette.current.Gray09
    )
}

@Preview
@Composable
fun AddSpendingPreview() {
    AddSpendingScreen(rememberNavController())
}