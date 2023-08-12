package com.example.zzanz_android.presentation.view.category

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.zzanz_android.R
import com.example.zzanz_android.common.navigation.NavRoutes
import com.example.zzanz_android.common.ui.theme.ZzanZColorPalette
import com.example.zzanz_android.common.ui.theme.ZzanZDimen
import com.example.zzanz_android.common.ui.theme.ZzanZTypo
import com.example.zzanz_android.domain.model.Category
import com.example.zzanz_android.domain.model.ChallengeStatus
import com.example.zzanz_android.domain.model.SpendingModel
import com.example.zzanz_android.domain.util.MoneyFormatter
import com.example.zzanz_android.presentation.view.component.AddSpendingComponent
import com.example.zzanz_android.presentation.view.component.AppBarWithBackNavigation
import com.example.zzanz_android.presentation.view.component.SpendingItemComponent
import com.example.zzanz_android.presentation.viewmodel.CategoryEffect
import com.example.zzanz_android.presentation.viewmodel.CategoryViewModel
import com.example.zzanz_android.presentation.viewmodel.SpendingListByPlanState

@Composable
fun CategoryScreen(
    navController: NavController,
    viewModel: CategoryViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val categoryState by viewModel.uiState.collectAsState()
    val effect by viewModel.effect.collectAsState(null)
    Scaffold(
        topBar = {
            AppBarWithBackNavigation {
                navController.popBackStack()
            }
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            when (categoryState.spendingListByPlanState) {
                is SpendingListByPlanState.Loading -> {
                    CircularProgressIndicator(
                        Modifier.align(Alignment.Center),
                        color = ZzanZColorPalette.current.Green04
                    )
                }

                is SpendingListByPlanState.Success -> {
                    val spendingList: LazyPagingItems<SpendingModel> =
                        (categoryState.spendingListByPlanState as SpendingListByPlanState.Success).spendingList.collectAsLazyPagingItems()
                    when (spendingList.loadState.refresh) {
                        is LoadState.Loading -> {
                            CircularProgressIndicator(
                                Modifier.align(Alignment.Center),
                                color = ZzanZColorPalette.current.Green04
                            )
                        }

                        is LoadState.Error -> {
                            viewModel.setEffectShowErrorToast()
                        }

                        else -> {
                            val planInfo by
                            (categoryState.spendingListByPlanState as SpendingListByPlanState.Success).planInfo.collectAsState(
                                null
                            )
                            planInfo?.let { plan ->
                                LazyColumn(modifier = Modifier.padding(horizontal = ZzanZDimen.current.defaultHorizontal)) {
                                    item {
                                        Title(
                                            plan.remainAmount,
                                            plan.category
                                        )
                                        SubTitle(
                                            stringResource(id = Category.valueOf(plan.category).stringResId),
                                            MoneyFormatter.format(plan.goalAmount)
                                        )
                                        Spacer(modifier = Modifier.height(32.dp))
                                        if (categoryState.challengeStatus == ChallengeStatus.OPENED) {
                                            AddSpendingComponent { navController.navigate(NavRoutes.Spending.route + "/${plan.id}/${plan.remainAmount}/${plan.category}") }
                                        }
                                    }

                                    items(spendingList.itemCount) { page ->
                                        spendingList[page]?.let { spendingModel ->
                                            SpendingItemComponent(
                                                plan.category,
                                                spendingModel.title,
                                                spendingModel.memo ?: "",
                                                MoneyFormatter.format(spendingModel.amount)
                                            )
                                        }
                                    }

                                    item {
                                        Spacer(modifier = Modifier.height(25.dp))
                                    }
                                }
                            }
                        }
                    }
                }

                is SpendingListByPlanState.Error -> {
                    viewModel.setEffectShowErrorToast()
                }
            }

            if (effect is CategoryEffect.ShowErrorToast) {
                Toast.makeText(
                    context,
                    (effect as CategoryEffect.ShowErrorToast).message,
                    Toast.LENGTH_LONG
                )
                    .show()
            }
        }
    }
}

@Composable
fun Title(remainAmount: Int, category: String) {
    val isOver = remainAmount < 0
    val titleComponent = if(isOver) {
        TitleComponent(
            stringResource(id = R.string.category_remain_amount_over_title_prefix, stringResource(id = Category.valueOf(category).stringResId)),
            stringResource(id = R.string.category_remain_amount_over_title_suffix),
            MoneyFormatter.format(remainAmount * -1),
            ZzanZColorPalette.current.Red04

        )
    } else {
        TitleComponent(
            stringResource(id = R.string.category_remain_amount_remain_title_prefix, stringResource(id = Category.valueOf(category).stringResId)),
            stringResource(id = R.string.category_remain_amount_remain_title_suffix),
            MoneyFormatter.format(remainAmount),
            ZzanZColorPalette.current.Green04
        )
    }
    Surface(modifier = Modifier.padding(top = 8.dp)) {
        Text(
            buildAnnotatedString {
                withStyle(SpanStyle(color = ZzanZColorPalette.current.Black)) {
                    append(titleComponent.prefix)
                }
                withStyle(SpanStyle(color = titleComponent.color)) {
                    append("\n${titleComponent.remainAmount}${stringResource(id = R.string.money_unit)} ")
                }
                withStyle(SpanStyle(color = ZzanZColorPalette.current.Black)) {
                    append(titleComponent.suffix)
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

data class TitleComponent(
    val prefix: String,
    val suffix: String,
    val remainAmount: String,
    val color: Color
)

@Preview
@Composable
fun CategoryPreview() {
    CategoryScreen(rememberNavController())
}