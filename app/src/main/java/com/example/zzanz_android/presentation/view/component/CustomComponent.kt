package com.example.zzanz_android.presentation.view.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.zzanz_android.R
import com.example.zzanz_android.common.ui.theme.ZzanZColorPalette
import com.example.zzanz_android.common.ui.theme.ZzanZTypo

@Composable
fun InformationComponent(
    iconColor: Color,
    textColor: Color,
    message: String
){
    Row(verticalAlignment = Alignment.CenterVertically) {
        InfoICon(color = iconColor)
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = message, style = ZzanZTypo.current.Body02, color = textColor)
    }
}

@Composable
fun AddSpendingComponent(){
    Surface(modifier = Modifier
        .fillMaxWidth()
        .wrapContentHeight()
    ) {
        Row(modifier = Modifier.padding(vertical = 10.dp), verticalAlignment = Alignment.CenterVertically){
            AddIcon()
            Spacer(modifier = Modifier.width(16.dp))
            TitleText(stringResource(id = R.string.category_add_spending_btn_title))
        }
    }
}

@Composable
fun SpendingItemComponent(title: String, memo: String, amount: String){
    Surface(modifier = Modifier
        .fillMaxWidth()
        .wrapContentHeight()
    ) {
        Row(modifier = Modifier.padding(vertical = 17.dp), verticalAlignment = Alignment.CenterVertically){
            AddIcon() // TODO : gowoon - 임시 아무 아이콘
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                TitleText(title = title)
                MemoText(memo = memo)
            }
            AmountText(amount = amount)
        }
    }
}

@Composable
fun TitleText(title: String){
    Text(text = title, style = ZzanZTypo.current.Body01, color = ZzanZColorPalette.current.Gray08)
}

@Composable
fun MemoText(memo: String){
    Text(text = memo, style = ZzanZTypo.current.Body02, color = ZzanZColorPalette.current.Gray05)
}

@Composable
fun AmountText(amount: String){
    Text(text = amount, style = ZzanZTypo.current.Body01, color = ZzanZColorPalette.current.Gray08)
}