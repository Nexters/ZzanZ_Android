package com.example.zzanz_android.presentation.view.component

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.zzanz_android.R
import com.example.zzanz_android.ZzanZApp
import com.example.zzanz_android.common.ui.theme.ZzanZColorPalette
import com.example.zzanz_android.common.ui.util.ImageViewWithXml

@Composable
fun BackIcon() {
    Icon(painter = painterResource(id = R.drawable.icon_left), contentDescription = null)
}

@Composable
fun AddIcon() {
    ImageViewWithXml(modifier = Modifier.size(36.dp), resId = R.drawable.icon_add)
}

@Composable
fun InfoICon(color: Color) {
    Icon(
        modifier = Modifier.size(16.dp),
        painter = painterResource(id = R.drawable.alert_information_circle),
        tint = color,
        contentDescription = null
    )
}