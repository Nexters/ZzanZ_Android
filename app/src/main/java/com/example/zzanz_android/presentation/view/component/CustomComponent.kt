package com.example.zzanz_android.presentation.view.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.zzanz_android.R
import com.example.zzanz_android.common.ui.theme.ZzanZColorPalette
import com.example.zzanz_android.common.ui.theme.ZzanZTypo
import com.example.zzanz_android.common.ui.util.dpToPx
import com.example.zzanz_android.presentation.view.component.util.MoneyCommaVisualTransformation

@Composable
fun PlainInputTextField(
    text: TextFieldValue,
    hint: String,
    onTextChanged: (TextFieldValue) -> Unit
){
    BasicTextField(
        value = text,
        onValueChange = onTextChanged,
        textStyle = ZzanZTypo.current.SubHeading,
        singleLine = true,
        decorationBox = { innerTextField ->
            InputTextFieldBox(
                color = ZzanZColorPalette.current.Green04,
                borderSize = 1.dp.dpToPx()
            ) {
                if (text.text.isEmpty()) {
                    Text(
                        text = hint,
                        color = ZzanZColorPalette.current.Gray03,
                        style = ZzanZTypo.current.SubHeading,
                    )
                }
                innerTextField()
            }
        }
    )
}
@Composable
fun MoneyInputTextField(
    text: TextFieldValue,
    hint: String,
    onTextChanged: (TextFieldValue) -> Unit
) {
    BasicTextField(
        value = text,
        onValueChange = onTextChanged,
        textStyle = ZzanZTypo.current.SubHeading,
        singleLine = true,
        visualTransformation = MoneyCommaVisualTransformation(stringResource(id = R.string.money_unit)),
        decorationBox = { innerTextField ->
            InputTextFieldBox(
                color = ZzanZColorPalette.current.Green04,
                borderSize = 1.dp.dpToPx()
            ) {
                if (text.text.isEmpty()) {
                    Text(
                        buildAnnotatedString {
                            withStyle(SpanStyle(color = ZzanZColorPalette.current.Gray03)) {
                                append(hint)
                            }
                            withStyle(SpanStyle(color = ZzanZColorPalette.current.Gray09)) {
                                append(" " + stringResource(id = R.string.money_unit))
                            }
                        },
                        style = ZzanZTypo.current.SubHeading,
                    )
                }
                innerTextField()
            }
        }
    )
}

@Composable
fun InputTextFieldBox(
    color: Color,
    borderSize: Float,
    content: @Composable () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .drawBehind {
                drawLine(
                    color = color,
                    start = Offset(0f, size.height),
                    end = Offset(size.width, size.height),
                    strokeWidth = borderSize
                )
            }
            .padding(horizontal = 8.dp)
    ) {
        Box(modifier = Modifier.align(Alignment.CenterStart)) {
            content()
        }
    }
}

@Composable
fun InformationComponent(
    iconColor: Color,
    textColor: Color,
    message: String
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        InfoICon(color = iconColor)
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = message, style = ZzanZTypo.current.Body02, color = textColor)
    }
}

@Composable
fun AddSpendingComponent() {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        Row(
            modifier = Modifier.padding(vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AddIcon()
            Spacer(modifier = Modifier.width(16.dp))
            TitleText(stringResource(id = R.string.category_add_spending_btn_title))
        }
    }
}

@Composable
fun SpendingItemComponent(title: String, memo: String, amount: String) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        Row(
            modifier = Modifier.padding(vertical = 17.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AddIcon() // TODO : gowoon - 임시 아무 아이콘
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                TitleText(title = title)
                MemoText(memo = memo)
            }
            AmountText(amount = amount)
        }
    }
}

@Composable
fun TitleText(title: String) {
    Text(text = title, style = ZzanZTypo.current.Body01, color = ZzanZColorPalette.current.Gray08)
}

@Composable
fun MemoText(memo: String) {
    Text(text = memo, style = ZzanZTypo.current.Body02, color = ZzanZColorPalette.current.Gray05)
}

@Composable
fun AmountText(amount: String) {
    Text(text = amount, style = ZzanZTypo.current.Body01, color = ZzanZColorPalette.current.Gray08)
}

@Preview
@Composable
fun ComponentPreview() {
    val moneyState = remember {
        mutableStateOf(TextFieldValue(""))
    }
    val textState = remember {
        mutableStateOf(TextFieldValue(""))
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(horizontal = 24.dp)
    ) {
        MoneyInputTextField(text = moneyState.value, hint = "10,000", onTextChanged = {
            moneyState.value = it
        })
        PlainInputTextField(text = textState.value, hint = "hint", onTextChanged = {
            textState.value = it
        })
    }
}