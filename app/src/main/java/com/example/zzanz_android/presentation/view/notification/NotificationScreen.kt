package com.example.zzanz_android.presentation.view.notification

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.zzanz_android.R
import com.example.zzanz_android.common.navigation.NavRoutes
import com.example.zzanz_android.common.navigation.SettingNavRoutes
import com.example.zzanz_android.common.navigation.SettingType
import com.example.zzanz_android.common.ui.theme.ZzanZColorPalette
import com.example.zzanz_android.common.ui.theme.ZzanZDimen
import com.example.zzanz_android.common.ui.theme.ZzanZTypo
import com.example.zzanz_android.presentation.view.component.AppBarWithBackNavigation
import com.example.zzanz_android.presentation.view.component.GreenRoundButton
import com.example.zzanz_android.presentation.view.component.TitleText
import com.example.zzanz_android.presentation.view.component.contract.NotificationContract
import com.example.zzanz_android.presentation.viewmodel.NotificationViewModel
import timber.log.Timber

@Composable
fun NotificationSetting(
    navController: NavHostController,
    settingType: String? = SettingType.onBoarding,
    notificationViewModel: NotificationViewModel = hiltViewModel()
) {
    val hourState = notificationViewModel.uiState.collectAsState().value.hour
    val minuteState = notificationViewModel.uiState.collectAsState().value.minute
    val titleRes = notificationViewModel.uiState.collectAsState().value.title.value
    var buttonTitle = stringResource(id = R.string.set_notification_time_btn_title)
    LaunchedEffect(key1 = true, block = {
        notificationViewModel.setEvent(NotificationContract.Event.GetNotificationTime)
        notificationViewModel.setEvent(NotificationContract.Event.SetSettingType(settingType))
    })
    LaunchedEffect(key1 = Unit, block = {
        notificationViewModel.effect.collect {
            when (it) {
                NotificationContract.Effect.NextRoutes -> {
                    navController.navigate(NavRoutes.Home.route) {
                        if (settingType == SettingType.onBoarding) {
                            popUpTo(navController.graph.id) {
                                inclusive = true
                            }
                        }
                    }
                }
            }
        }
    })

    val hour = hourState.value
    val minute = minuteState.value
    val buttonFormat = "${String.format("%02d", hour)}:${String.format("%02d", minute)}"
    buttonTitle = stringResource(id = R.string.set_notification_time_btn_title, buttonFormat)
    Timber.e("333 - $hour : $minute")
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(ZzanZColorPalette.current.White)
            .padding(horizontal = ZzanZDimen.current.defaultHorizontal)
    ) {
        AppBarWithBackNavigation(isBackIconVisible = false)
        Spacer(modifier = Modifier.height(8.dp))
        TitleText(
            modifier = Modifier, text = stringResource(titleRes)
        )
        Spacer(modifier = Modifier.weight(1f))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            CircularNumber(
                hourSize = 24,
                initialHour = hour,
                maxSize = 12,
                isHour = true,
                notificationViewModel = notificationViewModel,
                numberPadding = 1
            )
            Spacer(modifier = Modifier.width(32.dp))
            Text(
                text = ":",
                color = ZzanZColorPalette.current.Gray09,
                style = ZzanZTypo.current.Heading.copy(fontSize = 36.sp)
            )
            Spacer(modifier = Modifier.width(32.dp))
            CircularNumber(
                hourSize = 60,
                initialHour = minute,
                maxSize = 59,
                isHour = false,
                numberPadding = 10
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        GreenRoundButton(
            modifier = Modifier.fillMaxWidth(), text = buttonTitle, onClick = {
                notificationViewModel.setEvent(NotificationContract.Event.OnNextButtonClicked)
            }, enabled = true
        )
        Spacer(modifier = Modifier.height(24.dp))
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CircularNumber(
    isHour: Boolean,
    hourSize: Int, initialHour: Int, maxSize: Int,
    notificationViewModel: NotificationViewModel = hiltViewModel(),
    numberPadding: Int,
) {
    val height = 160.dp
    val cellSize = height / 3

    val hourOffset = if (hourSize == maxSize) 1 else 0
    val expandedSize = hourSize * 10_000_000
    val initialListPoint = expandedSize / 2
    val targetIndex = initialListPoint + initialHour - 1

    val scrollState = rememberLazyListState(targetIndex)
    val hour by remember { derivedStateOf { (scrollState.firstVisibleItemIndex + 1) % hourSize } }

    if (!scrollState.isScrollInProgress) {
        notificationViewModel.setEvent(
            NotificationContract.Event.SetNotificationTime(
                isHour = isHour, num = hour
            )
        )
        // Timber.e("FocusedHour ${hour}")
    }

    LaunchedEffect(Unit) {
        // subtract the offset upon initial scrolling, otherwise it will look like
        // it moved 1 hour past the initial hour when format is set to 12hr format
        scrollState.scrollToItem(targetIndex - hourOffset)
    }

    Box(
        modifier = Modifier
            .height(height)
            .width(46.dp),
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
                val isFocusedNum = (scrollState.firstVisibleItemIndex + 1) == it
                Box(
                    modifier = Modifier
                        .height(cellSize)
                        .width(46.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = if (isHour) num.toString() else String.format("%02d", num),
                        style = ZzanZTypo.current.Heading.copy(fontSize = if (isFocusedNum) 36.sp else 28.sp),
                        color = if (isFocusedNum) ZzanZColorPalette.current.Gray09 else ZzanZColorPalette.current.Gray04
                    )
                }
            })
        }
    }
}

@Preview
@Composable
fun NotificationSettingPreview() {
    NotificationSetting(navController = rememberNavController(), settingType = null)
}