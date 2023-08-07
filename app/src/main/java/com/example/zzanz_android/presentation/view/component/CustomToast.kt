package com.example.zzanz_android.presentation.view.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.zIndex
import com.example.zzanz_android.common.ui.theme.ZzanZColorPalette
import com.example.zzanz_android.common.ui.theme.ZzanZTypo

@Composable
fun Toast(modifier: Modifier = Modifier, message: String, isVisible: Boolean = false) {
    if (!isVisible) return

    Dialog(
        onDismissRequest = {}, properties = DialogProperties()
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Transparent)
                .height(56.dp)
                .padding(bottom = 100.dp),
            contentAlignment = Alignment.BottomCenter
        ) {
            Text(
                modifier = modifier
                    .zIndex(1f)
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .background(
                        color = ZzanZColorPalette.current.Red04, shape = RoundedCornerShape(8.dp)
                    )
                    .padding(10.dp),
                text = message,
                textAlign = TextAlign.Center,
                style = ZzanZTypo.current.SubHeading,
                color = ZzanZColorPalette.current.White
            )
        }
    }
}


@Preview
@Composable
fun ToastPreview() {
    Toast(message = "Toast Test!", isVisible = true)
}