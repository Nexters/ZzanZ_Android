package com.example.zzanz_android

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.example.zzanz_android.common.Resource
import com.example.zzanz_android.domain.usecase.preference.GetLastSettingRouteUseCase
import com.example.zzanz_android.presentation.contract.GlobalContract
import com.example.zzanz_android.presentation.contract.GlobalUiEvent
import com.example.zzanz_android.presentation.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getLastSettingRouteUseCase: GetLastSettingRouteUseCase
) : BaseViewModel<GlobalContract.Event, GlobalContract.State, GlobalContract.Effect>() {
    init {
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
    }

}