package com.example.zzanz_android.presentation.view.component.contract

import com.example.zzanz_android.presentation.view.component.SettingUiData
import com.example.zzanz_android.presentation.viewmodel.UiEffect
import com.example.zzanz_android.presentation.viewmodel.UiEvent
import com.example.zzanz_android.presentation.viewmodel.UiState
import kotlinx.coroutines.flow.MutableSharedFlow

class SplashContract {
    sealed class Event : UiEvent {
        data class SetSplashType(val route: String) : Event()
        object OnNextButtonClicked : Event()
    }

    data class State(
        val uiData: SettingUiData
    ) : UiState

    sealed class Effect : UiEffect {
        data class NextRoutes(val route: String): Effect()

    }
}