package com.example.zzanz_android.presentation.view.component.contract

import androidx.compose.runtime.MutableState
import com.example.zzanz_android.presentation.viewmodel.UiEffect
import com.example.zzanz_android.presentation.viewmodel.UiEvent
import com.example.zzanz_android.presentation.viewmodel.UiState
import kotlinx.coroutines.flow.MutableSharedFlow

class GlobalContract {
    sealed class Event : UiEvent {
        data class SetFcmToken(val token: String): Event()
    }

    data class State(
        val isLoading: MutableState<Boolean>
    ) : UiState

    sealed class Effect : UiEffect {
        data class ShowToast(val message: String) : Effect()
        data class SetSettingLastRoute(val route: String?) : Effect()
    }
}

object GlobalUiEvent {
    val uiEffect = MutableSharedFlow<GlobalContract.Effect>(extraBufferCapacity = 1)

    suspend fun showToast(message: String) {
        uiEffect.emit(GlobalContract.Effect.ShowToast(message))
    }
}