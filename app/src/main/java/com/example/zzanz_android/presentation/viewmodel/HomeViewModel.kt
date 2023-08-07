package com.example.zzanz_android.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.example.zzanz_android.common.Resource
import com.example.zzanz_android.domain.model.ChallengeModel
import com.example.zzanz_android.domain.usecase.home.GetChallengeListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getChallengeListUseCase: GetChallengeListUseCase
) : BaseViewModel<HomeUiEvent, HomeState, HomeEffect>() {
    override fun createInitialState(): HomeState {
        return HomeState(ChallengeListState.Loading)
    }

    init {
        setEvent(HomeUiEvent.LoadingChallengeList)
    }

    override fun handleEvent(event: HomeUiEvent) {
        when (event) {
            HomeUiEvent.LoadingChallengeList -> {
                fetchChallengeList()
            }
        }
    }

    private fun fetchChallengeList() {
        viewModelScope.launch {
            getChallengeListUseCase().collect { result ->
                when (result) {
                    is Resource.Success -> {
                        setState(
                            currentState.copy(
                                challengeList = ChallengeListState.Success(flow { emit(result.data) })
                            )
                        )
                    }
                    is Resource.Error -> {
                        // TODO : gowoon - error handling in presentation
                    }
                }
            }
        }
    }

}

sealed class HomeUiEvent : UiEvent {
    object LoadingChallengeList : HomeUiEvent()
}

sealed class HomeEffect : UiEffect

data class HomeState(
    val challengeList: ChallengeListState
) : UiState

sealed class ChallengeListState {
    object Loading : ChallengeListState()
    object Error : ChallengeListState()
    data class Success(val data: Flow<PagingData<ChallengeModel>>) : ChallengeListState()
}