package com.example.zzanz_android.presentation.view.component

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.zzanz_android.common.ui.theme.ZzanZColorPalette
import com.example.zzanz_android.common.ui.theme.ZzanZTypo
import com.example.zzanz_android.presentation.view.component.contract.BudgetContract


@Composable
fun BottomGreenButton(
    buttonText: String,
    onClick: () -> Unit,
    isButtonEnabled: Boolean,
    isKeyboardOpen: Boolean,
    horizontalWidth: Dp
) {
    GreenButton(
        modifier = Modifier
            .height(56.dp)
            .padding(horizontal = horizontalWidth),
        text = buttonText,
        onClick = {
            onClick()
        },
        enabled = isButtonEnabled,
        isKeyboardOpen = isKeyboardOpen
    )
    if (!isKeyboardOpen) {
        Spacer(modifier = Modifier.height(24.dp))
    }
}


@Composable
fun GreenButton(
    modifier: Modifier, text: String, onClick: () -> Unit, enabled: Boolean, isKeyboardOpen: Boolean
) {
    Button(modifier = modifier
        .fillMaxWidth()
        .height(56.dp),
        shape = if (!isKeyboardOpen) RoundedCornerShape(size = 12.dp) else RectangleShape,
        enabled = enabled,
        colors = ButtonDefaults.run {
            buttonColors(
                disabledContainerColor = ZzanZColorPalette.current.Gray03,
                disabledContentColor = ZzanZColorPalette.current.White,
                containerColor = ZzanZColorPalette.current.Green04,
                contentColor = ZzanZColorPalette.current.White,
            )
        },
        onClick = { onClick() }) {
        Text(
            text = text, style = ZzanZTypo.current.Heading
        )
    }
}

@Composable
fun GreenRoundButton(
    modifier: Modifier, text: String, onClick: () -> Unit, enabled: Boolean
) {
    Button(modifier = modifier
        .fillMaxWidth()
        .height(56.dp),
        shape = RoundedCornerShape(size = 12.dp),
        enabled = enabled,
        colors = ButtonDefaults.run {
            buttonColors(
                disabledContainerColor = ZzanZColorPalette.current.Gray03,
                disabledContentColor = ZzanZColorPalette.current.White,
                containerColor = ZzanZColorPalette.current.Green04,
                contentColor = ZzanZColorPalette.current.White,
            )
        },
        onClick = { onClick() }) {
        Text(
            text = text, style = ZzanZTypo.current.Heading
        )
    }
}

@Composable
fun GreenRectButton(modifier: Modifier, text: String, onClick: () -> Unit, enabled: Boolean) {
    Button(modifier = modifier
        .fillMaxWidth()
        .height(56.dp),
        shape = RectangleShape,
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(
            disabledContainerColor = ZzanZColorPalette.current.Gray03,
            disabledContentColor = ZzanZColorPalette.current.White,
            containerColor = ZzanZColorPalette.current.Green04,
            contentColor = ZzanZColorPalette.current.White,
        ),
        onClick = { onClick() }) {
        Text(
            text = text, style = ZzanZTypo.current.Heading
        )
    }
}

@Composable
fun CustomCategoryButton(
    modifier: Modifier, text: String, onClick: () -> Unit, isChecked: Boolean
) {
    Button(modifier = modifier
        .fillMaxWidth()
        .height(56.dp)
        .border(
            width = 1.dp,
            shape = RoundedCornerShape(size = 8.dp),
            color = if (isChecked) ZzanZColorPalette.current.Green04
            else ZzanZColorPalette.current.Gray03
        ),
        shape = RoundedCornerShape(size = 8.dp),
        colors = ButtonDefaults.run {
            buttonColors(
                disabledContainerColor = ZzanZColorPalette.current.White,
                disabledContentColor = ZzanZColorPalette.current.Gray08,
                containerColor = if (isChecked) ZzanZColorPalette.current.Green01
                else ZzanZColorPalette.current.White,
                contentColor = ZzanZColorPalette.current.Gray08,
            )
        },
        onClick = {
            onClick()
        }) {
        Text(
            text = text, style = ZzanZTypo.current.Body02
        )
    }
}

@Preview
@Composable
fun CustomButtonPreview() {
    Column(modifier = Modifier.padding(10.dp)) {
        GreenRoundButton(modifier = Modifier, text = "Button", onClick = {}, enabled = true)
        Spacer(modifier = Modifier.height(8.dp))
        GreenRectButton(modifier = Modifier, text = "RectButton", onClick = {}, enabled = false)
        Spacer(modifier = Modifier.height(8.dp))
        CustomCategoryButton(
            modifier = Modifier, text = "CategoryButton", onClick = {}, isChecked = true
        )
    }
}
