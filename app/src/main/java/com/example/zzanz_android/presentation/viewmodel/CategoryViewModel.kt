package com.example.zzanz_android.presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.example.zzanz_android.common.Resource
import com.example.zzanz_android.common.navigation.ArgumentKey
import com.example.zzanz_android.domain.model.ChallengeStatus
import com.example.zzanz_android.domain.model.PlanModel
import com.example.zzanz_android.domain.model.SpendingModel
import com.example.zzanz_android.domain.usecase.category.GetSpendingListByCategoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val getSpendingListUseCase: GetSpendingListByCategoryUseCase
) : BaseViewModel<CategoryEvent, CategoryState, CategoryEffect>() {
    override fun createInitialState(): CategoryState {
        return CategoryState(ChallengeStatus.OPENED, SpendingListByPlanState.Loading)
    }

    override fun handleEvent(event: CategoryEvent) {
        when (event) {
            CategoryEvent.LoadSpendingList -> {
                setSpendingDataByPlan()
            }
        }
    }

    init {
        setEvent(CategoryEvent.LoadSpendingList)
    }

    private fun setSpendingDataByPlan() {
        viewModelScope.launch {
            val planId: Int = checkNotNull(savedStateHandle[ArgumentKey.planId])
            getSpendingListUseCase(planId).collect { resource ->
                when (resource) {
                    is Resource.Success -> {
                        val challengeStatusName: String =
                            checkNotNull(savedStateHandle[ArgumentKey.challengeStatus])
                        setState(
                            currentState.copy(
                                challengeStatus = ChallengeStatus.valueOf(challengeStatusName),
                                spendingListByPlanState = SpendingListByPlanState.Success(
                                    planInfo = resource.data.plan,
                                    spendingList = resource.data.spendingList
                                )
                            )
                        )
                    }

                    is Resource.Error -> {
                        setState(currentState.copy(spendingListByPlanState = SpendingListByPlanState.Error))
                    }
                }
            }
        }
    }

    fun setEffectShowErrorToast() {
        setEffect(CategoryEffect.ShowErrorToast("Error"))
    }
}

sealed class CategoryEvent : UiEvent {
    object LoadSpendingList : CategoryEvent()
}

sealed class CategoryEffect : UiEffect {
    data class ShowErrorToast(val message: String) : CategoryEffect()
}

data class CategoryState(
    val challengeStatus: ChallengeStatus,
    val spendingListByPlanState: SpendingListByPlanState
) : UiState

sealed class SpendingListByPlanState {
    object Loading : SpendingListByPlanState()
    object Error : SpendingListByPlanState()
    data class Success(
        val planInfo: Flow<PlanModel>,
        val spendingList: Flow<PagingData<SpendingModel>>
    ) : SpendingListByPlanState()
}