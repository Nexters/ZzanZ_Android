package com.example.zzanz_android.presentation.view.spending

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.zzanz_android.R
import com.example.zzanz_android.common.ui.theme.ZzanZColorPalette
import com.example.zzanz_android.common.ui.theme.ZzanZDimen
import com.example.zzanz_android.common.ui.theme.ZzanZTypo
import com.example.zzanz_android.presentation.view.component.AppBarWithBackNavigation
import com.example.zzanz_android.presentation.view.component.GreenRoundButton
import com.example.zzanz_android.presentation.view.component.InformationComponent
import com.example.zzanz_android.presentation.view.component.MoneyInputTextField
import com.example.zzanz_android.presentation.view.component.PlainInputTextField

@Composable
fun AddSpendingScreen() {
    val title = remember { mutableStateOf(TextFieldValue("")) }
    val amount = remember { mutableStateOf(TextFieldValue("")) }
    val diffAmount = "10,000"
    val isOver = true
    val category = "식비"
    val memo = remember { mutableStateOf(TextFieldValue("")) }
    val btnEnabled = true

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
                titleValue = title.value,
                onTitleChanged = { newText -> title.value = newText },
                amountValue = amount.value,
                diffAmount = diffAmount,
                isOver = isOver,
                category = category,
                onAmountChanged = { newText -> amount.value = newText },
                memoValue = memo.value,
                onMemoChanged = { newText -> memo.value = newText }
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
    titleValue: TextFieldValue,
    onTitleChanged: (TextFieldValue) -> Unit,
    amountValue: TextFieldValue,
    diffAmount: String,
    isOver: Boolean,
    category: String,
    onAmountChanged: (TextFieldValue) -> Unit,
    memoValue: TextFieldValue,
    onMemoChanged: (TextFieldValue) -> Unit
) {
    Column(
        modifier = modifier
            .padding(horizontal = ZzanZDimen.current.defaultHorizontal)
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        TitleText(Modifier.padding(top = 8.dp, bottom = 4.dp))
        SpendingTitle(title = titleValue, onTextChanged = onTitleChanged)
        SpendingAmount(
            amount = amountValue,
            diffAmount = diffAmount,
            isOver = isOver,
            category = category,
            onTextChanged = onAmountChanged
        )
        SpendingMemo(memo = memoValue, onMemoChanged = onMemoChanged)
    }
}

@Composable
fun SpendingTitle(title: TextFieldValue, onTextChanged: (TextFieldValue) -> Unit) {
    PlainInputTextField(
        text = title,
        hint = stringResource(id = R.string.spending_title_hint),
        onTextChanged = onTextChanged
    )
}

@Composable
fun SpendingAmount(
    amount: TextFieldValue,
    diffAmount: String,
    isOver: Boolean,
    category: String,
    onTextChanged: (TextFieldValue) -> Unit
) {
    val (infoMsgId, color) =
        if (isOver) Pair(R.string.spending_amount_over_message, ZzanZColorPalette.current.Red04)
        else Pair(R.string.spending_amount_remain_message, ZzanZColorPalette.current.Gray06)
    Column {
        MoneyInputTextField(
            text = amount,
            hint = stringResource(id = R.string.spending_amount_hint),
            onTextChanged = onTextChanged
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
fun SpendingMemo(memo: TextFieldValue, onMemoChanged: (TextFieldValue) -> Unit) {
    PlainInputTextField(
        text = memo,
        hint = stringResource(id = R.string.spending_memo_hint),
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
    AddSpendingScreen()
}