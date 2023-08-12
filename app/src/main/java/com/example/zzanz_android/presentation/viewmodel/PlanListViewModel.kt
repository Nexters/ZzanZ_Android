package com.example.zzanz_android.presentation.viewmodel

import com.example.zzanz_android.domain.model.PlanModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PlanListViewModel @Inject constructor() : BaseViewModel<PlanListUiEvent, PlanListState, PlanListEffect>() {
    override fun createInitialState(): PlanListState {
        return PlanListState(PlanListLoadingState.NotLoading)
    }

    override fun handleEvent(event: PlanListUiEvent) {
        when(event){
            is PlanListUiEvent.SetPlanList -> {
                setState(PlanListState(PlanListLoadingState.Loaded(event.planList)))
            }
        }
    }
}

sealed class PlanListUiEvent : UiEvent {
    data class SetPlanList(val planList: List<PlanModel>): PlanListUiEvent()
}

sealed class PlanListEffect : UiEffect

data class PlanListState(
    val planListLoadingState : PlanListLoadingState
) : UiState

sealed class PlanListLoadingState{
    object NotLoading : PlanListLoadingState()
    data class Loaded(val planList: List<PlanModel>) : PlanListLoadingState()
}