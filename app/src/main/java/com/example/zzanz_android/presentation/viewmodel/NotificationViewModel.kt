package com.example.zzanz_android.presentation.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.example.zzanz_android.R
import com.example.zzanz_android.common.Resource
import com.example.zzanz_android.domain.model.NotificationTimeModel
import com.example.zzanz_android.domain.usecase.PostNotificationTimeUseCase
import com.example.zzanz_android.domain.usecase.preference.GetNotificationTimeUseCase
import com.example.zzanz_android.domain.usecase.preference.SetNotificationTimeUseCase
import com.example.zzanz_android.presentation.view.component.contract.GlobalUiEvent
import com.example.zzanz_android.presentation.view.component.contract.NotificationContract
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class NotificationViewModel @Inject constructor(
    private val postNotificationTimeUseCase: PostNotificationTimeUseCase,
    private val getNotifiCationTimeUseCase: GetNotificationTimeUseCase,
    private val setNotifiCationTimeUseCase: SetNotificationTimeUseCase
) : BaseViewModel<NotificationContract.Event, NotificationContract.State, NotificationContract.Effect>() {
    override fun createInitialState(): NotificationContract.State {
        return NotificationContract.State(
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
                callNotificationTimeUseCase()
            }

            is NotificationContract.Event.SetSettingType -> {
                setTitle(event.settingType)
            }
        }
    }

    private fun setTitle(settingType: String?) {
        if (settingType == null) {
            setState(currentState.copy(title = mutableStateOf(R.string.set_notification_time_title)))
        } else {
            setState(currentState.copy(title = mutableStateOf(R.string.edit_notification_time_title)))
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
//                            GlobalUiEvent.showToast("NotificationTimeUseCase Success")

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
            Timber.e("NotificationTimeUseCase  hour - $hour, minute - $minute")
            setState(
                currentState.copy(
                    hour = mutableStateOf(hour), minute = mutableStateOf(minute)
                )
            )

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
                            Timber.e("setNotificationTimeUseCase Success")
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
                            GlobalUiEvent.showToast("postNotificationConfig - Success")
                            setNotificationTimeUsePreferences()
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