package com.example.zzanz_android.presentation.view.component

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.zzanz_android.R
import com.example.zzanz_android.common.ui.util.ImageViewWithXml

@Composable
fun BackIcon() {
    Icon(painter = painterResource(id = R.drawable.icon_left), contentDescription = null)
}

@Composable
fun MoreIcon(){
    Icon(painterResource(id = R.drawable.icon_right), contentDescription = null)
}

@Composable
fun AddIcon() {
    ImageViewWithXml(modifier = Modifier.size(36.dp), resId = R.drawable.icon_add)
}