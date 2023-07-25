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
import androidx.compose.ui.unit.dp
import com.example.zzanz_android.common.ui.theme.ZzanZColorPalette
import com.example.zzanz_android.common.ui.theme.ZzanZTypo

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
            text = text, style = ZzanZTypo.current.Headline
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
            text = text, style = ZzanZTypo.current.Headline
        )
    }
}

@Composable
fun CustomCategoryButton(modifier: Modifier, text: String, onClick: () -> Unit) {
    Button(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
            .border(
                width = 1.dp,
                color = ZzanZColorPalette.current.Green04,
                shape = RoundedCornerShape(8.dp)
            ),
        colors = ButtonDefaults.buttonColors(
            disabledContainerColor = ZzanZColorPalette.current.White,
            disabledContentColor = ZzanZColorPalette.current.Gray08,
            containerColor = ZzanZColorPalette.current.Green01,
            contentColor = ZzanZColorPalette.current.Gray08
        ),
        onClick = { onClick() },
        shape = RoundedCornerShape(8.dp)
    ) {
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
        CustomCategoryButton(modifier = Modifier, text = "CategoryButton", onClick = {})
    }
}
