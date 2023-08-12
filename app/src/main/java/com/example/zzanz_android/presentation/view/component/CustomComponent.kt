package com.example.zzanz_android.presentation.view.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.zzanz_android.R
import com.example.zzanz_android.common.ui.theme.ZzanZColorPalette
import com.example.zzanz_android.common.ui.theme.ZzanZTypo
import com.example.zzanz_android.common.ui.util.dpToPx
import com.example.zzanz_android.domain.model.Category
import com.example.zzanz_android.domain.model.ChallengeStatus
import com.example.zzanz_android.domain.util.MoneyFormatter
import com.example.zzanz_android.presentation.view.component.util.MoneyCommaVisualTransformation
import java.lang.Float.min

@Composable
fun ProgressIndicator(
    modifier: Modifier = Modifier, color: Color, ratio: Float
) {
    LinearProgressIndicator(
        modifier = modifier,
        trackColor = ZzanZColorPalette.current.Gray03,
        color = color,
        progress = ratio
    )
}

@Composable
fun PlainInputTextField(
    modifier: Modifier = Modifier,
    innerTextModifier: Modifier = Modifier,
    text: TextFieldValue,
    hint: String,
    onClickAction: () -> Unit,
    onTextChanged: (TextFieldValue) -> Unit
) {
    val colorSet = ZzanZColorPalette.current
    var color = remember { mutableStateOf(colorSet.Gray03) }
    BasicTextField(modifier = modifier.onFocusChanged {
        color.value = if (it.isFocused) colorSet.Green04 else colorSet.Gray03
    },
        value = text,
        onValueChange = onTextChanged,
        textStyle = ZzanZTypo.current.SubHeading,
        singleLine = true,
        keyboardActions = KeyboardActions(onDone = { onClickAction() }),
        decorationBox = { innerTextField ->
            InputTextFieldBox(
                modifier = innerTextModifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .padding(8.dp), color = color.value, borderSize = 1.dp.dpToPx()
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
        })
}

@Composable
fun MoneyInputTextField(
    modifier: Modifier = Modifier,
    innerTextModifier: Modifier = Modifier,
    text: TextFieldValue,
    onClickAction: () -> Unit,
    onTextChanged: (TextFieldValue) -> Unit,
    textSize: Int = 18,
    isShowUnit: Boolean = true,
    keyboardOptions: KeyboardOptions = KeyboardOptions(
        keyboardType = KeyboardType.Number,
        imeAction = ImeAction.Done
    ),
    keyboardActions: KeyboardActions = KeyboardActions(onDone = { onClickAction() })
) {
    val colorSet = ZzanZColorPalette.current
    var color = remember { mutableStateOf(colorSet.Gray03) }
    BasicTextField(modifier = modifier.onFocusChanged {
        color.value = if (it.isFocused) colorSet.Green04 else colorSet.Gray03
    },
        value = text,
        onValueChange = onTextChanged,
        textStyle = ZzanZTypo.current.SubHeading.copy(fontSize = textSize.sp),
        singleLine = true,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        visualTransformation = MoneyCommaVisualTransformation(
            stringResource(id = R.string.money_unit),
            isShowUnit
        ),
        cursorBrush = SolidColor(ZzanZColorPalette.current.Green04),
        decorationBox = { innerTextField ->
            InputTextFieldBox(
                modifier = innerTextModifier, color = color.value, borderSize = 1.dp.dpToPx()
            ) {
                if (text.text.isEmpty()) {
                    Text(
                        buildAnnotatedString {
                            withStyle(SpanStyle(color = ZzanZColorPalette.current.Gray03)) {
                                append(MoneyFormatter.format(stringResource(id = R.string.spending_amount_hint)))
                            }
                            if (isShowUnit) {
                                withStyle(SpanStyle(color = ZzanZColorPalette.current.Gray09)) {
                                    append(" " + stringResource(id = R.string.money_unit))
                                }
                            }
                        },
                        style = ZzanZTypo.current.SubHeading.copy(fontSize = textSize.sp),
                    )
                }
                innerTextField()
            }
        })
}

@Composable
fun InputTextFieldBox(
    modifier: Modifier = Modifier,
    color: Color,
    borderSize: Float,
    content: @Composable () -> Unit
) {
    Box(modifier = modifier.drawBehind {
        drawLine(
            color = color,
            start = Offset(0f, size.height),
            end = Offset(size.width, size.height),
            strokeWidth = borderSize
        )
    }) {
        Box(modifier = Modifier.align(Alignment.CenterStart)) {
            content()
        }
    }
}

@Composable
fun InformationComponent(
    iconColor: Color, textColor: Color, message: String
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        InfoIcon(color = iconColor)
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = message, style = ZzanZTypo.current.Body02, color = textColor)
    }
}

@Composable
fun PagerFocusedItem(
    title: String, challengeStatus: ChallengeStatus, onClick: () -> Unit
) {
    val backgroundColor = when (challengeStatus) {
        ChallengeStatus.PRE_OPENED -> ZzanZColorPalette.current.Gray06
        ChallengeStatus.OPENED -> ZzanZColorPalette.current.Green04
        ChallengeStatus.CLOSED -> ZzanZColorPalette.current.Gray04
    }
    Box(modifier = Modifier
        .clip(RoundedCornerShape(40.dp))
        .background(backgroundColor)
        .padding(vertical = 8.dp, horizontal = 15.dp)
        .wrapContentSize()
        .clickable { onClick() }) {
        Text(
            text = title,
            style = ZzanZTypo.current.Body01.copy(fontWeight = FontWeight.SemiBold),
            color = ZzanZColorPalette.current.White
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PagerUnFocusedItem(
    title: String, challengeStatus: ChallengeStatus, onClick: () -> Unit
) {
    val textColor = when (challengeStatus) {
        ChallengeStatus.CLOSED -> ZzanZColorPalette.current.Gray03
        else -> ZzanZColorPalette.current.Gray06
    }
    BadgedBox(modifier = Modifier
        .padding(vertical = 8.dp, horizontal = 15.dp)
        .clickable { onClick() },
        badge = {
            if (challengeStatus == ChallengeStatus.PRE_OPENED) Badge(
                modifier = Modifier.size(
                    5.dp
                )
            )
        }) {
        Box(
            modifier = Modifier.wrapContentSize()
        ) {
            Text(
                text = title, style = ZzanZTypo.current.Body01, color = textColor
            )
        }
    }
}

@Composable
fun CategoryCardItem(
    itemWidth: Dp,
    title: String,
    remainAmount: Int,
    ratio: Float,
    onClickItem: () -> Unit
) {
    Column(
        modifier = Modifier
            .padding(bottom = 16.dp)
            .shadow(
                elevation = 13.dp,
                spotColor = Color(0x0F000000),
                ambientColor = Color(0x0F000000)
            )
            .width(itemWidth)
            .height(100.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(ZzanZColorPalette.current.White)
            .padding(16.dp)
            .clickable { onClickItem() },
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            val (amount, color) = if(remainAmount < 0){
                Pair(remainAmount * -1, ZzanZColorPalette.current.Red04)
            } else {
                Pair(remainAmount, ZzanZColorPalette.current.Gray08)
            }
            CategoryTitleText(modifier = Modifier.align(Alignment.TopStart), title = title)
            RemainAmountText(
                modifier = Modifier.align(Alignment.BottomEnd),
                remainAmount = MoneyFormatter.format(amount) + stringResource(id = R.string.money_unit),
                color = color
            )
        }
        ProgressIndicator(
            modifier = Modifier.padding(top = 8.dp), color = ZzanZColorPalette.current.Green04, ratio = ratio
        )
    }
}

@Composable
fun CategoryTitleText(
    modifier: Modifier = Modifier, title: String
) {
    Text(
        modifier = modifier,
        text = title,
        style = ZzanZTypo.current.Body02,
        color = ZzanZColorPalette.current.Gray06
    )
}

@Composable
fun RemainAmountText(
    modifier: Modifier = Modifier, remainAmount: String, color: Color
) {
    Text(
        modifier = modifier,
        text = remainAmount,
        style = ZzanZTypo.current.Body01.copy(fontWeight = FontWeight.SemiBold),
        color = color
    )
}

@Composable
fun AddSpendingComponent(
    onClick: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        Row(
            modifier = Modifier
                .padding(vertical = 10.dp)
                .clickable { onClick() },
            verticalAlignment = Alignment.CenterVertically
        ) {
            AddIcon()
            Spacer(modifier = Modifier.width(16.dp))
            TitleText(stringResource(id = R.string.category_add_spending_btn_title))
        }
    }
}

@Composable
fun SpendingItemComponent(category: String, title: String, memo: String, amount: String) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        Row(
            modifier = Modifier.padding(vertical = 17.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            CategoryIcon(
                modifier = Modifier.size(32.dp),
                resId = Category.valueOf(category).imgResId
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                TitleText(title = title)
                if(memo.isNotEmpty()) MemoText(memo = memo)
            }
            AmountText(amount = amount + stringResource(id = R.string.money_unit))
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
