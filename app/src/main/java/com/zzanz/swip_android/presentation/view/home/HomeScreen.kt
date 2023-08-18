package com.zzanz.swip_android.presentation.view.home

import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.zzanz.swip_android.R
import com.zzanz.swip_android.common.navigation.NavRoutes
import com.zzanz.swip_android.common.navigation.SettingNavRoutes
import com.zzanz.swip_android.common.navigation.SettingType
import com.zzanz.swip_android.common.ui.theme.ZzanZColorPalette
import com.zzanz.swip_android.common.ui.theme.ZzanZDimen
import com.zzanz.swip_android.common.ui.theme.ZzanZTypo
import com.zzanz.swip_android.common.util.BrowserManager
import com.zzanz.swip_android.domain.model.Category
import com.zzanz.swip_android.domain.model.ChallengeModel
import com.zzanz.swip_android.domain.model.ChallengeStatus
import com.zzanz.swip_android.domain.model.PlanModel
import com.zzanz.swip_android.domain.util.DateFormatter
import com.zzanz.swip_android.domain.util.MoneyFormatter
import com.zzanz.swip_android.presentation.view.component.AppBarWithMoreAction
import com.zzanz.swip_android.presentation.view.component.CategoryCardItem
import com.zzanz.swip_android.presentation.view.component.GreenRoundButton
import com.zzanz.swip_android.presentation.view.component.MoreBottomSheet
import com.zzanz.swip_android.presentation.view.component.PagerFocusedItem
import com.zzanz.swip_android.presentation.view.component.PagerUnFocusedItem
import com.zzanz.swip_android.presentation.view.component.ProgressIndicator
import com.zzanz.swip_android.presentation.viewmodel.ChallengeListState
import com.zzanz.swip_android.presentation.viewmodel.HomeEffect
import com.zzanz.swip_android.presentation.viewmodel.HomeViewModel
import com.zzanz.swip_android.presentation.viewmodel.PlanListUiEvent
import com.zzanz.swip_android.presentation.viewmodel.PlanListViewModel
import kotlinx.coroutines.launch

object HomeScreenValue {
    const val GRID_COUNT = 2
}

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    homeViewModel: HomeViewModel = hiltViewModel(),
    planListViewModel: PlanListViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val challengeListState by homeViewModel.uiState.collectAsState()
    val effect by homeViewModel.effect.collectAsState(initial = null)

    val scope = rememberCoroutineScope()
    val bottomSheetState = rememberModalBottomSheetState()

    val systemUiController = rememberSystemUiController()
    systemUiController.setStatusBarColor(ZzanZColorPalette.current.Gray01)

    Scaffold(
        modifier = Modifier,
        topBar = {
            AppBarWithMoreAction(ZzanZColorPalette.current.Gray01) {
                scope.launch { bottomSheetState.expand() }
            }
        },
        containerColor = ZzanZColorPalette.current.Gray01
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            when (challengeListState.challengeList) {
                is ChallengeListState.Loading -> {
                    CircularProgressIndicator(
                        Modifier.align(Alignment.Center),
                        color = ZzanZColorPalette.current.Green04
                    )
                }

                is ChallengeListState.Success -> {
                    val challengeList =
                        (challengeListState.challengeList as ChallengeListState.Success).data.collectAsLazyPagingItems()
                    val challengeStatus = remember { mutableStateOf(ChallengeStatus.CLOSED) }
                    val pagerState = rememberPagerState { challengeList.itemCount }
                    when (challengeList.loadState.refresh) {
                        is LoadState.Loading -> {
                            CircularProgressIndicator(
                                Modifier.align(Alignment.Center),
                                color = ZzanZColorPalette.current.Green04
                            )
                        }

                        is LoadState.Error -> {
                            homeViewModel.setEffectToShowToast()
                        }

                        else -> {
                            Column(Modifier.fillMaxSize()) {
                                HomeContent(
                                    modifier = Modifier.weight(1f),
                                    pagerState = pagerState,
                                    pagingItems = challengeList,
                                    setCurrentChallenge = { challenge ->
                                        challengeStatus.value = challenge.state
                                        planListViewModel.setEvent(
                                            PlanListUiEvent.SetPlanList(
                                                challenge.planList
                                            )
                                        )
                                    },
                                    onClickItem = { planId ->
                                        navController.navigate(NavRoutes.Category.route + "/${planId}/${challengeStatus.value.name}")
                                    }
                                )
                                if (challengeStatus.value == ChallengeStatus.PRE_OPENED) {
                                    GreenRoundButton(
                                        modifier = Modifier
                                            .padding(ZzanZDimen.current.defaultHorizontal)
                                            .fillMaxWidth()
                                            .height(56.dp),
                                        text = stringResource(id = R.string.home_edit_plan_btn_title),
                                        onClick = {
                                            navController.navigate(SettingNavRoutes.Budget.route + "?${SettingType.home}")
                                        },
                                        enabled = true
                                    )
                                }
                            }
                        }
                    }
                }

                else -> {}
            }
            if (bottomSheetState.isVisible) {
                MoreBottomSheet(
                    scope = scope,
                    state = bottomSheetState,
                    onClickChangeAlarm = {
                        navController.navigate(NavRoutes.Notification.route + "?${SettingType.home}")
                    },
                    onClickSendFeedback = { BrowserManager.openFeedbackPage(context) },
                    onClickTerms = { BrowserManager.openTermsPage(context) },
                )
            }
            if (effect is HomeEffect.ShowToast) {
                Toast.makeText(context, (effect as HomeEffect.ShowToast).message, Toast.LENGTH_LONG)
                    .show()
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeContent(
    modifier: Modifier = Modifier,
    pagerState: PagerState,
    pagingItems: LazyPagingItems<ChallengeModel>,
    setCurrentChallenge: (ChallengeModel) -> Unit,
    onClickItem: (Int) -> Unit
) {
    val context = LocalContext.current
    var title by remember { mutableStateOf("") }
    var dday by remember { mutableStateOf<Int?>(null) }
    var subTitle by remember { mutableStateOf("") }
    val planList = remember { mutableStateOf(listOf<PlanModel>()) }
    val challengeStatus = remember { mutableStateOf(ChallengeStatus.OPENED) }
    val goalAmount = remember { mutableStateOf(0) }
    val remainAmount = remember { mutableStateOf(0) }
    val ratio = remember { mutableStateOf(0f) }

    LazyColumn(modifier.fillMaxSize()) {
        item {
            WeekPager(pagerState, pagingItems) {
                pagingItems[pagerState.currentPage]?.let { challenge ->
                    setCurrentChallenge(challenge)
                    title = when (challenge.state) {
                        ChallengeStatus.PRE_OPENED -> context.getString(R.string.home_challenge_title_pre_opened)
                        ChallengeStatus.OPENED -> context.getString(R.string.home_challenge_title_opened)
                        ChallengeStatus.CLOSED -> context.getString(R.string.home_challenge_title_closed)
                    }
                    dday = if (challenge.state == ChallengeStatus.OPENED) {
                        DateFormatter.calculateDday(challenge.endAt)
                    } else {
                        null
                    }
                    subTitle =
                        "${DateFormatter.format(challenge.startAt)} ~ ${
                            DateFormatter.format(
                                challenge.endAt
                            )
                        }"
                    planList.value = challenge.planList
                    challengeStatus.value = challenge.state
                    goalAmount.value = challenge.goalAmount
                    remainAmount.value = challenge.remainAmount
                    ratio.value =
                        if (challenge.remainAmount < 0) 0f else (challenge.remainAmount.toFloat() / challenge.goalAmount.toFloat())
                }
            }
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = ZzanZDimen.current.defaultHorizontal)
            ) {
                ChallengeTitle(title, subTitle, dday = dday)
                Spacer(modifier = Modifier.height(28.dp))
            }
        }
        item {
            CategoryList(
                planList.value,
                if (challengeStatus.value == ChallengeStatus.PRE_OPENED) {
                    { }
                } else {
                    onClickItem
                }
            )
        }
        item {
            Spacer(modifier = Modifier.height(10.dp))
            when (challengeStatus.value) {
                ChallengeStatus.OPENED -> {
                    ChallengeResult(messageContent = {
                        if (remainAmount.value < 0f) {
                            ChallengeResultTitleWhenOpened(
                                prefix = stringResource(id = R.string.home_challenge_result_title_opened_over_prefix),
                                suffix = stringResource(id = R.string.home_challenge_result_title_opened_over_suffix),
                                amount = MoneyFormatter.format(remainAmount.value * -1),
                                amountColor = ZzanZColorPalette.current.Red04
                            )
                        } else {
                            ChallengeResultTitleWhenOpened(
                                prefix = stringResource(id = R.string.home_challenge_result_title_opened_remain_prefix),
                                suffix = stringResource(id = R.string.home_challenge_result_title_opened_remain_suffix),
                                amount = MoneyFormatter.format(remainAmount.value),
                                amountColor = ZzanZColorPalette.current.Green04
                            )
                        }
                    }, ratio = ratio.value)
                }

                ChallengeStatus.CLOSED -> {
                    ChallengeResult(messageContent = {
                        ChallengeResultTitleWhenClosed(
                            message = if (remainAmount.value < 0f) stringResource(id = R.string.home_challenge_result_title_closed_fail) else stringResource(
                                id = R.string.home_challenge_result_title_closed_success
                            )
                        )
                    }, ratio = ratio.value)
                }

                else -> {}
            }
            Spacer(modifier = Modifier.height(25.dp))
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun WeekPager(
    pagerState: PagerState,
    pagingItems: LazyPagingItems<ChallengeModel>,
    setCurrentPage: (Int) -> Unit
) {
    val scope = rememberCoroutineScope()
    val paddingValue = (LocalConfiguration.current.screenWidthDp - 90) / 2
    HorizontalPager(
        modifier = Modifier.padding(bottom = 28.dp, top = 8.dp),
        pageSpacing = 8.dp,
        reverseLayout = true,
        contentPadding = PaddingValues(horizontal = paddingValue.dp),
        state = pagerState
    ) {
        setCurrentPage(it)
        val challenge = remember { pagingItems[it] }
        val title = challenge?.let { challenge ->
            when (challenge.state) {
                ChallengeStatus.PRE_OPENED -> stringResource(id = R.string.next_week)
                ChallengeStatus.OPENED -> stringResource(id = R.string.this_week)
                ChallengeStatus.CLOSED -> {
                    if (pagingItems[it - 1]?.state == ChallengeStatus.OPENED) {
                        stringResource(id = R.string.pre_week)
                    } else {
                        "${challenge.month}${stringResource(id = R.string.month)} ${challenge.week}${
                            stringResource(
                                id = R.string.week
                            )
                        }"
                    }
                }
            }
        }
        if (pagerState.currentPage == it) {
            PagerFocusedItem(
                title = title ?: "",
                challengeStatus = challenge?.state ?: ChallengeStatus.OPENED
            ) {
                scope.launch {
                    pagerState.animateScrollToPage(it)
                }
            }
        } else {
            PagerUnFocusedItem(
                title = title ?: "",
                challengeStatus = challenge?.state ?: ChallengeStatus.CLOSED
            ) {
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
    subtitle: String,
    dday: Int? = null
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            buildAnnotatedString {
                withStyle(SpanStyle(color = ZzanZColorPalette.current.Gray09)) {
                    append(title)
                }
                dday?.let {
                    withStyle(SpanStyle(color = ZzanZColorPalette.current.Green04)) {
                        append(" ${stringResource(id = R.string.home_challenge_title_opened_dday)}$it")
                    }
                }
            },
            style = ZzanZTypo.current.H1,
        )
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = subtitle,
            style = ZzanZTypo.current.Body01,
            color = ZzanZColorPalette.current.Gray05
        )
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun CategoryList(
    planList: List<PlanModel>,
    onClickItem: (Int) -> Unit
) {
    val horizontalSpace = 16.dp
    // TODO : 추후 개선 사항 ( 스크롤 영역에 따라서 그리드로 변경 )
    val itemWidth =
        (LocalConfiguration.current.screenWidthDp.dp - (ZzanZDimen.current.defaultHorizontal * 2) - horizontalSpace - 10.dp) / 2
    FlowRow(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = ZzanZDimen.current.defaultHorizontal),
        horizontalArrangement = Arrangement.spacedBy(horizontalSpace),
        maxItemsInEachRow = HomeScreenValue.GRID_COUNT
    ) {
        planList.forEach {
            CategoryCardItem(
                itemWidth = itemWidth,
                title = stringResource(id = Category.valueOf(it.category).stringResId),
                remainAmount = it.remainAmount,
                ratio = if (it.remainAmount <= 0) 0f else (it.remainAmount.toFloat() / it.goalAmount.toFloat()),
                onClickItem = { onClickItem(it.id) }
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
            if (ratio > 1f) ZzanZColorPalette.current.Red04 else ZzanZColorPalette.current.Green04
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
    amount: String,
    amountColor: Color
) {
    Text(
        buildAnnotatedString {
            withStyle(SpanStyle(color = ZzanZColorPalette.current.Gray08)) {
                append(prefix)
            }
            withStyle(SpanStyle(color = amountColor)) {
                append(" $amount${stringResource(id = R.string.money_unit)} ")
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
    Text(
        text = message,
        color = ZzanZColorPalette.current.Gray08,
        style = ZzanZTypo.current.Body02
    )
}

@Preview
@Composable
fun HomePreview() {
    ChallengeResult(messageContent = {
        ChallengeResultTitleWhenOpened(
            prefix = stringResource(id = R.string.home_challenge_result_title_opened_remain_prefix),
            suffix = stringResource(id = R.string.home_challenge_result_title_opened_remain_suffix),
            amount = "50,000원",
            amountColor = ZzanZColorPalette.current.Green04
        )
    }, ratio = 0.7f)
}