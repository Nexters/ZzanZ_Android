package com.example.zzanz_android.presentation.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.zzanz_android.MainViewModel
import com.example.zzanz_android.common.navigation.NavRoutes
import com.example.zzanz_android.common.navigation.SettingNavRoutes
import com.example.zzanz_android.common.navigation.SettingType
import com.example.zzanz_android.common.ui.theme.ZzanZTypo
import com.example.zzanz_android.presentation.view.component.contract.GlobalContract
import timber.log.Timber

@Composable
fun Splash(navController: NavHostController, mainViewModel: MainViewModel = hiltViewModel()) {
    mainViewModel.setEvent(GlobalContract.Event.GetSettingLastRoute)

    LaunchedEffect(key1 = Unit, block = {
        mainViewModel.effect.collect { it ->
            when (it) {
                is GlobalContract.Effect.NavigationInvoke -> {
                    navController.navigate(it.route) {
                        navController.popBackStack(NavRoutes.Splash.route, true)
                    }
                }

                else -> {
                }
            }
        }
    })


    Surface(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                text = "Splash Screen", style = ZzanZTypo.current.Caption
            )
            Spacer(modifier = Modifier.height(20.dp))
            Column(
                modifier = Modifier.wrapContentWidth(),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
//                TestNavButton(
//                    route = settingRoute, onButtonClicked = onButtonClicked
//                )
//                TestNavButton(
//                    route = NavRoutes.Home.route, onButtonClicked = onButtonClicked
//                )
//                TestNavButton(
//                    route = NavRoutes.Spending.route + "/1/5000/식비",
//                    onButtonClicked = onButtonClicked
//                )
//                TestNavButton(
//                    route = NavRoutes.Notification.route, onButtonClicked = onButtonClicked
//                )
            }
        }
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