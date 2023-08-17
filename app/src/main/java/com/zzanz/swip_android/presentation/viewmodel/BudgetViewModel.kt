package com.zzanz.swip_android.presentation.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.zzanz.swip_android.R
import com.zzanz.swip_android.common.NetworkState
import com.zzanz.swip_android.common.Resource
import com.zzanz.swip_android.common.navigation.NavRoutes
import com.zzanz.swip_android.common.navigation.SettingNavRoutes
import com.zzanz.swip_android.common.navigation.SettingType
import com.zzanz.swip_android.domain.model.BudgetCategoryData
import com.zzanz.swip_android.domain.model.BudgetCategoryModel
import com.zzanz.swip_android.domain.model.Category
import com.zzanz.swip_android.domain.usecase.PostBudgetByCategoryUseCase
import com.zzanz.swip_android.domain.usecase.PostBudgetUseCase
import com.zzanz.swip_android.domain.usecase.PutBudgetByCategoryUseCase
import com.zzanz.swip_android.domain.usecase.PutBudgetUseCase
import com.zzanz.swip_android.domain.usecase.preference.SetLastSettingRouteUseCase
import com.zzanz.swip_android.presentation.view.component.SettingUiData
import com.zzanz.swip_android.presentation.view.component.contract.BudgetContract
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
    private val postBudgetByCategoryUseCase: PostBudgetByCategoryUseCase,
    private val putBudgetByCategoryUseCase: PutBudgetByCategoryUseCase,
    private val setLastSettingRouteUseCase: SetLastSettingRouteUseCase
) : BaseViewModel<BudgetContract.Event, BudgetContract.State, BudgetContract.Effect>() {
    private val _screenType = MutableStateFlow("")
    val screenType = _screenType.asStateFlow()

    private val _budgetData = MutableStateFlow(BudgetCategoryData)
    val budgetData = _budgetData.asStateFlow()

    private val _uiData: MutableStateFlow<SettingUiData?> = MutableStateFlow(null)
    val uiData = _uiData.asStateFlow()

    private val _settingType = MutableStateFlow("")
    val settingType = _settingType.asStateFlow()

    override fun handleEvent(event: BudgetContract.Event) {
        when (event) {
            is BudgetContract.Event.SetSettingScreenType -> {
                setScreenType(event.route)
            }

            is BudgetContract.Event.SetBudgetCategoryList -> {
                clearBudgetByCategoryList()
                _budgetData.value.category.value = _budgetData.value.category.value.map { budget ->
                    event.category.forEach { plan ->
                        if (plan.category == budget.categoryId.toString()) {
                            budget.isChecked = true
                            budget.budget = plan.goalAmount.toString()
                            return@map budget
                        }
                    }
                    return@map budget
                }

                _budgetData.value.category.value = _budgetData.value.category.value.map {
                    if (it.categoryId == Category.NESTEGG) {
                        return@map it.copy(
                            isChecked = true,
                            budget = getRemainingBudget().toString()
                        )
                    }
                    return@map it
                }
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

            is BudgetContract.Event.GetSettingUiData -> {
                getSettingUiData(event.route, event.argument)
            }

            is BudgetContract.Event.ClearBudgetCategoryItem -> {
                clearBudgetByCategoryList()
            }

        }
    }

    private fun clearBudgetByCategoryList() {
        _budgetData.value.category.value = _budgetData.value.category.value.map {
            if (it.categoryId != Category.NESTEGG) {
                it.isChecked = false
                return@map it
            }
            return@map it
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

    private fun getSettingUiData(route: String, argument: String?) {
        var settingUiData = SettingRoute.data.single {
            it.currentRoute == route
        }
        if (route == SettingNavRoutes.Budget.route) {
            settingUiData = if (argument == SettingType.home) {
                settingUiData.copy(
                    titleText = R.string.edit_week_budget_title,
                    nextRoute = SettingNavRoutes.BudgetByCategory.route
                )
            } else {
                settingUiData.copy(titleText = R.string.next_week_budget_title)
            }
        }

        if (route == SettingNavRoutes.BudgetByCategory.route) {
            settingUiData = if (argument == SettingType.home) {
                settingUiData.copy(
                    titleText = R.string.edit_budget_by_category_title,
                    buttonText = R.string.edit_complete
                )
            } else {
                settingUiData.copy(titleText = R.string.budget_by_category_title)
            }
        }
        _settingType.value = if (argument.isNullOrEmpty()) SettingType.onBoarding else argument
        _uiData.value = settingUiData
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
        if (_budgetData.value.totalBudget.value.isEmpty()) {
            return 0
        }
        return (_budgetData.value.totalBudget.value.toInt() - getCategoryBudgetSum())
    }

    private fun setBudgetByCategoryState(): BudgetContract.BudgetByCategoryState {
        return BudgetContract.BudgetByCategoryState(
            remainingBudget = mutableStateOf(getRemainingBudget().toString()),
            totalCategory = mutableStateOf(getSelectedCategoryCount()),
            enteredCategory = mutableStateOf(getEnteredBudgetCategoryCount())
        )
    }

    private fun getEnteredBudgetCategoryCount(): Int {
        val item = _budgetData.value.category.value.filter {
            it.isChecked && it.categoryId != Category.NESTEGG && validateCategoryBudget(it.budget) && getCategoryBudgetSum() <= _budgetData.value.totalBudget.value.toInt()
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
                if (setButtonState(_screenType.value)) {
                    return@map it.copy(budget = getRemainingBudget().toString())
                } else {
                    return@map it.copy(budget = "0")
                }
            } else return@map it
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
        if (_settingType.value == SettingType.onBoarding) return postBudgetUseCase()
        return putBudgetUseCase()
    }

    private fun postBudgetByCategoryUseCase() {
        viewModelScope.launch {
            val budgetByCategoryList = _budgetData.value.category.value.filter {
                it.isChecked
            }
            postBudgetByCategoryUseCase.invoke(budgetByCategoryList).onStart {
                setState(currentState.copy(budgetByCategoryState = NetworkState.Loading))
            }.collect {
                when (it) {
                    is Resource.Success -> {
                        if (it.data) {
                            setLastSettingRoute()
//                            GlobalUiEvent.showToast("postBudgetCategoryUseCase - Success")
                            setState(currentState.copy(budgetByCategoryState = NetworkState.Success))
                        }
                    }

                    is Resource.Error -> {
                        it.exception.message?.let { message: String ->
                            Timber.e(message)
//                            GlobalUiEvent.showToast(message)
                        }
                    }
                }
            }
        }
    }

    private fun putBudgetByCategoryUseCase() {
        viewModelScope.launch {
            val budgetByCategoryList = _budgetData.value.category.value.filter {
                it.isChecked
            }
            putBudgetByCategoryUseCase.invoke(budgetByCategoryList).onStart {
                setState(currentState.copy(budgetByCategoryState = NetworkState.Loading))
            }.collect {
                when (it) {
                    is Resource.Success -> {
                        if (it.data) {
                            setState(currentState.copy(budgetByCategoryState = NetworkState.Success))
                            setEffect(BudgetContract.Effect.NextRoutes)
                        }
                    }

                    is Resource.Error -> {
                        it.exception.message?.let { message: String ->
                            Timber.e(message)
//                            GlobalUiEvent.showToast(message)
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
//                            GlobalUiEvent.showToast(message)
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
                            if (_settingType.value == SettingType.onBoarding) {
                                postBudgetByCategoryUseCase()
                            } else {
                                putBudgetByCategoryUseCase()
                            }
                        }
                    }

                    is Resource.Error -> {
                        it.exception.message?.let { message: String ->
                            Timber.e(message)
                            postBudgetUseCase()
//                            GlobalUiEvent.showToast(message)
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
//                            GlobalUiEvent.showToast(message)
                        }
                    }
                }
            }
        }
    }

    object SettingRoute {
        val data = listOf(
            SettingUiData(
                currentRoute = SettingNavRoutes.BudgetByCategory.route,
                titleText = R.string.budget_by_category_title,
                nextRoute = NavRoutes.Notification.route,
                buttonText = R.string.next
            ), SettingUiData(
                currentRoute = SettingNavRoutes.Budget.route,
                titleText = R.string.next_week_budget_title,
                nextRoute = SettingNavRoutes.BudgetCategory.route,
                buttonText = R.string.next
            ), SettingUiData(
                currentRoute = SettingNavRoutes.BudgetCategory.route,
                titleText = R.string.next_week_budget_category,
                nextRoute = SettingNavRoutes.BudgetByCategory.route,
                buttonText = R.string.next

            )
        )
    }
}

