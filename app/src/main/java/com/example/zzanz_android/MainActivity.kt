package com.example.zzanz_android

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.zzanz_android.common.navigation.NavHost
import com.example.zzanz_android.common.ui.theme.ZzanZ_AndroidTheme
import com.example.zzanz_android.common.ui.util.keyboardAsState
import com.example.zzanz_android.presentation.contract.GlobalContract
import com.example.zzanz_android.presentation.contract.GlobalUiEvent
import com.example.zzanz_android.presentation.view.component.Toast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val isKeyboardOpen by keyboardAsState()
            var toastState by remember { mutableStateOf("") }
            var job: Job? = null
            val scope = rememberCoroutineScope()

            ZzanZ_AndroidTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    LaunchedEffect(key1 = isKeyboardOpen, block = {})
                    LaunchedEffect(key1 = Unit) {
                        GlobalUiEvent.uiEffect.collect {
                            Timber.d("### Collected GlobalContract Effect - $it")
                            when (it) {
                                is GlobalContract.Effect.ShowToast -> {
                                    job?.cancel()
                                    job = scope.launch {
                                        if (it.message.isNotBlank()) {
                                            toastState = it.message
                                            delay(2000L)
                                            toastState = ""
                                        }
                                    }
                                }
                            }
                        }
                    }
                    NavHost()
                    Toast(
                        modifier = Modifier,
                        message = toastState,
                        isVisible = toastState.isNotEmpty(),
                    )
                    if (isKeyboardOpen) {
                        Spacer(modifier = Modifier.padding(WindowInsets.ime.getBottom(LocalDensity.current).dp))
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainPreview() {
    ZzanZ_AndroidTheme {
        NavHost()
    }
}