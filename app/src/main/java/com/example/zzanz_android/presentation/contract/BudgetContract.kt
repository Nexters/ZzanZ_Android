package com.example.zzanz_android.presentation.contract

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.input.TextFieldValue
import com.example.zzanz_android.presentation.viewmodel.UiEffect
import com.example.zzanz_android.presentation.viewmodel.UiEvent
import com.example.zzanz_android.presentation.viewmodel.UiState

class BudgetContract {
    sealed class Event : UiEvent {
        data class OnFetchBudget(val budget: TextFieldValue) : Event()
        object OnNextButtonClicked : Event()
    }

    data class State(
        val budgetState: BudgetState,
        val budget: MutableState<TextFieldValue>,
        val buttonState: MutableState<Boolean>
    ) : UiState

    sealed class BudgetState {
        object Idle : BudgetState()
        object Loading : BudgetState()
        object Success : BudgetState()
    }


    sealed class Effect : UiEffect {
        data class ShowError(val message: String?) : Effect()
    }
}