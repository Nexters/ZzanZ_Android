package com.example.zzanz_android.presentation.view.component.contract

import androidx.compose.runtime.MutableState
import androidx.compose.ui.text.input.TextFieldValue
import com.example.zzanz_android.common.NetworkState
import com.example.zzanz_android.domain.model.BudgetCategoryModel
import com.example.zzanz_android.presentation.viewmodel.UiEffect
import com.example.zzanz_android.presentation.viewmodel.UiEvent
import com.example.zzanz_android.presentation.viewmodel.UiState

class BudgetContract {
    sealed class Event : UiEvent {
        data class SetSettingScreenType(val route: String) : Event()
        data class SetScreenState(val route: String) : Event()
        data class OnFetchBudget(val budget: String) : Event()
        data class OnFetchBudgetCategoryItem(val budgetCategory: BudgetCategoryModel) : Event()
        object OnNextButtonClicked : Event()
    }

    data class State(
        val budgetState: NetworkState,
        val buttonState: MutableState<Boolean>,
        val budgetByCategoryState: NetworkState,
        val budgetByCategoryItemState: MutableState<BudgetByCategoryState>
    ) : UiState

    data class BudgetByCategoryState(
        val remainingBudget: MutableState<String>,
        val enteredCategory: MutableState<Int>,
        val totalCategory: MutableState<Int>
    )

    sealed class Effect : UiEffect {
        object NextRoutes: Effect()
    }
}