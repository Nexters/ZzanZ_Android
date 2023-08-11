package com.example.zzanz_android.presentation.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.example.zzanz_android.common.NetworkState
import com.example.zzanz_android.common.Resource
import com.example.zzanz_android.common.navigation.NavRoutes
import com.example.zzanz_android.common.navigation.SettingNavRoutes
import com.example.zzanz_android.domain.model.BudgetCategoryData
import com.example.zzanz_android.domain.model.BudgetCategoryModel
import com.example.zzanz_android.domain.model.Category
import com.example.zzanz_android.domain.usecase.BudgetByCategoryUseCase
import com.example.zzanz_android.domain.usecase.PostBudgetUseCase
import com.example.zzanz_android.domain.usecase.PutBudgetUseCase
import com.example.zzanz_android.domain.usecase.preference.SetLastSettingRouteUseCase
import com.example.zzanz_android.presentation.view.component.contract.BudgetContract
import com.example.zzanz_android.presentation.view.component.contract.GlobalUiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class BudgetViewModel @Inject constructor(
    private val postBudgetUseCase: PostBudgetUseCase,
    private val putBudgetUseCase: PutBudgetUseCase,
    private val postBudgetByCategoryUseCase: BudgetByCategoryUseCase,
    private val setLastSettingRouteUseCase: SetLastSettingRouteUseCase
) : BaseViewModel<BudgetContract.Event, BudgetContract.State, BudgetContract.Effect>() {
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
                } else {
                    setEffect(BudgetContract.Effect.NextRoutes)
                }
            }

        }
    }

    override fun createInitialState(): BudgetContract.State {
        return BudgetContract.State(
            budgetState = NetworkState.Idle,
            buttonState = mutableStateOf(false),
            budgetByCategoryState = NetworkState.Idle,
            budgetByCategoryItemState = mutableStateOf(
                BudgetContract.BudgetByCategoryState(
                    remainingBudget = mutableStateOf(""),
                    enteredCategory = mutableStateOf(0),
                    totalCategory = mutableStateOf(0)
                )
            )
        )
    }

    private fun setScreenType(route: String) {
        _screenType.value = route
    }

    private fun setScreenState(route: String) {
        when (route) {
            SettingNavRoutes.Budget.route -> {
                setState(
                    currentState.copy(
                        buttonState = mutableStateOf(setButtonState(route))
                    )
                )
            }

            SettingNavRoutes.BudgetCategory.route -> {
                setState(
                    currentState.copy(
                        buttonState = mutableStateOf(setButtonState(route))
                    )
                )
            }

            SettingNavRoutes.BudgetByCategory.route -> {
                setState(
                    currentState.copy(
                        buttonState = mutableStateOf(setButtonState(route)),
                        budgetByCategoryItemState = mutableStateOf(setBudgetByCategoryState())
                    )
                )
            }
        }
    }

    private fun getCategoryBudgetSum(): Int {
        var budgetSum = 0
        _budgetData.value.category.value.forEach {
            if (it.categoryId != Category.NESTEGG) {
                val budget = it.budget.toIntOrNull()
                budget?.let {
                    budgetSum += budget
                }
            }
        }
        return budgetSum
    }

    private fun getRemainingBudget(): Int {
        return (_budgetData.value.totalBudget.value.toInt() - getCategoryBudgetSum())
    }

    private fun setBudgetByCategoryState(): BudgetContract.BudgetByCategoryState {
        Timber.e("setBudgetByCategoryState ${getEnteredBudgetCategoryCount()}")
        return BudgetContract.BudgetByCategoryState(
            remainingBudget = mutableStateOf(getRemainingBudget().toString()),
            totalCategory = mutableStateOf(getSelectedCategoryCount()),
            enteredCategory = mutableStateOf(getEnteredBudgetCategoryCount())
        )
    }

    private fun getEnteredBudgetCategoryCount(): Int {
        val item = _budgetData.value.category.value.filter {
            it.isChecked && it.categoryId != Category.NESTEGG
                    && validateCategoryBudget(it.budget)
                    && getCategoryBudgetSum() <= _budgetData.value.totalBudget.value.toInt()
        }.size
        return item
    }

    private fun getSelectedCategoryCount(): Int {
        return _budgetData.value.category.value.filter {
            (it.isChecked && it.categoryId != Category.NESTEGG)
        }.size
    }

    private fun setBudgetCategoryItem(item: BudgetCategoryModel) {
        _budgetData.value.category.value = _budgetData.value.category.value.map {
            if (it.categoryId == item.categoryId) {
                item
            } else it
        }

        if (_screenType.value == SettingNavRoutes.BudgetCategory.route) {
            setState(
                currentState.copy(
                    buttonState = mutableStateOf(setButtonState(_screenType.value))
                )
            )
        } else {
            setNestEggCategoryItem()
            setState(
                currentState.copy(
                    budgetByCategoryItemState = mutableStateOf(setBudgetByCategoryState()),
                    buttonState = mutableStateOf(setButtonState(_screenType.value))
                )
            )
        }
    }

    private fun setNestEggCategoryItem() {
        _budgetData.value.category.value = _budgetData.value.category.value.map {
            if (it.categoryId == Category.NESTEGG) {
                if (setButtonState(_screenType.value)) it.copy(
                    budget = getRemainingBudget().toString()
                )
                else it.copy(budget = "0")
            } else it
        }
    }

    private fun setButtonState(route: String): Boolean {
        return when (route) {
            SettingNavRoutes.Budget.route -> {
                validateBudget(_budgetData.value.totalBudget.value)
            }

            SettingNavRoutes.BudgetCategory.route -> {
                _budgetData.value.category.value.any {
                    it.isChecked && it.categoryId != Category.NESTEGG
                }
            }

            SettingNavRoutes.BudgetByCategory.route -> {
                getEnteredBudgetCategoryCount() == getSelectedCategoryCount() && getRemainingBudget() >= 0
            }

            else -> false
        }
    }

    private fun setBudget(budget: String) {
        setTotalBudget(budget)
        setState(
            currentState.copy(
                buttonState = mutableStateOf(setButtonState(_screenType.value))
            )
        )
    }

    private fun setTotalBudget(text: String) {
        _budgetData.value.totalBudget = mutableStateOf(text)
    }

    private fun validateBudget(budget: String): Boolean {
        return if (budget.isEmpty() || budget == "0") false
        else budget.all { Character.isDigit(it) }
    }

    private fun validateCategoryBudget(budget: String): Boolean {
        return if (budget.isEmpty() || budget == "0") false
        else budget.all { Character.isDigit(it) }
    }

    private fun callBudgetUseCase() {
        if (_budgetData.value.totalBudget.value.isEmpty()) return postBudgetUseCase()
        return putBudgetUseCase()
    }


    private fun postBudgetByCategoryUseCase() {
        viewModelScope.launch {
            val budgetByCategoryList = _budgetData.value.category.value.filter {
                it.isChecked
            }
            postBudgetByCategoryUseCase.invoke(budgetByCategoryList)
                .onStart {
                    setState(currentState.copy(budgetByCategoryState = NetworkState.Loading))
                }
                .collect {
                    when (it) {
                        is Resource.Success -> {
                            if (it.data) {
                                setLastSettingRoute()
                                GlobalUiEvent.showToast("postBudgetCategoryUseCase - Success")
                                setState(currentState.copy(budgetByCategoryState = NetworkState.Success))
                            }
                        }

                        is Resource.Error -> {
                            it.exception.message?.let { message: String ->
                                Timber.e(message)
                                GlobalUiEvent.showToast(message)
                            }
                        }
                    }
                }
        }
    }

    private fun setLastSettingRoute() {
        viewModelScope.launch {
            setLastSettingRouteUseCase.invoke(NavRoutes.Notification.route).collect {
                when (it) {
                    is Resource.Success -> {
                        if (it.data) {
                            setEffect(BudgetContract.Effect.NextRoutes)
                        }
                    }

                    is Resource.Error -> {
                        it.exception.message?.let { message: String ->
                            Timber.e(message)
                            GlobalUiEvent.showToast(message)
                        }
                    }
                }
            }
        }
    }


    private fun putBudgetUseCase() {
        viewModelScope.launch {
            putBudgetUseCase.invoke(_budgetData.value.totalBudget.value.toInt()).onStart {
                setState(currentState.copy(budgetState = NetworkState.Loading))
            }.collect {
                when (it) {
                    is Resource.Success -> {
                        if (it.data) {
                            setState(currentState.copy(budgetState = NetworkState.Success))
                            postBudgetByCategoryUseCase()
                        }
                    }

                    is Resource.Error -> {
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
            postBudgetUseCase.invoke(_budgetData.value.totalBudget.value.toInt()).onStart {
                setState(currentState.copy(budgetState = NetworkState.Loading))
            }.collect {
                when (it) {
                    is Resource.Success -> {
                        if (it.data) {
                            setState(currentState.copy(budgetState = NetworkState.Success))
                            postBudgetByCategoryUseCase()
                        }

                    }

                    is Resource.Error -> {
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


