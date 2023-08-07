package com.example.zzanz_android.presentation.view.setting

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.zzanz_android.common.ui.theme.ZzanZColorPalette
import com.example.zzanz_android.common.ui.theme.ZzanZTypo
import com.example.zzanz_android.presentation.view.component.TitleText

@Composable
fun AlarmSetting(titleText: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        TitleText(
            modifier = Modifier.padding(horizontal = 24.dp),
            text = titleText
        )
        Spacer(modifier = Modifier.height(28.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .defaultMinSize(minWidth = 312.dp, minHeight = 367.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            CircularClock(hourSize = 24, initialHour = 12)
            Text(
                text = ":",
                color = ZzanZColorPalette.current.Gray09,
                style = ZzanZTypo.current.Heading
            )
            CircularClock(hourSize = 60, initialHour = 10)
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CircularClock(
    hourSize: Int, initialHour: Int
) {
    // TODO - 분 초로 수정
    val height = 160.dp
    val cellSize = height / 3
    val cellTextSize = LocalDensity.current.run { (cellSize / 2f).toSp() }

    // just prepare an offset of 1 hour when format is set to 12hr format
    val hourOffset = if (hourSize == 12) 1 else 0
    val expandedSize = hourSize * 10_000_000
    val initialListPoint = expandedSize / 2
    val targetIndex = initialListPoint + initialHour - 1

    val scrollState = rememberLazyListState(targetIndex)
    val hour by remember { derivedStateOf { (scrollState.firstVisibleItemIndex + 1) % hourSize } }

    if (!scrollState.isScrollInProgress) {
        Log.e("FocusedHour", "${hour + hourOffset}")
    }

    LaunchedEffect(Unit) {
        // subtract the offset upon initial scrolling, otherwise it will look like
        // it moved 1 hour past the initial hour when format is set to 12hr format
        scrollState.scrollToItem(targetIndex - hourOffset)
    }

    Box(
        modifier = Modifier
            .height(height)
            .wrapContentWidth()
    ) {
        LazyColumn(
            modifier = Modifier.wrapContentWidth(),
            state = scrollState,
            flingBehavior = rememberSnapFlingBehavior(lazyListState = scrollState)
        ) {
            items(expandedSize, itemContent = {

                // if 12hr format, move 1 hour so instead of displaying 00 -> 11
                // it will display 01 to 12
                val num = (it % hourSize) + hourOffset
                Box(
                    modifier = Modifier.size(cellSize), contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = String.format("%02d", num),
                        style = ZzanZTypo.current.Heading,
                        color = ZzanZColorPalette.current.Gray09
                    )
                }
            })
        }
    }
}

@Preview
@Composable
fun AlarmSettingPreview() {
    AlarmSetting(titleText = "Text")
}