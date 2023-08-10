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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.zzanz_android.common.navigation.NavRoutes
import com.example.zzanz_android.common.ui.theme.ZzanZTypo

@Composable
fun Splash(navController: NavHostController) {
    // NavHostController TestCode
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
                TestNavButton(route = NavRoutes.Setting.route, navController = navController)
                TestNavButton(route = NavRoutes.Home.route, navController = navController)
                TestNavButton(route = NavRoutes.Spending.route + "/1/5000", navController = navController)
            }
        }
    }
}

@Composable
fun TestNavButton(route: String, navController: NavHostController, modifier: Modifier = Modifier) {
    Button(
        onClick = {
            navController.navigate(route) {
                popUpTo(route) {
                    inclusive = true
                }
            }
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