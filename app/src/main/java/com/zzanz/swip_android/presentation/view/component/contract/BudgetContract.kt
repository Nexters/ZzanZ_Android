package com.zzanz.swip_android.presentation.view.component.contract

import androidx.compose.runtime.MutableState
import com.zzanz.swip_android.common.NetworkState
import com.zzanz.swip_android.domain.model.BudgetCategoryModel
import com.zzanz.swip_android.domain.model.PlanModel
import com.zzanz.swip_android.presentation.viewmodel.UiEffect
import com.zzanz.swip_android.presentation.viewmodel.UiEvent
import com.zzanz.swip_android.presentation.viewmodel.UiState

class BudgetContract {
    sealed class Event : UiEvent {
        data class SetSettingScreenType(val route: String) : Event()
        data class SetScreenState(val route: String) : Event()
        data class OnFetchBudget(val budget: String) : Event()
        object ClearBudgetCategoryItem : Event()
        data class OnFetchBudgetCategoryItem(val budgetCategory: BudgetCategoryModel) : Event()
        data class SetBudgetCategoryList(val category: List<PlanModel>) : Event()

        object OnFetchNestEggItem : Event()

        object OnNextButtonClicked : Event()
        data class GetSettingUiData(val route: String, val argument: String?) : Event()

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
        object NextRoutes : Effect()
    }
}