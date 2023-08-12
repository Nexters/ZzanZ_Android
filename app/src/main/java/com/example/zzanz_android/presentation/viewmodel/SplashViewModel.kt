package com.example.zzanz_android.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.example.zzanz_android.R
import com.example.zzanz_android.common.Resource
import com.example.zzanz_android.common.navigation.SettingNavRoutes
import com.example.zzanz_android.common.navigation.SettingType
import com.example.zzanz_android.common.navigation.SplashNavRoutes
import com.example.zzanz_android.domain.usecase.preference.GetLastSettingRouteUseCase
import com.example.zzanz_android.presentation.view.component.SettingUiData
import com.example.zzanz_android.presentation.view.component.contract.GlobalUiEvent
import com.example.zzanz_android.presentation.view.component.contract.SplashContract
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val getLastSettingRouteUseCase: GetLastSettingRouteUseCase
) : BaseViewModel<SplashContract.Event, SplashContract.State, SplashContract.Effect>() {

    override fun createInitialState(): SplashContract.State {
        return SplashContract.State(
            uiData = SettingUiData(
                currentRoute = SplashNavRoutes.ExplainService.route,
                titleText = R.string.explain_service_title,
                nextRoute = SplashNavRoutes.ChallengeStart.route,
                backRoute = "",
                buttonText = R.string.next,
                explainContent = R.string.explain_service_sub_title,
                contentImage = R.drawable.icon_splash_img
            )
        )
    }

    override fun handleEvent(event: SplashContract.Event) {
        when (event) {
            is SplashContract.Event.SetSplashType -> {
                setSettingUiData(event.route)
            }

            is SplashContract.Event.OnNextButtonClicked -> {
                if (uiState.value.uiData.currentRoute == SplashNavRoutes.ExplainService.route) {
                    setEffect(SplashContract.Effect.NextRoutes(uiState.value.uiData.nextRoute))
                } else {
                    getLastSettingRoute()
                }
            }
        }
    }

    private fun setSettingUiData(routes: String) {
        if (routes == SplashNavRoutes.ExplainService.route) {
            setState(
                currentState.copy(
                    uiData = SettingUiData(
                        currentRoute = SplashNavRoutes.ExplainService.route,
                        titleText = R.string.explain_service_title,
                        nextRoute = SplashNavRoutes.ChallengeStart.route,
                        backRoute = "",
                        buttonText = R.string.next,
                        explainContent = R.string.explain_service_sub_title,
                        contentImage = R.drawable.icon_splash_img
                    )
                )
            )
        } else {
            setState(
                currentState.copy(
                    uiData = SettingUiData(
                        currentRoute = SplashNavRoutes.ChallengeStart.route,
                        titleText = R.string.challenge_start_title,
                        nextRoute = SettingNavRoutes.Budget.route,
                        backRoute = SplashNavRoutes.ExplainService.route,
                        buttonText = R.string.challenge_start,
                        explainContent = R.string.challenge_start_sub_title,
                        contentImage = R.drawable.icon_swip_money
                    )
                )
            )
        }
    }

    private fun getLastSettingRoute() {
        viewModelScope.launch {
            getLastSettingRouteUseCase.invoke(null).collect {
                when (it) {
                    is Resource.Success -> {
                        var route = SettingNavRoutes.Budget.route
                        Timber.e("### getLastSettingRoute - ${it.data}")
                        if (!it.data.isNullOrEmpty()) {
                            route = it.data
                        }
                        setEffect(SplashContract.Effect.NextRoutes(route + "/${SettingType.onBoarding}"))
                    }

                    is Resource.Error -> {
                        it.exception.message?.let { message: String ->
                            Timber.e("error - $message")
                            GlobalUiEvent.showToast(message)
                        }
                    }
                }
            }
        }
    }
}