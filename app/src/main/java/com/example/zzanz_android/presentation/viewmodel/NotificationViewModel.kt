package com.example.zzanz_android.presentation.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.example.zzanz_android.common.Resource
import com.example.zzanz_android.domain.model.NotificationModel
import com.example.zzanz_android.domain.usecase.PostNotificationUseCase
import com.example.zzanz_android.domain.usecase.preference.GetFcmTokenUseCase
import com.example.zzanz_android.presentation.view.component.contract.GlobalUiEvent
import com.example.zzanz_android.presentation.view.component.contract.NotificationContract
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class NotificationViewModel @Inject constructor(
    private val postNotificationUseCase: PostNotificationUseCase,
    private val getFcmTokenUseCase: GetFcmTokenUseCase
) : BaseViewModel<NotificationContract.Event, NotificationContract.State, NotificationContract.Effect>() {
    override fun createInitialState(): NotificationContract.State {
        return NotificationContract.State(
            hour = mutableStateOf(22), minute = mutableStateOf(0)
        )
    }

    override fun handleEvent(event: NotificationContract.Event) {
        when (event) {
            is NotificationContract.Event.SetNotificationTime -> {
                setNotificationTime(isHour = event.isHour, num = event.num)
            }

            is NotificationContract.Event.OnNextButtonClicked -> {
                getFcmTokenUseCase()
            }
        }
    }

    private fun getFcmTokenUseCase() {
        viewModelScope.launch {
            getFcmTokenUseCase.invoke(null).collect { it ->
                when (it) {
                    is Resource.Success -> {
                        it.data?.let { token: String ->
                            GlobalUiEvent.showToast("getFcmTokenUseCase - Success")
                            Timber.e("getFcmTokenUseCase - $token")
                            callPostNotificationUseCase(token)
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

    private fun callPostNotificationUseCase(token: String) {
        viewModelScope.launch {
            postNotificationUseCase.invoke(
                NotificationModel(
                    fcmToken = token,
                    operatingSystem = "ANDROID",
                    notificationHour = uiState.value.hour.value,
                    notificationMinute = uiState.value.minute.value
                )

            ).collect {
                when (it) {
                    is Resource.Success -> {
                        if (it.data) {
                            GlobalUiEvent.showToast("postNotificationConfig - Success")
                            setEffect(NotificationContract.Effect.NextRoutes)
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

    private fun setNotificationTime(isHour: Boolean, num: Int) {
        if (isHour) {
            setState(currentState.copy(hour = mutableStateOf(num)))
        } else {
            setState(currentState.copy(minute = mutableStateOf(num)))
        }
    }
}