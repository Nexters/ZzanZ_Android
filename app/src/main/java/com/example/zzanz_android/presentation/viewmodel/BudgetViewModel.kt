package com.example.zzanz_android.presentation.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.viewModelScope
import com.example.zzanz_android.common.Resource
import com.example.zzanz_android.common.navigation.SettingNavRoutes
import com.example.zzanz_android.domain.model.BudgetCategoryData
import com.example.zzanz_android.domain.model.BudgetCategoryModel
import com.example.zzanz_android.domain.model.Category
import com.example.zzanz_android.domain.usecase.PostBudgetUseCase
import com.example.zzanz_android.domain.usecase.PutBudgetUseCase
import com.example.zzanz_android.presentation.contract.BudgetContract
import com.example.zzanz_android.presentation.contract.GlobalUiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import okhttp3.internal.wait
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
    private val postBudgetUseCase: PostBudgetUseCase, private val putBudgetUseCase: PutBudgetUseCase
) : BaseViewModel<BudgetContract.Event, BudgetContract.State, BudgetContract.Effect>() {
    private val TAG = this.javaClass.simpleName

    private val _screenType = MutableStateFlow("")
    val screenType = _screenType.asStateFlow()

    private val _budgetData = MutableStateFlow(BudgetCategoryData)
    val budgetData = _budgetData.asStateFlow()

    override fun handleEvent(event: BudgetContract.Event) {
        when (event) {
            is BudgetContract.Event.SetSettingScreenType -> {
                setScreenType(event.route)
            }

            is BudgetContract.Event.SetScreenState -> {
                setScreenState(event.route)
            }

            is BudgetContract.Event.OnFetchBudget -> {
                setBudget(event.budget)
            }

            is BudgetContract.Event.OnFetchBudgetCategoryItem -> {
                setBudgetCategoryItem(event.budgetCategory)
            }

            is BudgetContract.Event.OnNextButtonClicked -> {
                if (_screenType.value == SettingNavRoutes.BudgetByCategory.route) {
                    callBudgetUseCase()
                }
                setState(currentState.copy(budgetState = BudgetContract.BudgetState.Success))
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

    private fun setScreenType(route: String) {
        _screenType.value = route
    }

    private fun setScreenState(route: String) {
        if (route == SettingNavRoutes.Budget.route) {
            setState(
                currentState.copy(
                    budget = mutableStateOf(_budgetData.value.totalBudget.value),
                    buttonState = mutableStateOf(setButtonState(route))
                )
            )
        } else if (route == SettingNavRoutes.BudgetCategory.route) {
            setState(
                currentState.copy(
                    buttonState = mutableStateOf(setButtonState(route))
                )
            )
        }
    }

    private fun setBudgetCategoryItem(item: BudgetCategoryModel) {
        _budgetData.value.category.value = _budgetData.value.category.value.map {
            if (it.categoryId == item.categoryId) {
                item
            } else it
        }
        setState(currentState.copy(buttonState = mutableStateOf(setButtonState(_screenType.value))))
    }

    private fun setButtonState(route: String): Boolean {
        if (route == SettingNavRoutes.Budget.route) {
            val totalBudget = _budgetData.value.totalBudget.value
            return if (totalBudget.text.isNullOrEmpty()) false
            else totalBudget.text.all { Character.isDigit(it) }
        } else if (route == SettingNavRoutes.BudgetCategory.route) {
            return _budgetData.value.category.value.any {
                it.isChecked && it.categoryId != Category.NESTEGG
            }
        }
        return false
    }

    private fun setBudget(budget: TextFieldValue) {
        setTotalBudget(budget)
        setState(
            currentState.copy(
                budget = mutableStateOf(budget),
                buttonState = mutableStateOf(setButtonState(_screenType.value))
            )
        )
    }

    private fun callBudgetUseCase() {
        if (_budgetData.value.totalBudget.value.text.isNullOrEmpty()) return postBudgetUseCase()
        return putBudgetUseCase()
    }


    private fun setTotalBudget(text: TextFieldValue) {
        _budgetData.value.totalBudget = mutableStateOf(text)
    }

    private fun putBudgetUseCase() {
        viewModelScope.launch {
            putBudgetUseCase.invoke(uiState.value.budget.value.text.toInt()).onStart {
                setState(currentState.copy(budgetState = BudgetContract.BudgetState.Loading))
            }.collect {
                when (it) {
                    is Resource.Success -> {
                        if (it.data) {
                            setTotalBudget(uiState.value.budget.value)
                            setState(currentState.copy(budgetState = BudgetContract.BudgetState.Success))
                        }
                    }

                    is Resource.Error -> {
                        setEffect(BudgetContract.Effect.ShowError(it.exception.message.toString()))
                        it.exception.message?.let { message: String ->
                            Timber.e(message)
                            postBudgetUseCase()
                            GlobalUiEvent.showToast(message)
                        }
                    }
                }
            }
        }
    }

    private fun postBudgetUseCase() {
        viewModelScope.launch {
            postBudgetUseCase.invoke(currentState.budget.value.text.toInt()).onStart {
                setState(currentState.copy(budgetState = BudgetContract.BudgetState.Loading))
            }.collect {
                when (it) {
                    is Resource.Success -> {
                        if (it.data) {
                            setTotalBudget(uiState.value.budget.value)
                            setState(currentState.copy(budgetState = BudgetContract.BudgetState.Success))
                        }

                    }

                    is Resource.Error -> {
                        setEffect(BudgetContract.Effect.ShowError(it.exception.message.toString()))
                        it.exception.message?.let { message: String ->
                            Timber.e(message)
                            putBudgetUseCase()
                            GlobalUiEvent.showToast(message)
                        }
                    }
                }
            }
        }
    }

}


