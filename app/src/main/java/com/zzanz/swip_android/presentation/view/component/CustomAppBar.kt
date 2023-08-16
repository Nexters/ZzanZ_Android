package com.zzanz.swip_android.presentation.view.component

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.zzanz.swip_android.common.ui.theme.ZzanZColorPalette

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBarWithBackNavigation(
    modifier: Modifier = Modifier,
    isBackIconVisible: Boolean = true,
    appbarColor: Color,
    onBackButtonAction: () -> Unit = {}) {
    TopAppBar(
        modifier = modifier,
        title = {},
        navigationIcon = {
            if (isBackIconVisible) {
                IconButton(onClick = {
                    onBackButtonAction.invoke()
                }) {
                    BackIcon()
                }
            }
        },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = appbarColor,
            navigationIconContentColor = ZzanZColorPalette.current.Gray08
        )
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