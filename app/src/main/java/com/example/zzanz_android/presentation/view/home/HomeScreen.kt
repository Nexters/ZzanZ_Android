package com.example.zzanz_android.presentation.view.home

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
import androidx.compose.foundation.pager.PageSize
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
import com.example.zzanz_android.R
import com.example.zzanz_android.common.navigation.NavRoutes
import com.example.zzanz_android.common.navigation.SettingType
import com.example.zzanz_android.common.ui.theme.ZzanZColorPalette
import com.example.zzanz_android.common.ui.theme.ZzanZDimen
import com.example.zzanz_android.common.ui.theme.ZzanZTypo
import com.example.zzanz_android.domain.model.Category
import com.example.zzanz_android.domain.model.ChallengeModel
import com.example.zzanz_android.domain.model.ChallengeStatus
import com.example.zzanz_android.domain.model.PlanModel
import com.example.zzanz_android.domain.util.DateFormatter
import com.example.zzanz_android.domain.util.MoneyFormatter
import com.example.zzanz_android.presentation.view.component.AppBarWithMoreAction
import com.example.zzanz_android.presentation.view.component.CategoryCardItem
import com.example.zzanz_android.presentation.view.component.GreenRoundButton
import com.example.zzanz_android.presentation.view.component.MoreBottomSheet
import com.example.zzanz_android.presentation.view.component.PagerFocusedItem
import com.example.zzanz_android.presentation.view.component.PagerUnFocusedItem
import com.example.zzanz_android.presentation.view.component.ProgressIndicator
import com.example.zzanz_android.presentation.viewmodel.ChallengeListState
import com.example.zzanz_android.presentation.viewmodel.HomeEffect
import com.example.zzanz_android.presentation.viewmodel.HomeViewModel
import com.example.zzanz_android.presentation.viewmodel.PlanListLoadingState
import com.example.zzanz_android.presentation.viewmodel.PlanListUiEvent
import com.example.zzanz_android.presentation.viewmodel.PlanListViewModel
import kotlinx.coroutines.launch
import java.lang.Float.min

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
    val planListState by planListViewModel.uiState.collectAsState()
    val effect by homeViewModel.effect.collectAsState(initial = null)

    val scope = rememberCoroutineScope()
    val bottomSheetState = rememberModalBottomSheetState()

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
                    val pagerState = rememberPagerState()
                    val challengeList =
                        (challengeListState.challengeList as ChallengeListState.Success).data.collectAsLazyPagingItems()
                    val challengeStatus = remember { mutableStateOf(ChallengeStatus.CLOSED) }

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
                                    planList = if (planListState.planListLoadingState is PlanListLoadingState.Loaded) {
                                        (planListState.planListLoadingState as PlanListLoadingState.Loaded).planList
                                    } else {
                                        emptyList()
                                    },
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
                                            navController.navigate(NavRoutes.Notification.route + "/${SettingType.home}")
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
                        navController.navigate(NavRoutes.Notification.route + "/${SettingType.home}")
                    },
                    onClickSendFeedback = { /*TODO*/ },
                    onClickJoinCommunity = { /* TODO */ },
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
    planList: List<PlanModel>,
    setCurrentChallenge: (ChallengeModel) -> Unit,
    onClickItem: (Int) -> Unit
) {
    val context = LocalContext.current
    var title by remember { mutableStateOf("") }
    var dday by remember { mutableStateOf<Int?>(null) }
    var subTitle by remember { mutableStateOf("") }
    val planList = remember { mutableStateOf(planList) }
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
                        (challenge.remainAmount.toFloat() / challenge.goalAmount.toFloat())
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
            CategoryList(planList.value, onClickItem)
        }
        item {
            Spacer(modifier = Modifier.height(10.dp))
            when (challengeStatus.value) {
                ChallengeStatus.OPENED -> {
                    ChallengeResult(messageContent = {
                        ChallengeResultTitleWhenOpened(
                            prefix = stringResource(id = R.string.home_challenge_result_title_opened_remain_prefix),
                            suffix = stringResource(id = R.string.home_challenge_result_title_opened_remain_suffix),
                            amount = MoneyFormatter.format(remainAmount.value),
                            amountColor = if (ratio.value > 1f) ZzanZColorPalette.current.Red04 else ZzanZColorPalette.current.Green04
                        )
                    }, ratio = min(ratio.value, 1f))
                }

                ChallengeStatus.CLOSED -> {
                    ChallengeResult(messageContent = {
                        ChallengeResultTitleWhenClosed(
                            message = stringResource(id = R.string.home_challenge_result_title_closed_success)
                        )
                    }, ratio = min(ratio.value, 1f))
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
        pageCount = pagingItems.itemCount,
        pageSize = PageSize.Fixed(90.dp),
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
    val itemWidth =
        (LocalConfiguration.current.screenWidthDp.dp - (ZzanZDimen.current.defaultHorizontal * 2) - horizontalSpace) / 2
    FlowRow(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = ZzanZDimen.current.defaultHorizontal),
        horizontalArrangement = Arrangement.spacedBy(horizontalSpace),
        maxItemsInEachRow = HomeScreenValue.GRID_COUNT
    ) {
        planList.forEach {
            val (amount, color) = if (it.remainAmount < 0) Pair(
                it.goalAmount,
                ZzanZColorPalette.current.Red04
            ) else Pair(it.remainAmount, ZzanZColorPalette.current.Green04)
            CategoryCardItem(
                itemWidth = itemWidth,
                title = stringResource(id = Category.valueOf(it.category).stringResId),
                remainAmount = MoneyFormatter.format(amount),
                ratio = (it.remainAmount.toFloat() / it.goalAmount.toFloat()),
                indicatorColor = color,
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