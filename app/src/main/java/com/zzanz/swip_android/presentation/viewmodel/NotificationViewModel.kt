package com.zzanz.swip_android.presentation.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.zzanz.swip_android.R
import com.zzanz.swip_android.common.Resource
import com.zzanz.swip_android.common.navigation.NavRoutes
import com.zzanz.swip_android.common.navigation.SettingType
import com.zzanz.swip_android.domain.model.FcmTokenModel
import com.zzanz.swip_android.domain.model.NotificationTimeModel
import com.zzanz.swip_android.domain.usecase.PostFcmTokenUseCase
import com.zzanz.swip_android.domain.usecase.PostNotificationTimeUseCase
import com.zzanz.swip_android.domain.usecase.preference.GetFcmTokenUseCase
import com.zzanz.swip_android.domain.usecase.preference.GetNotificationTimeUseCase
import com.zzanz.swip_android.domain.usecase.preference.SetLastSettingRouteUseCase
import com.zzanz.swip_android.domain.usecase.preference.SetNotificationTimeUseCase
import com.zzanz.swip_android.presentation.view.component.contract.NotificationContract
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class NotificationViewModel @Inject constructor(
    private val postNotificationTimeUseCase: PostNotificationTimeUseCase,
    private val getNotifiCationTimeUseCase: GetNotificationTimeUseCase,
    private val setNotifiCationTimeUseCase: SetNotificationTimeUseCase,
    private val getFcmTokenUseCase: GetFcmTokenUseCase,
    private val postFcmTokenUseCase: PostFcmTokenUseCase,
    private val settingRouteUseCase: SetLastSettingRouteUseCase
) : BaseViewModel<NotificationContract.Event, NotificationContract.State, NotificationContract.Effect>() {

    override fun createInitialState(): NotificationContract.State {
        return NotificationContract.State(
            isLoading = mutableStateOf(true),
            hour = mutableStateOf(22),
            minute = mutableStateOf(0),
            title = mutableStateOf(R.string.set_notification_time_title)
        )
    }

    override fun handleEvent(event: NotificationContract.Event) {
        when (event) {
            is NotificationContract.Event.SetNotificationTime -> {
                setNotificationTime(isHour = event.isHour, num = event.num)
            }

            is NotificationContract.Event.GetNotificationTime -> {
                getNotificationTimeUseCase()
            }

            is NotificationContract.Event.OnNextButtonClicked -> {
                getFcmTokenUseCase()
            }

            is NotificationContract.Event.SetSettingType -> {
                setTitle(event.settingType)
            }
        }
    }

    private fun setTitle(settingType: String?) {
        if (settingType == SettingType.home) {
            setState(currentState.copy(title = mutableStateOf(R.string.edit_notification_time_title)))
        } else {
            setState(currentState.copy(title = mutableStateOf(R.string.set_notification_time_title)))
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
                            callNotificationTimeUseCase()
                        }
                    }

                    is Resource.Error -> {
                        it.exception.message?.let { message: String ->
                            Timber.e(message)
//                            GlobalUiEvent.showToast(message)
                        }
                    }
                }
            }
        }
    }


    private fun getFcmTokenUseCase() {
        viewModelScope.launch {
            getFcmTokenUseCase.invoke(null).collect {
                when (it) {
                    is Resource.Success -> {
                        it.data?.let {
//                            GlobalUiEvent.showToast("Token - $it")
                            postFcmTokenUseCase(it)
                        }
                    }

                    is Resource.Error -> {
                        it.exception.message?.let { message: String ->
                            Timber.e(message)
//                            GlobalUiEvent.showToast(message)
                        }
                    }
                }
            }
        }
    }

    private fun getNotificationTimeUseCase() {
        var hour = 22
        var minute = 0
        viewModelScope.launch {
            getNotifiCationTimeUseCase.invoke(null).collect { it ->
                when (it) {
                    is Resource.Success -> {
                        it.data.let { times ->
                            times[0]?.let {
                                hour = it
                            }
                            times[1]?.let {
                                minute = it
                            }
                            setState(
                                currentState.copy(
                                    isLoading = mutableStateOf(false),
                                    hour = mutableStateOf(hour),
                                    minute = mutableStateOf(minute)
                                )
                            )
//                            GlobalUiEvent.showToast("NotificationTimeUseCase Success")
                        }
                    }

                    is Resource.Error -> {
                        it.exception.message?.let { message: String ->
                            Timber.e(message)
//                            GlobalUiEvent.showToast(message)
                        }
                    }
                }
            }
        }
    }

    private fun setNotificationTimeUsePreferences() {
        viewModelScope.launch {
            setNotifiCationTimeUseCase.invoke(
                listOf<Int>(
                    uiState.value.hour.value, uiState.value.minute.value
                )
            ).collect { it ->
                when (it) {
                    is Resource.Success -> {
                        if (it.data) {
                            settingRouteUseCase()
                        }
                    }

                    is Resource.Error -> {
                        it.exception.message?.let { message: String ->
                            Timber.e(message)
//                            GlobalUiEvent.showToast(message)
                        }
                    }
                }
            }
        }
    }

    private fun settingRouteUseCase() {
        viewModelScope.launch {
            settingRouteUseCase.invoke(NavRoutes.Home.route).collect { it ->
                when (it) {
                    is Resource.Success -> {
                        if (it.data) {
                            setEffect(NotificationContract.Effect.NextRoutes)
                        }
                    }

                    is Resource.Error -> {
                        it.exception.message?.let { message: String ->
                            Timber.e(message)
//                            GlobalUiEvent.showToast(message)
                        }
                    }
                }
            }
        }
    }

    private fun callNotificationTimeUseCase() {
        viewModelScope.launch {
            postNotificationTimeUseCase.invoke(
                NotificationTimeModel(
                    notificationHour = uiState.value.hour.value,
                    notificationMinute = uiState.value.minute.value
                )
            ).collect {
                when (it) {
                    is Resource.Success -> {
                        if (it.data) {
//                            GlobalUiEvent.showToast("postNotificationConfig - Success")
                            setNotificationTimeUsePreferences()
                        }
                    }

                    is Resource.Error -> {
                        it.exception.message?.let { message: String ->
                            Timber.e(message)
//                            GlobalUiEvent.showToast(message)
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