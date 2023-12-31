package com.zzanz.swip_android

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.zzanz.swip_android.common.Resource
import com.zzanz.swip_android.common.navigation.NavRoutes
import com.zzanz.swip_android.domain.usecase.preference.GetLastSettingRouteUseCase
import com.zzanz.swip_android.domain.usecase.preference.SetFcmTokenUseCase
import com.zzanz.swip_android.presentation.view.component.contract.GlobalContract
import com.zzanz.swip_android.presentation.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val setTokenUseCase: SetFcmTokenUseCase,
    private val getLastSettingRouteUseCase: GetLastSettingRouteUseCase
) : BaseViewModel<GlobalContract.Event, GlobalContract.State, GlobalContract.Effect>() {


    override fun createInitialState(): GlobalContract.State {
        return GlobalContract.State(
            isLoading = mutableStateOf(true), startDestination = mutableStateOf(null)
        )
    }

    override fun handleEvent(event: GlobalContract.Event) {
        when (event) {
            is GlobalContract.Event.SetFcmToken -> {
                setTokenUseCase(event.token)
            }

            is GlobalContract.Event.GetLastRoute -> {
                getLastSettingRoute()
            }
        }
    }

    private fun getLastSettingRoute() {
        viewModelScope.launch {
            getLastSettingRouteUseCase.invoke(null).collect {
                when (it) {
                    is Resource.Success -> {
                        var route = NavRoutes.Splash.route
                        if (!it.data.isNullOrEmpty()) {
                            route = it.data
                        }
                        setState(
                            currentState.copy(
                                isLoading = mutableStateOf(false),
                                startDestination = mutableStateOf(route)
                            )
                        )
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

    private fun setTokenUseCase(token: String) {
        viewModelScope.launch {
            setTokenUseCase.invoke(token).collect {
                when (it) {
                    is Resource.Success -> {
                        Timber.d("setTokenUseCase Success")
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