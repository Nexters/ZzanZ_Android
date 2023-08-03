package com.example.zzanz_android.presentation.viewmodel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.example.zzanz_android.domain.repository.ChallengeRepository
import com.example.zzanz_android.domain.usecase.BudgetUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
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
class GoalAmountViewModel @Inject constructor(
    private val budgetUseCase: BudgetUseCase
) : BaseViewModel() {
    private val TAG = this.javaClass.simpleName

    private val events = Channel<GoalAmountEvent>()

    private val _budgetState = MutableStateFlow<GoalAmountState>(GoalAmountState.Loading)
    val budgetState: StateFlow<GoalAmountState>
        get() = _budgetState

    private val _budget = mutableStateOf<String>("")
    val budget : State<String>
        get() = _budget

    init {
        handleEvent(events as UiEvent)
        viewModelScope.launch(Dispatchers.IO) {
            delay(1500)
            _budgetState.value = GoalAmountState.Success
        }
    }

    override fun createInitialState(): UiState {
        TODO("Not yet implemented")
    }

    override fun handleEvent(event: UiEvent) {
        TODO("Not yet implemented")
    }

    fun setGoalAmount() {
        viewModelScope.launch {
            events.send(GoalAmountEvent.Loading)
            val result = budgetUseCase(_budget.value.toInt())
            Log.d(TAG, "postGoalAmount Result ${result}")
            events.send(GoalAmountEvent.OnSuccess(result = result.toString().toBoolean()))
        }
    }
}

/**
 * Event
 * 0. BudgetEvent가 선행 되어야 함
 * 1. budget 정보 서버에 POST 하는 이벤트 -> 결과 수신하는 이벤트
 * 2. budget 서버 통신 실패하는 이벤트
 * 3. 실패한 요청을 재시도 하는 이벤트
 */
sealed interface GoalAmountEvent : UiEvent {
    data class RequestGoalAmount(val budget: Int) : GoalAmountEvent
    object Loading : GoalAmountEvent
    data class OnSuccess(val result: Boolean) : GoalAmountEvent
    object OnError : GoalAmountEvent
    data class Retry(val budget: Int) : GoalAmountEvent
}

/**
 * Event
 * 1. budget 정보 확인하는 이벤트 (숫자인지, 0보다 큰지, 등)
 * 2. 확인 버튼 활성화 이벤트 (budget 정보가 올바르면 버튼 활성화)
 */
sealed class ValidateEvent : UiEvent {
    data class ValidateBudget(val budget: Int) : ValidateEvent()
    data class ValidateButton(val budget: Int) : ValidateEvent()
}


sealed class GoalAmountState : UiState {
    object Loading : GoalAmountState()
    object Success : GoalAmountState()
    data class Error(val exception: Exception) : GoalAmountState()
}

/**
 * Effect = Intent
 * 1. budget 정보 서버에 POST 하는 이벤트
 */
sealed class GoalAmountEffect : UiEffect {
    data class RequestGoalAmount(val budget: Int) : GoalAmountEffect()
}