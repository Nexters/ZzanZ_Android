package com.example.zzanz_android.presentation.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.zzanz_android.R
import com.example.zzanz_android.common.navigation.SplashNavRoutes
import com.example.zzanz_android.common.ui.theme.ZzanZColorPalette
import com.example.zzanz_android.common.ui.theme.ZzanZTypo
import com.example.zzanz_android.presentation.view.component.BottomGreenButton
import com.example.zzanz_android.presentation.view.component.contract.SplashContract
import com.example.zzanz_android.presentation.viewmodel.OnBoardingViewModel

@Composable
fun OnBoarding(
    navController: NavHostController,
    route: String = SplashNavRoutes.ExplainService.route,
    onBoardingViewModel: OnBoardingViewModel = hiltViewModel()
) {

    val uiData = onBoardingViewModel.uiState.collectAsState().value.uiData

    onBoardingViewModel.setEvent(SplashContract.Event.SetSplashType(route))

    LaunchedEffect(key1 = Unit, block = {
        onBoardingViewModel.effect.collect { it ->
            when (it) {
                is SplashContract.Effect.NextRoutes -> {
                    navController.navigate(it.route) {
                        if (it.route != SplashNavRoutes.ChallengeStart.route) {
                            popUpTo(navController.graph.id) {
                                inclusive = true
                            }
                        }
                    }
                }
            }
        }
    })


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(ZzanZColorPalette.current.White),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(90.dp))
        uiData.explainContent?.let {
            Text(
                text = stringResource(id = it),
                style = ZzanZTypo.current.Body01.copy(color = ZzanZColorPalette.current.Gray06),
                textAlign = TextAlign.Center
            )
        }
        Spacer(modifier = Modifier.height(13.dp))
        Text(
            text = stringResource(id = uiData.titleText),
            style = ZzanZTypo.current.H1.copy(fontSize = 28.sp, lineHeight = 40.sp),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.weight(1f))
        val contentScale =
            if (uiData.currentRoute == SplashNavRoutes.ExplainService.route) ContentScale.FillWidth else ContentScale.None
        uiData.contentImage?.let {
            Image(
                modifier = Modifier.fillMaxWidth(),
                painter = painterResource(it),
                contentDescription = null,
                contentScale = contentScale
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        BottomGreenButton(
            buttonText = stringResource(id = uiData.buttonText),
            onClick = {
                onBoardingViewModel.setEvent(
                    SplashContract.Event.OnNextButtonClicked
                )
            },
            isButtonEnabled = true,
            isKeyboardOpen = false,
            horizontalWidth = 24.dp,
            buttonIcon = if (route == SplashNavRoutes.ChallengeStart.route) R.drawable.icon_fighting else null
        )
    }
}

@Preview
@Composable
fun OnBoardingPreview() {
    OnBoarding(navController = rememberNavController())
}