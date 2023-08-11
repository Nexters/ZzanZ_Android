package com.example.zzanz_android

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.example.zzanz_android.common.Resource
import com.example.zzanz_android.domain.usecase.preference.GetLastSettingRouteUseCase
import com.example.zzanz_android.domain.usecase.preference.SetFcmTokenUseCase
import com.example.zzanz_android.presentation.view.component.contract.BudgetContract
import com.example.zzanz_android.presentation.view.component.contract.GlobalContract
import com.example.zzanz_android.presentation.view.component.contract.GlobalUiEvent
import com.example.zzanz_android.presentation.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val setTokenUseCase: SetFcmTokenUseCase,
    private val getLastSettingRouteUseCase: GetLastSettingRouteUseCase
) : BaseViewModel<GlobalContract.Event, GlobalContract.State, GlobalContract.Effect>() {
    init {
        // TODO - splash 작업 후, 자동 이동 되도록 수정할 예정
        viewModelScope.launch {
            getLastSettingRouteUseCase.invoke(null).collect {
                when (it) {
                    is Resource.Success -> {
                        Timber.e("### getLastSettingRoute - ${it.data}")
                        setEffect(GlobalContract.Effect.SetSettingLastRoute(it.data))
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

    override fun createInitialState(): GlobalContract.State {
        return GlobalContract.State(isLoading = mutableStateOf(false))
    }

    override fun handleEvent(event: GlobalContract.Event) {
        when (event) {
            is GlobalContract.Event.SetFcmToken -> {
                setTokenUseCase(event.token)
            }
        }
    }

    private fun setTokenUseCase(token: String) {
        viewModelScope.launch {
            setTokenUseCase.invoke(token).collect {
                when (it) {
                    is Resource.Success -> {
                        if (it.data) {
                            GlobalUiEvent.showToast("Success Fcm Token Save")
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

}