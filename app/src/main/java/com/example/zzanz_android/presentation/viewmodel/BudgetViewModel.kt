package com.example.zzanz_android.presentation.viewmodel

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.zzanz_android.common.Resource
import com.example.zzanz_android.domain.repository.ChallengeRepository
import com.example.zzanz_android.domain.usecase.BudgetUseCase
import com.example.zzanz_android.presentation.contract.BudgetContract
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
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

    private val _isButtonActive = MutableStateFlow(false)
    val isButtonActive = _isButtonActive

    override fun handleEvent(event: BudgetContract.Event) {
        when(event) {
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
        _isButtonActive.value = if (budget.text.isNullOrEmpty()) false
            else budget.text.all { Character.isDigit(it) }

        setState(state = BudgetContract.State(
            budgetState = BudgetContract.BudgetState.Idle,
            budget = mutableStateOf(budget),
            buttonState = mutableStateOf(_isButtonActive.value)))
    }

    private fun postBudget() {
        viewModelScope.launch {
            budgetUseCase.invoke(currentState.budget.value.text.toInt())
                .onStart {
                    setState(currentState.copy(budgetState = BudgetContract.BudgetState.Loading))
                }
                .collect {
                    when(it) {
                        is Resource.Success -> {
                            val isSuccess = it.data
                            Log.e(TAG, isSuccess.toString())
                            setState(currentState.copy(budgetState = BudgetContract.BudgetState.Success))

                        }
                        is Resource.Error -> {
                            setEffect(BudgetContract.Effect.ShowError(it.exception.message.toString()))
                        }
                    }
                }
        }
    }

}


