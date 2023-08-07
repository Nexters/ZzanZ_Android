package com.example.zzanz_android.presentation.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.viewModelScope
import com.example.zzanz_android.MainViewModel
import com.example.zzanz_android.common.Resource
import com.example.zzanz_android.domain.usecase.BudgetUseCase
import com.example.zzanz_android.presentation.contract.BudgetContract
import com.example.zzanz_android.presentation.contract.GlobalContract
import com.example.zzanz_android.presentation.contract.GlobalUiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

/**
 * Event
 * 1. budget 정보 확인하는 이벤트 (숫자인지, 0보다 큰 숫자인지 등)
 * 2. budget 정보 서버에 POST 하는 이벤트 -> 결과 수신하는 이벤트
 * 3. budget 서버 통신 실패하는 이벤트
 * 4. 실패한 요청을 재시도 하는 이벤트
 */

@HiltViewModel
class BudgetViewModel @Inject constructor(
    private val budgetUseCase: BudgetUseCase
) : BaseViewModel<BudgetContract.Event, BudgetContract.State, BudgetContract.Effect>() {
    private val TAG = this.javaClass.simpleName

    override fun handleEvent(event: BudgetContract.Event) {
        when (event) {
            is BudgetContract.Event.OnFetchBudget -> {
                setBudget(event.budget)
            }

            is BudgetContract.Event.OnNextButtonClicked -> {
                postBudget()
            }
        }
    }

    override fun createInitialState(): BudgetContract.State {
        return BudgetContract.State(
            budgetState = BudgetContract.BudgetState.Idle,
            budget = mutableStateOf(TextFieldValue("")),
            buttonState = mutableStateOf(false)
        )
    }

    private fun setBudget(budget: TextFieldValue) {
        val buttonState = if (budget.text.isNullOrEmpty()) false
        else budget.text.all { Character.isDigit(it) }

        setState(
            state = BudgetContract.State(
                budgetState = BudgetContract.BudgetState.Idle,
                budget = mutableStateOf(budget),
                buttonState = mutableStateOf(buttonState)
            )
        )
    }

    private fun postBudget() {
        viewModelScope.launch {
            budgetUseCase.invoke(currentState.budget.value.text.toInt())
                .onStart {
                    setState(currentState.copy(budgetState = BudgetContract.BudgetState.Loading))
                }
                .collect {
                    when (it) {
                        is Resource.Success -> {
                            if (it.data) {
                                setState(currentState.copy(budgetState = BudgetContract.BudgetState.Success))
                            }

                        }

                        is Resource.Error -> {
                            setEffect(BudgetContract.Effect.ShowError(it.exception.message.toString()))
                            it.exception.message?.let { message: String ->
                                Timber.e(message)
                                GlobalUiEvent.showToast(message)
                            }
                        }
                    }
                }
        }
    }

}


