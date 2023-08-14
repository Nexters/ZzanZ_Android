package com.example.zzanz_android.presentation.view.component

import android.view.WindowInsets
import androidx.compose.foundation.background
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.example.zzanz_android.common.ui.theme.ZzanZColorPalette

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBarWithBackNavigation(
    modifier: Modifier = Modifier,
    isBackIconVisible: Boolean = true,
    onBackButtonAction: () -> Unit = {}) {
    TopAppBar(
        modifier = modifier.background(ZzanZColorPalette.current.White),
        title = {},
        navigationIcon = {
            if (isBackIconVisible) {
                IconButton(onClick = {
                    onBackButtonAction.invoke()
                }) {
                    BackIcon()
                }
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBarWithMoreAction(appbarColor: Color, onClickAction: () -> Unit){
    TopAppBar(
        title = {},
        actions = {
            IconButton(onClick = onClickAction) {
                MoreIcon()
            }
        },
        colors = TopAppBarDefaults.mediumTopAppBarColors(containerColor = appbarColor)
    )
}