package com.example.zzanz_android.presentation.view.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.zzanz_android.R
import com.example.zzanz_android.common.ui.theme.ZzanZColorPalette
import com.example.zzanz_android.common.ui.theme.ZzanZTypo
import com.example.zzanz_android.domain.model.ChallengeStatus

@Composable
fun ProgressIndicator(
    modifier: Modifier = Modifier,
    color: Color,
    ratio: Float
) {
    LinearProgressIndicator(
        modifier = modifier,
        trackColor = ZzanZColorPalette.current.Gray03,
        color = color,
        progress = ratio
    )
}

@Composable
fun PagerFocusedItem(
    title: String,
    challengeStatus: ChallengeStatus,
    onClick: () -> Unit
) {
    val backgroundColor = when (challengeStatus) {
        ChallengeStatus.PRE_OPENED -> ZzanZColorPalette.current.Gray06
        ChallengeStatus.OPENED -> ZzanZColorPalette.current.Green04
        ChallengeStatus.CLOSED -> ZzanZColorPalette.current.Gray04
    }
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(40.dp))
            .background(backgroundColor)
            .padding(vertical = 8.dp, horizontal = 15.dp)
            .wrapContentSize()
            .clickable { onClick() }
    ) {
        Text(
            text = title,
            style = ZzanZTypo.current.Body01.copy(fontWeight = FontWeight.SemiBold),
            color = ZzanZColorPalette.current.White
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PagerUnFocusedItem(
    title: String,
    challengeStatus: ChallengeStatus,
    onClick: () -> Unit
) {
    val textColor = when (challengeStatus) {
        ChallengeStatus.CLOSED -> ZzanZColorPalette.current.Gray03
        else -> ZzanZColorPalette.current.Gray06
    }
    BadgedBox(
        modifier = Modifier.padding(vertical = 8.dp, horizontal = 15.dp).clickable { onClick() },
        badge = {
            if (challengeStatus == ChallengeStatus.PRE_OPENED) Badge(
                modifier = Modifier.size(
                    5.dp
                )
            )
        })
    {
        Box(
            modifier = Modifier
                .wrapContentSize()
        ) {
            Text(
                text = title,
                style = ZzanZTypo.current.Body01,
                color = textColor
            )
        }
    }
}

@Composable
fun CategoryCardItem(
    title: String,
    remainAmount: String,
    ratio: Float,
    indicatorColor: Color
) {
    Column(
        modifier = Modifier
            .width(154.dp)
            .height(96.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(ZzanZColorPalette.current.White)
            .padding(16.dp)
    ) {
        Box(modifier = Modifier.fillMaxWidth().weight(1f)){
            CategoryTitleText(modifier = Modifier.align(Alignment.TopStart), title = title)
            RemainAmountText(
                modifier = Modifier.align(Alignment.BottomEnd),
                remainAmount = remainAmount
            )
        }
        ProgressIndicator(
            modifier = Modifier.padding(top = 8.dp),
            color = indicatorColor,
            ratio = ratio
        )
    }
}

@Composable
fun CategoryTitleText(
    modifier: Modifier = Modifier,
    title: String
) {
    Text(
        modifier = modifier,
        text = title,
        style = ZzanZTypo.current.Body02,
        color = ZzanZColorPalette.current.Gray06
    )
}

@Composable
fun RemainAmountText(
    modifier: Modifier = Modifier,
    remainAmount: String
) {
    Text(
        modifier = modifier,
        text = remainAmount,
        style = ZzanZTypo.current.Body01.copy(fontWeight = FontWeight.SemiBold),
        color = ZzanZColorPalette.current.Gray08
    )
}

@Composable
fun AddSpendingComponent() {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        Row(
            modifier = Modifier.padding(vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AddIcon()
            Spacer(modifier = Modifier.width(16.dp))
            TitleText(stringResource(id = R.string.category_add_spending_btn_title))
        }
    }
}

@Composable
fun SpendingItemComponent(title: String, memo: String, amount: String) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        Row(
            modifier = Modifier.padding(vertical = 17.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
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
fun TitleText(title: String) {
    Text(text = title, style = ZzanZTypo.current.Body01, color = ZzanZColorPalette.current.Gray08)
}

@Composable
fun MemoText(memo: String) {
    Text(text = memo, style = ZzanZTypo.current.Body02, color = ZzanZColorPalette.current.Gray05)
}

@Composable
fun AmountText(amount: String) {
    Text(text = amount, style = ZzanZTypo.current.Body01, color = ZzanZColorPalette.current.Gray08)
}

@Preview
@Composable
fun ComponentPreview() {
    CategoryCardItem(
        title = "식비",
        remainAmount = "50,000원",
        0.7f,
        ZzanZColorPalette.current.Green04
    )
}