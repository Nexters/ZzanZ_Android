package com.zzanz.swip_android.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.zzanz.swip_android.R
import com.zzanz.swip_android.common.Resource
import com.zzanz.swip_android.common.navigation.SettingNavRoutes
import com.zzanz.swip_android.common.navigation.SettingType
import com.zzanz.swip_android.common.navigation.SplashNavRoutes
import com.zzanz.swip_android.domain.usecase.preference.SetLastSettingRouteUseCase
import com.zzanz.swip_android.presentation.view.component.SettingUiData
import com.zzanz.swip_android.presentation.view.component.contract.SplashContract
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class OnBoardingViewModel @Inject constructor(
    private val setLastSettingRouteUseCase: SetLastSettingRouteUseCase
) : BaseViewModel<SplashContract.Event, SplashContract.State, SplashContract.Effect>() {

    override fun createInitialState(): SplashContract.State {
        return SplashContract.State(
            uiData = SettingUiData(
                currentRoute = SplashNavRoutes.ExplainService.route,
                titleText = R.string.explain_service_title,
                nextRoute = SplashNavRoutes.ChallengeStart.route,
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
                    setLastSettingRoute()
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
                        buttonText = R.string.challenge_start,
                        explainContent = R.string.challenge_start_sub_title,
                        contentImage = R.drawable.icon_swip_money
                    )
                )
            )
        }
    }


    private fun setLastSettingRoute() {
        viewModelScope.launch {
            setLastSettingRouteUseCase.invoke(SettingNavRoutes.Budget.route).collect {
                when (it) {
                    is Resource.Success -> {
                        if (it.data) {
                            setEffect(
                                SplashContract.Effect.NextRoutes(
                                    SettingNavRoutes.Budget.route + "?${SettingType.onBoarding}"
                                )
                            )
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
}