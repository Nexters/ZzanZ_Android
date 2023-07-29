package com.example.zzanz_android.presentation.view.component

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

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
fun AppBarWithMoreAction(onClickAction: () -> Unit){
    TopAppBar(
        title = {},
        actions = {
            IconButton(onClick = onClickAction) {
                MoreIcon()
            }
        }
    )
}

@Preview
@Composable
fun Preview(){
    Column {
        AppBarWithBackNavigation()
        AppBarWithMoreAction({})
    }
}