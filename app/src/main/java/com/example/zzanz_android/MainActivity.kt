package com.example.zzanz_android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.zzanz_android.common.navigation.NavHost
import com.example.zzanz_android.common.ui.theme.ZzanZ_AndroidTheme
import com.example.zzanz_android.common.ui.util.keyboardAsState
import com.example.zzanz_android.presentation.view.component.Toast
import com.example.zzanz_android.presentation.view.component.contract.GlobalContract
import com.example.zzanz_android.presentation.view.component.contract.GlobalUiEvent
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val isKeyboardOpen by keyboardAsState()
            var toastState by remember { mutableStateOf("") }
            var job: Job? = null
            val scope = rememberCoroutineScope()
            val mainViewModel: MainViewModel = hiltViewModel()

            SetFirebaseToken(mainViewModel)
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

                                else -> {}
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

@Composable
private fun SetFirebaseToken(
    mainViewModel: MainViewModel = hiltViewModel()
) {
    FirebaseMessaging.getInstance().token.addOnCompleteListener(
        OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Timber.e("Fetching FCM registration token failed ${task.exception}")
                return@OnCompleteListener
            }

            // Get new FCM registration token
            val token = task.result
            mainViewModel.setEvent(GlobalContract.Event.SetFcmToken(token))

            // Log and toast
            Timber.e("Token $token")
        }
    )
}

@Preview(showBackground = true)
@Composable
fun MainPreview() {
    ZzanZ_AndroidTheme {
        NavHost()
    }
}