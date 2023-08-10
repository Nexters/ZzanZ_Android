package com.example.zzanz_android.presentation.view.spending

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.zzanz_android.R
import com.example.zzanz_android.common.navigation.ArgumentKey
import com.example.zzanz_android.common.ui.theme.ZzanZColorPalette
import com.example.zzanz_android.common.ui.theme.ZzanZDimen
import com.example.zzanz_android.common.ui.theme.ZzanZTypo
import com.example.zzanz_android.common.ui.util.keyboardAsState
import com.example.zzanz_android.domain.util.MoneyFormatter
import com.example.zzanz_android.presentation.view.component.AppBarWithBackNavigation
import com.example.zzanz_android.presentation.view.component.BottomGreenButton
import com.example.zzanz_android.presentation.view.component.InformationComponent
import com.example.zzanz_android.presentation.view.component.MoneyInputTextField
import com.example.zzanz_android.presentation.view.component.PlainInputTextField
import com.example.zzanz_android.presentation.viewmodel.AddSpendingEvent
import com.example.zzanz_android.presentation.viewmodel.AddSpendingViewModel
import com.example.zzanz_android.presentation.viewmodel.STEP

@Composable
fun AddSpendingScreen(
    navController: NavController,
    viewModel: AddSpendingViewModel = hiltViewModel()
) {
    val context = LocalContext.current

    val focusManager = LocalFocusManager.current
    val isKeyboardOpen by keyboardAsState()

    val titleFocusRequester = remember { FocusRequester() }
    val amountFocusRequester = remember { FocusRequester() }
    val memoFocusRequester = remember { FocusRequester() }

    val title = remember { mutableStateOf(TextFieldValue("")) }
    val amount = remember { mutableStateOf(TextFieldValue("")) }
    val memo = remember { mutableStateOf(TextFieldValue("")) }

    val addSpendingState by viewModel.uiState.collectAsState()
    val currentStep by remember { mutableStateOf(addSpendingState.currentStep) }
    val diffAmount by remember { mutableStateOf(addSpendingState.diffAmountState?.diffAmount) }
    val isOver by remember { mutableStateOf(addSpendingState.diffAmountState?.isOver ?: false) }
    val category = remember {
        mutableStateOf(navController.currentBackStackEntry?.arguments?.getString(ArgumentKey.categoryName) ?: "")
    }
    val btnEnabled = remember { mutableStateOf(amount.value.text.isNotEmpty() && title.value.text.isNotEmpty()) }

    LaunchedEffect(currentStep) {
        when (currentStep) {
            STEP.AMOUNT -> amountFocusRequester.requestFocus()
            STEP.TITLE -> titleFocusRequester.requestFocus()
            else -> focusManager.clearFocus()
        }
    }

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
                onTitleChanged = { newText ->
                    title.value = newText
                    viewModel.setEvent(AddSpendingEvent.UpdateTitleValue(newText.text))
                },
                amountValue = amount.value,
                diffAmount = diffAmount?.let { MoneyFormatter.format(it) },
                isOver = isOver,
                category = category.value,
                onAmountChanged = { newText ->
                    amount.value = newText
                    viewModel.setEvent(
                        AddSpendingEvent.UpdateAmountValue(
                            MoneyFormatter.revert(
                                newText.text, context.getString(R.string.money_unit)
                            )
                        )
                    )
                },
                memoValue = memo.value,
                onMemoChanged = { newText ->
                    memo.value = newText
                    viewModel.setEvent(AddSpendingEvent.UpdateMemoValue(newText.text))
                },
                onClickAction = {
                    when (currentStep) {
                        STEP.AMOUNT -> {
                            if (amount.value.text.isEmpty()) {
                                focusManager.clearFocus()
                            } else {
                                viewModel.setEvent(AddSpendingEvent.OnClickNext)
                            }
                        }

                        STEP.TITLE -> {
                            if (title.value.text.isEmpty()) {
                                focusManager.clearFocus()
                            } else {
                                viewModel.setEvent(AddSpendingEvent.OnClickNext)
                            }
                        }

                        else -> {
                            focusManager.clearFocus()
                        }
                    }
                }
            )
            BottomGreenButton(
                buttonText = if (isKeyboardOpen) {
                    stringResource(id = R.string.next)
                } else {
                    stringResource(id = R.string.spending_done_btn_title)
                },
                onClick = { /*TODO*/ },
                isButtonEnabled = btnEnabled.value,
                isKeyboardOpen = isKeyboardOpen,
                horizontalWidth = if (isKeyboardOpen) 0.dp else ZzanZDimen.current.defaultHorizontal
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
    diffAmount: String?,
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
        if (currentStep.ordinal >= 1) {
            item {
                SpendingTitle(
                    title = titleValue,
                    onTextChanged = onTitleChanged,
                    onClickAction = onClickAction,
                    focusRequester = titleFocusRequester
                )
            }
        }
        if (currentStep.ordinal >= 0) {
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
        if (currentStep.ordinal >= 2) {
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
    diffAmount: String?,
    isOver: Boolean = false,
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
            modifier = Modifier
                .focusRequester(focusRequester)
                .fillMaxWidth()
                .height(56.dp),
            text = amount,
            onClickAction = onClickAction,
            onTextChanged = onTextChanged,
        )
        Spacer(modifier = Modifier.height(12.dp))

        diffAmount?.let {
            InformationComponent(
                iconColor = color,
                textColor = color,
                message = stringResource(id = infoMsgId, category, diffAmount)
            )
        }
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