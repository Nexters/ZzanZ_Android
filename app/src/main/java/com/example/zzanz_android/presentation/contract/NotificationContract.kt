package com.example.zzanz_android.presentation.contract

import androidx.compose.runtime.MutableState
import com.example.zzanz_android.presentation.viewmodel.UiEffect
import com.example.zzanz_android.presentation.viewmodel.UiEvent
import com.example.zzanz_android.presentation.viewmodel.UiState

class NotificationContract {
    sealed class Event : UiEvent {
        data class SetNotificationTime(val isHour: Boolean = true, val num: Int) : Event()
    }

    data class State(
        val timeState: TimeState,
    ) : UiState

    data class TimeState(
        val hour: MutableState<Int>, val minute: MutableState<Int>

    )

    sealed class Effect : UiEffect {
        object NextRoutes : Effect()
    }
}