package com.zzanz.swip_android.presentation.view.component

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.zzanz.swip_android.common.ui.theme.ZzanZColorPalette
import com.zzanz.swip_android.common.ui.theme.ZzanZTypo

@Composable
fun TitleText(modifier: Modifier, text: String = "") {
    Text(
        modifier = modifier,
        text = text,
        style = ZzanZTypo.current.H1.copy(fontWeight = FontWeight.Bold),
        color = ZzanZColorPalette.current.Gray09
    )
}

@Preview
@Composable
fun TitleTextPreview() {
    TitleText(modifier = Modifier, text = "TestTitle")
}