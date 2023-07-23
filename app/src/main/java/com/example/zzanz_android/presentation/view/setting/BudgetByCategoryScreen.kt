package com.example.zzanz_android.presentation.view.setting

import android.util.Log
import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.zzanz_android.R
import com.example.zzanz_android.common.ui.theme.ZzanZColorPalette
import com.example.zzanz_android.common.ui.theme.ZzanZTypo
import com.example.zzanz_android.presentation.view.component.BudgetTextField
import com.example.zzanz_android.presentation.view.component.TitleText

@Composable
fun BudgetByCategory() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(ZzanZColorPalette.current.White)
    ) {
        TitleText(
            modifier = Modifier,
            text = stringResource(id = R.string.budget_by_category_title)
        )
        Spacer(modifier = Modifier.height(28.dp))
        explainTotalBudget(totalBudget = 10000)
        Spacer(modifier = Modifier.height(12.dp))
        // TODO - 카테고리별 리스트로 만들기
        BudgetByCategoryItem("카테고리1", R.drawable.icon_plus, 1000)
        // TODO - 클릭시 bottom bar 추가
        AddBudgetByCategoryItemBtn()
        Spacer(modifier = Modifier.height(12.dp))
    }
}

@Composable
fun explainTotalBudget(totalBudget: Int) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(0.dp)
            .background(
                color = ZzanZColorPalette.current.Gray01,
                shape = RectangleShape
            )
            .padding(horizontal = 24.dp, vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(
                id = R.drawable.alert_information_circle
            ),
            contentDescription = ""
        )
        Spacer(modifier = Modifier.width(7.dp))
        Text(
            text = stringResource(id = R.string.total_budget_title_1) + " ",
            style = ZzanZTypo.current.Body03,
            color = ZzanZColorPalette.current.Gray08
        )
        Text(
            text = stringResource(id = R.string.money_krw, totalBudget),
            style = ZzanZTypo.current.Body03.copy(fontWeight = FontWeight.Bold),
            color = ZzanZColorPalette.current.Gray08
        )
        Text(
            text = stringResource(id = R.string.total_budget_title_2),
            style = ZzanZTypo.current.Body03,
            color = ZzanZColorPalette.current.Gray08
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BudgetByCategoryItem(
    category: String,
    @DrawableRes
    categoryImage: Int,
    budget: Int
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(0.dp)
            .background(color = Color.Transparent)
            .padding(horizontal = 24.dp, vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        PlusIcon()
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(
                text = category,
                color = ZzanZColorPalette.current.Gray05,
                style = ZzanZTypo.current.Body03.copy(fontWeight = FontWeight.Medium)
            )
            Row {
                BudgetTextField(
                    modifier = Modifier
                        .width(54.dp)
                        .height(24.dp),
                    strExplain = "10,000",
                    onTextChange = { text: String -> Log.d("BudgetByCategory", text) },
                    keyboardType = KeyboardType.Number
                )
                Text(
                    text = stringResource(id = R.string.krw),
                    style = ZzanZTypo.current.Body02.copy(
                        fontWeight = FontWeight.SemiBold
                    ),
                    color = ZzanZColorPalette.current.Gray05
                )
            }
        }
        Spacer(modifier = Modifier.weight(1f))
        // TODO - 삭제 버튼 영역 넓히기 (패딩 안먹혀져있음 수정필요)
        Column(
            modifier = Modifier.height(56.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                modifier = Modifier
                    .padding(0.dp)
                    .height(56.dp)
                    .padding(vertical = 17.dp),
                textAlign = TextAlign.Center,
                text = stringResource(id = R.string.delete),
                color = ZzanZColorPalette.current.Red04,
                style = ZzanZTypo.current.Body03.copy(fontWeight = FontWeight.Medium)
            )
        }

        Spacer(modifier = Modifier.width(24.dp))

    }
}

@Composable
fun AddBudgetByCategoryItemBtn() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .padding()
            .background(color = Color.Transparent)
            .padding(horizontal = 24.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        PlusIcon()
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = stringResource(id = R.string.add_category),
            style = ZzanZTypo.current.Body02.copy(fontWeight = FontWeight.SemiBold),
            color = ZzanZColorPalette.current.Gray08
        )
    }
}

@Composable
fun PlusIcon() {
    Column(
        modifier = Modifier
            .width(32.dp)
            .height(32.dp)
            .background(
                color = ZzanZColorPalette.current.Gray02,
                shape = CircleShape
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            modifier = Modifier
                .size(20.dp),
            painter = painterResource(id = R.drawable.icon_plus),
            contentDescription = ""
        )
    }
}


@Preview
@Composable
fun BudgetByCategoryPreview() {
    BudgetByCategory()
}