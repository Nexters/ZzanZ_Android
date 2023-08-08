package com.example.zzanz_android

import androidx.compose.runtime.mutableStateOf
import com.example.zzanz_android.presentation.contract.GlobalContract
import com.example.zzanz_android.presentation.viewmodel.BaseViewModel
import com.example.zzanz_android.presentation.viewmodel.UiState

class MainViewModel(
) : BaseViewModel<GlobalContract.Event, GlobalContract.State, GlobalContract.Effect>() {
    // TODO 유나 - OnBoarding 로직 추가할 예정
    override fun createInitialState(): GlobalContract.State {
        return GlobalContract.State(isLoading = mutableStateOf(false))
    }

    override fun handleEvent(event: GlobalContract.Event) {
    }

}