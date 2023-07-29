package com.example.zzanz_android.presentation.view.category

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.zzanz_android.R
import com.example.zzanz_android.common.ui.theme.ZzanZColorPalette
import com.example.zzanz_android.common.ui.theme.ZzanZDimen
import com.example.zzanz_android.common.ui.theme.ZzanZTypo
import com.example.zzanz_android.presentation.view.component.AddSpendingComponent
import com.example.zzanz_android.presentation.view.component.AppBarWithBackNavigation
import com.example.zzanz_android.presentation.view.component.SpendingItemComponent

@Composable
fun CategoryScreen() {
    // TODO : gowoon - 후에 뷰모델 이용해서 데이터로 대체될 임시 value
    val isVisibleAddButton = true
    val category = "식비"
    val goalAmount = "100,000원"
    val remainAmount = "50,000원"
    val spendingTitle = "저녁"
    val memo = "짠지랑 신나는 치맥"
    val spendingAmount = "15,000원"
    Scaffold(
        topBar = { AppBarWithBackNavigation() }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            LazyColumn(modifier = Modifier.padding(horizontal = ZzanZDimen.current.defaultHorizontal)) {
                item{
                    Title(remainAmount, ZzanZColorPalette.current.Green04)
                    SubTitle(category, goalAmount)
                    Spacer(modifier = Modifier.height(32.dp))
                    if(isVisibleAddButton){
                        AddSpendingComponent()
                    }
                }
                items(10){ // TODO : gowoon - list로 대체될 것
                    SpendingItemComponent(spendingTitle, memo, spendingAmount)
                }
                item {
                    Spacer(modifier = Modifier.height(25.dp))
                }
            }
        }
    }
}

@Composable
fun Title(remainAmount: String, color: Color) {
    Surface(modifier = Modifier.padding(top = 8.dp)) {
        Text(
            buildAnnotatedString {
                withStyle(SpanStyle(color = ZzanZColorPalette.current.Black)) {
                    append(stringResource(id = R.string.category_remain_amount_title_prefix))
                }
                withStyle(SpanStyle(color = color)) {
                    append("\n$remainAmount ")
                }
                withStyle(SpanStyle(color = ZzanZColorPalette.current.Black)) {
                    append(stringResource(id = R.string.category_remain_amount_title_suffix))
                }
            },
            style = ZzanZTypo.current.H1
        )
    }
}

@Composable
fun SubTitle(category: String, goalAmount: String) {
    Surface(modifier = Modifier.padding(top = 8.dp)) {
        Text(
            text = stringResource(
                id = R.string.category_goal_amount_subtitle,
                category,
                goalAmount
            ),
            style = ZzanZTypo.current.Body01,
            color = ZzanZColorPalette.current.Gray05
        )
    }
}

@Preview
@Composable
fun CategoryPreview() {
    CategoryScreen()
}