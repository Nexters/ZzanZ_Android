package com.example.zzanz_android.presentation.view.component

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBarWithBackNavigation() {
    TopAppBar(
        title = {},
        navigationIcon = {
            IconButton(onClick = { /*TODO*/ }) { // TODO : gowoon - 그냥 BackPress일 수도 있을 거 같아서 우선 TODO
                BackIcon()
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