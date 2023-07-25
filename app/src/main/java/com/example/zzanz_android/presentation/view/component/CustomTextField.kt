package com.example.zzanz_android.presentation.view.component

import android.util.Log
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import com.example.zzanz_android.common.ui.theme.ZzanZColorPalette
import com.example.zzanz_android.common.ui.theme.ZzanZTypo


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BudgetTextField(
    modifier: Modifier,
    strExplain: String = "",
    onTextChange: (String) -> Unit,
    keyboardType: KeyboardType
) {
    var text by remember { mutableStateOf("") }
    TextField(
        value = text,
        onValueChange = {
            text = it
            onTextChange(it)
        },
        maxLines = 1,
        placeholder = {
            Text(
                text = strExplain, style = ZzanZTypo.current.Headline
            )
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType
        ),
        modifier = modifier,
        textStyle = ZzanZTypo.current.Headline,
        colors = TextFieldDefaults.textFieldColors(
            textColor = ZzanZColorPalette.current.Gray09,
            disabledTextColor = ZzanZColorPalette.current.Gray03,
            containerColor = ZzanZColorPalette.current.White,
            cursorColor = ZzanZColorPalette.current.Green04,
            placeholderColor = ZzanZColorPalette.current.Gray03
        )
    )
}

@Preview
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditPreview() {
    BudgetTextField(
        modifier = Modifier,
        strExplain = "Test",
        { text: String -> Log.d("budgetTextField", text) },
        keyboardType = KeyboardType.Number
    )
}