package com.zzanz.swip_android.presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.zzanz.swip_android.common.navigation.ArgumentKey
import com.zzanz.swip_android.domain.model.SpendingModel
import com.zzanz.swip_android.domain.usecase.AddSpendingUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddSpendingViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val addSpendingUseCase: AddSpendingUseCase,
) : BaseViewModel<AddSpendingEvent, AddSpendingState, AddSpendingEffect>() {
    override fun createInitialState(): AddSpendingState {
        return AddSpendingState(
            spending = SpendingModel(-1, "", "", 0),
            currentStep = STEP.AMOUNT,
            diffAmountState = null
        )
    }

    override fun handleEvent(event: AddSpendingEvent) {
        when (event) {
            AddSpendingEvent.InitBundleData -> {
                setDiffAmountState()
            }

            AddSpendingEvent.OnClickNext -> {
                setNextStep()
            }

            AddSpendingEvent.OnClickDone -> {
                submitSpending()
            }
            is AddSpendingEvent.UpdateTitleValue -> {
                setState(currentState.copy(spending = currentState.spending.copy(
                    title = event.value
                )))
            }
            is AddSpendingEvent.UpdateAmountValue -> {
                setState(currentState.copy(spending = currentState.spending.copy(
                    amount = event.value
                )))
                setDiffAmountState()
            }
            is AddSpendingEvent.UpdateMemoValue -> {
                setState(currentState.copy(spending = currentState.spending.copy(
                    memo = event.value
                )))
            }
        }
    }

    private fun setDiffAmountState() {
        val diffAmountFromBundle: Int =
            checkNotNull(savedStateHandle[ArgumentKey.remainAmount])
        val newDiffAmount = diffAmountFromBundle-currentState.spending.amount
        setState(
            currentState.copy(
                diffAmountState = DiffAmountState(
                    diffAmount = newDiffAmount,
                    isOver = newDiffAmount < 0
                )
            )
        )
    }

    private fun setNextStep() {
        when (currentState.currentStep) {
            STEP.AMOUNT -> {
                setState(
                    currentState.copy(
                        currentStep = STEP.TITLE
                    )
                )
            }

            STEP.TITLE -> {
                setState(
                    currentState.copy(
                        currentStep = STEP.MEMO
                    )
                )
            }

            else -> {}
        }
    }

    private fun submitSpending() {
        viewModelScope.launch {
            val planId: Int = checkNotNull(savedStateHandle[ArgumentKey.planId])
            addSpendingUseCase(planId, currentState.spending)
        }
    }

    init {
        setEvent(AddSpendingEvent.InitBundleData)
    }
}

sealed class AddSpendingEvent : UiEvent {
    object InitBundleData : AddSpendingEvent()
    object OnClickNext : AddSpendingEvent()

    object OnClickDone : AddSpendingEvent()
    data class UpdateTitleValue(val value: String): AddSpendingEvent()
    data class UpdateAmountValue(val value: Int): AddSpendingEvent()
    data class UpdateMemoValue(val value: String): AddSpendingEvent()
}

sealed class AddSpendingEffect : UiEffect {

}

data class AddSpendingState(
    val spending: SpendingModel,
    val currentStep: STEP,
    val diffAmountState: DiffAmountState?
) : UiState

enum class STEP {
    AMOUNT, TITLE, MEMO
}

data class DiffAmountState(
    val diffAmount: Int,
    val isOver: Boolean
)