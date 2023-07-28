package com.example.zzanz_android.presentation.view.spending

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.zzanz_android.R
import com.example.zzanz_android.common.ui.theme.ZzanZColorPalette
import com.example.zzanz_android.common.ui.theme.ZzanZDimen
import com.example.zzanz_android.common.ui.theme.ZzanZTypo
import com.example.zzanz_android.presentation.view.component.AppBarWithBackNavigation

@Composable
fun AddSpendingScreen() {
    Scaffold(topBar = {
        AppBarWithBackNavigation()
    }) {
        Box(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        ) {
            AddSpendingContent()
        }
    }
}

@Composable
fun AddSpendingContent() {
    Column(
        modifier = Modifier
            .padding(horizontal = ZzanZDimen.current.defaultHorizontal)
            .fillMaxSize()
    ) {
        Spacer(modifier = Modifier.height(8.dp))
        TitleText()

    }
}

@Composable
fun TitleText() {
    Text(
        text = stringResource(id = R.string.spending_title),
        style = ZzanZTypo.current.H1,
        color = ZzanZColorPalette.current.Gray09
    )
}

@Preview
@Composable
fun AddSpendingPreview() {
    AddSpendingScreen()
}