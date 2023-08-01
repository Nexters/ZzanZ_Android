package com.example.zzanz_android.presentation.view.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
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
import com.example.zzanz_android.domain.model.ChallengeStatus
import com.example.zzanz_android.presentation.view.component.AppBarWithMoreAction
import com.example.zzanz_android.presentation.view.component.CategoryCardItem
import com.example.zzanz_android.presentation.view.component.GreenRoundButton
import com.example.zzanz_android.presentation.view.component.PagerFocusedItem
import com.example.zzanz_android.presentation.view.component.PagerUnFocusedItem
import com.example.zzanz_android.presentation.view.component.PopupSheetDialog
import com.example.zzanz_android.presentation.view.component.ProgressIndicator
import kotlinx.coroutines.launch

object HomeScreenValue {
    const val GRID_COUNT = 2
}

@Composable
fun HomeScreen() {
    val challengeStatus = ChallengeStatus.CLOSED
    val challengeTitle = "챌린지 시작 전"
    val startDate = "8.8"
    val endDate = "8.16"

    var showDialog by remember{ mutableStateOf(false) }

    Scaffold(
        modifier = Modifier,
        topBar = { AppBarWithMoreAction(ZzanZColorPalette.current.Gray01) {
            showDialog = !showDialog
        } },
        containerColor = ZzanZColorPalette.current.Gray01
    ) {
        Box(modifier = Modifier.padding(it)) {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                HomeContent(
                    modifier = Modifier.weight(1f),
                    challengeTitle = challengeTitle,
                    startDate = startDate,
                    endDate = endDate
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(ZzanZDimen.current.defaultHorizontal)
                ) {
                    when (challengeStatus) {
                        ChallengeStatus.PRE_OPENED -> {
                            GreenRoundButton(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(56.dp),
                                text = stringResource(id = R.string.home_edit_plan_btn_title),
                                onClick = { /*TODO*/ },
                                enabled = true
                            )
                        }

                        ChallengeStatus.OPENED -> {
                            ChallengeResult(messageContent = {
                                ChallengeResultTitleWhenOpened(
                                    prefix = stringResource(id = R.string.home_challenge_result_title_opened_remain_prefix),
                                    suffix = stringResource(id = R.string.home_challenge_result_title_opened_remain_suffix),
                                    amountWithUnit = "50,000원",
                                    amountColor = ZzanZColorPalette.current.Green04
                                )
                            }, ratio = 0.7f)
                        }

                        ChallengeStatus.CLOSED -> {
                            ChallengeResult(messageContent = {
                                ChallengeResultTitleWhenClosed(
                                    message = stringResource(id = R.string.home_challenge_result_title_closed_success)
                                )
                            }, ratio = 0.7f)
                        }
                    }
                }
            }
            if(showDialog){
                PopupSheetDialog { showDialog = false }
            }
        }
    }
}

@Composable
fun HomeContent(
    modifier: Modifier = Modifier,
    challengeTitle: String,
    startDate: String,
    endDate: String
) {
    Column(modifier.fillMaxSize()) {
        WeekPager()
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = ZzanZDimen.current.defaultHorizontal)
        ) {
            ChallengeTitle(challengeTitle, startDate, endDate)
            Spacer(modifier = Modifier.height(28.dp))
            CategoryList()
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun WeekPager() {
    val scope = rememberCoroutineScope()
    val pagerState = rememberPagerState()
    val paddingValue = (LocalConfiguration.current.screenWidthDp - 80) / 2
    HorizontalPager(
        modifier = Modifier.padding(bottom = 28.dp, top = 8.dp),
        pageCount = 10,
        pageSize = PageSize.Fixed(80.dp),
        pageSpacing = 8.dp,
        reverseLayout = true,
        contentPadding = PaddingValues(horizontal = paddingValue.dp),
        state = pagerState
    ) {
        if (pagerState.currentPage == it) {
            PagerFocusedItem(title = "타이틀", challengeStatus = ChallengeStatus.OPENED) {
                scope.launch {
                    pagerState.animateScrollToPage(it)
                }
            }
        } else {
            PagerUnFocusedItem(title = "타이틀", challengeStatus = ChallengeStatus.OPENED) {
                scope.launch {
                    pagerState.animateScrollToPage(it)
                }
            }
        }
    }
}

@Composable
fun ChallengeTitle(
    title: String,
    startDate: String,
    endDate: String
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = title, style = ZzanZTypo.current.H1, color = ZzanZColorPalette.current.Gray09)
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = "$startDate ~ $endDate",
            style = ZzanZTypo.current.Body01,
            color = ZzanZColorPalette.current.Gray05
        )
    }
}

@Composable
fun CategoryList() {
    LazyVerticalGrid(
        columns = GridCells.Fixed(HomeScreenValue.GRID_COUNT),
        modifier = Modifier.fillMaxSize(),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(20) {
            CategoryCardItem(
                title = "식비",
                remainAmount = "50,000원",
                ratio = 0.7f,
                indicatorColor = ZzanZColorPalette.current.Green04
            )
        }
    }
}

@Composable
fun ChallengeResult(
    messageContent: @Composable () -> Unit,
    ratio: Float
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val color =
            if (ratio < 1f) ZzanZColorPalette.current.Green04 else ZzanZColorPalette.current.Red04
        messageContent()
        ProgressIndicator(
            modifier = Modifier.padding(top = 16.dp, bottom = 20.dp),
            color = color,
            ratio = ratio
        )
    }
}

@Composable
fun ChallengeResultTitleWhenOpened(
    prefix: String,
    suffix: String,
    amountWithUnit: String,
    amountColor: Color
) {
    Text(
        buildAnnotatedString {
            withStyle(SpanStyle(color = ZzanZColorPalette.current.Gray08)) {
                append(prefix)
            }
            withStyle(SpanStyle(color = amountColor)) {
                append(" $amountWithUnit ")
            }
            withStyle(SpanStyle(color = ZzanZColorPalette.current.Gray08)) {
                append(suffix)
            }
        },
        style = ZzanZTypo.current.Body02
    )
}

@Composable
fun ChallengeResultTitleWhenClosed(
    message: String
) {
    Text(text = message, color = ZzanZColorPalette.current.Gray08, style = ZzanZTypo.current.Body02)
}

@Preview
@Composable
fun HomePreview() {
    HomeScreen()
}