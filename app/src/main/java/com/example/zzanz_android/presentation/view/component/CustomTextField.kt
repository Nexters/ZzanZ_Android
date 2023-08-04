package com.example.zzanz_android.presentation.view.component

import android.util.Log
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.zzanz_android.common.ui.theme.ZzanZColorPalette
import com.example.zzanz_android.common.ui.theme.ZzanZTypo
import com.example.zzanz_android.presentation.view.component.util.MoneyCommaVisualTransformation
import timber.log.Timber


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BudgetTextField(
    textState: MutableState<String>,
    modifier: Modifier,
    fontSize: Int,
    textHolder: String = "",
    onTextChange: (String) -> Unit,
    keyboardType: KeyboardType,
    won: String
) {

    val focusManager = LocalFocusManager.current
    val keyboardAction = KeyboardActions {
        focusManager.moveFocus(FocusDirection.Next)
    }

    TextField(
        value = textState.value,
        onValueChange = {
            textState.value = it
            Log.d("### BudgetTextField", it)
            onTextChange(it)
        },
        visualTransformation = if (textState.value.isEmpty()) VisualTransformation.None
        else MoneyCommaVisualTransformation(won),
        maxLines = 1,
        singleLine = true,
        placeholder = {
            Text(
                text = textHolder,
                style = ZzanZTypo.current.Heading.copy(fontSize = fontSize.sp),
                color = ZzanZColorPalette.current.Gray03
            )
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType
        ),
        modifier = modifier,
        textStyle = ZzanZTypo.current.Heading.copy(fontSize = fontSize.sp),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = ZzanZColorPalette.current.White,
            focusedTextColor = ZzanZColorPalette.current.Gray09,
            cursorColor = ZzanZColorPalette.current.Green04,
            disabledTextColor = ZzanZColorPalette.current.Gray03,
            unfocusedContainerColor = ZzanZColorPalette.current.White,
            unfocusedIndicatorColor = ZzanZColorPalette.current.Gray03,
            focusedIndicatorColor = ZzanZColorPalette.current.Green04,
        ),
        keyboardActions = keyboardAction
    )
}

@Preview
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditPreview() {
    val textState = remember {
        mutableStateOf("")
    }
    BudgetTextField(
        textState = textState,
        modifier = Modifier,
        fontSize = 100,
        textHolder = "Test",
        { text: String -> Log.d("budgetTextField", text) },
        keyboardType = KeyboardType.Number,
        "원"
    )
}