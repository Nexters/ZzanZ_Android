package com.example.zzanz_android.presentation.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.zzanz_android.R
import com.example.zzanz_android.common.navigation.NavRoutes
import com.example.zzanz_android.common.navigation.SplashNavRoutes
import com.example.zzanz_android.common.ui.theme.ZzanZColorPalette
import com.example.zzanz_android.common.ui.theme.ZzanZTypo
import com.example.zzanz_android.common.ui.util.ImageViewWithXml
import com.example.zzanz_android.presentation.view.component.BottomGreenButton
import com.example.zzanz_android.presentation.view.component.contract.SplashContract
import com.example.zzanz_android.presentation.viewmodel.SplashViewModel

@Composable
fun Splash(
    navController: NavHostController,
    route: String = SplashNavRoutes.ExplainService.route,
    splashViewModel: SplashViewModel = hiltViewModel()
) {

    val uiData = splashViewModel.uiState.collectAsState().value.uiData

    splashViewModel.setEvent(SplashContract.Event.SetSplashType(route))

    LaunchedEffect(key1 = Unit, block = {
        splashViewModel.effect.collect { it ->
            when (it) {
                is SplashContract.Effect.NextRoutes -> {
                    navController.navigate(it.route)
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
        uiData.contentImage?.let {
            ImageViewWithXml(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.TopCenter,
                resId = it
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        BottomGreenButton(
            buttonText = stringResource(id = uiData.buttonText),
            onClick = {
                splashViewModel.setEvent(
                    SplashContract.Event.OnNextButtonClicked
                )
            },
            isButtonEnabled = true,
            isKeyboardOpen = false,
            horizontalWidth = 24.dp,
            buttonIcon = if (route == SplashNavRoutes.ChallengeStart.route)
                R.drawable.icon_fighting else null
        )
    }
}

@Composable
fun TestNavButton(
    route: String, modifier: Modifier = Modifier, onButtonClicked: (String) -> Unit
) {
    Button(
        onClick = {
            onButtonClicked.invoke(route)
        },
        modifier = modifier.wrapContentSize(),
    ) {
        Text(
            text = "Go to ${route}"
        )
    }
}

@Preview
@Composable
fun SplashScreen() {
    Splash(navController = rememberNavController())
}