package com.example.zzanz_android

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.example.zzanz_android.common.Resource
import com.example.zzanz_android.domain.model.FcmTokenModel
import com.example.zzanz_android.domain.usecase.PostFcmTokenUseCase
import com.example.zzanz_android.domain.usecase.preference.GetLastSettingRouteUseCase
import com.example.zzanz_android.domain.usecase.preference.SetFcmTokenUseCase
import com.example.zzanz_android.presentation.view.component.contract.GlobalContract
import com.example.zzanz_android.presentation.view.component.contract.GlobalUiEvent
import com.example.zzanz_android.presentation.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val setTokenUseCase: SetFcmTokenUseCase,
    private val postFcmTokenUseCase: PostFcmTokenUseCase,
    private val getLastSettingRouteUseCase: GetLastSettingRouteUseCase
) : BaseViewModel<GlobalContract.Event, GlobalContract.State, GlobalContract.Effect>() {

    override fun createInitialState(): GlobalContract.State {
        return GlobalContract.State(isLoading = mutableStateOf(false))
    }

    override fun handleEvent(event: GlobalContract.Event) {
        when (event) {
            is GlobalContract.Event.SetFcmToken -> {
                postFcmTokenUseCase(event.token)
            }
        }
    }

    private fun postFcmTokenUseCase(token: String) {
        viewModelScope.launch {
            postFcmTokenUseCase.invoke(
                FcmTokenModel(
                    fcmToken = token,
                    operatingSystem = "ANDROID"
                )
            ).collect {
                when (it) {
                    is Resource.Success -> {
                        if (it.data) {
                            Timber.e("postFcmTokenUseCase Success")
                            setTokenUseCase(token)
                        }
                    }

                    is Resource.Error -> {
                        it.exception.message?.let { message: String ->
                            Timber.e("error - $message")
                            GlobalUiEvent.showToast(message)
                        }
                    }
                }
            }
        }
    }

    private fun setTokenUseCase(token: String) {
        viewModelScope.launch {
            setTokenUseCase.invoke(token).collect {
                when (it) {
                    is Resource.Success -> {
                        if (it.data) {
//                            GlobalUiEvent.showToast("Success Fcm Token Save")
                        }
                    }

                    is Resource.Error -> {
                        it.exception.message?.let { message: String ->
                            Timber.e(message)
                            GlobalUiEvent.showToast(message)
                        }
                    }
                }
            }
        }
    }

}