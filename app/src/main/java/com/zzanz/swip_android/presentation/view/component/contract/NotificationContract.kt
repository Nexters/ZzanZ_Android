package com.zzanz.swip_android.presentation.view.component.contract

import androidx.compose.runtime.MutableState
import com.zzanz.swip_android.presentation.viewmodel.UiEffect
import com.zzanz.swip_android.presentation.viewmodel.UiEvent
import com.zzanz.swip_android.presentation.viewmodel.UiState

class NotificationContract {
    sealed class Event : UiEvent {
        data class SetSettingType(
            val settingType: String?
        ) : Event()
        data class SetNotificationTime(
            val isHour: Boolean = true, val num: Int
        ) : Event()
        object GetNotificationTime : Event()
        object OnNextButtonClicked : Event()
    }

    data class State(
        val isLoading: MutableState<Boolean>,
        val hour: MutableState<Int>,
        val minute: MutableState<Int>,
        val title: MutableState<Int>
    ) : UiState


    sealed class Effect : UiEffect {
        object NextRoutes : Effect()
    }
}