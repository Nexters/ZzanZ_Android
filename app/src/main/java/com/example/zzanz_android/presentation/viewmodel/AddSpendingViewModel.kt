package com.example.zzanz_android.presentation.viewmodel

import com.example.zzanz_android.domain.usecase.AddSpendingUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AddSpendingViewModel @Inject constructor(
    private val addSpendingUseCase: AddSpendingUseCase
): BaseViewModel<AddSpendingEvent, AddSpendingState, AddSpendingEffect>() {
    override fun createInitialState(): AddSpendingState {
        TODO("Not yet implemented")
    }

    override fun handleEvent(event: AddSpendingEvent) {
        TODO("Not yet implemented")
    }
}

sealed class AddSpendingEvent: UiEvent{

}

sealed class AddSpendingEffect: UiEffect{

}

data class AddSpendingState(
    val t: Nothing
): UiState