package com.example.zzanz_android.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel<Event : UiEvent, State : UiState, Effect : UiEffect>: ViewModel() {
    private val initialState : State by lazy { createInitialState() }
    abstract fun createInitialState() : State

    val currentState: State
        get() = uiState.value

    private val _state : MutableStateFlow<State> = MutableStateFlow(initialState)
    val uiState = _state.asStateFlow()

    private val _effect : Channel<Effect> = Channel()
    val effect = _effect.receiveAsFlow()

    private val _event : Channel<Event> = Channel()
    val event = _event.receiveAsFlow()

    init {
        subscribeEvents()
    }

    private fun subscribeEvents(){
        viewModelScope.launch {
            event.collect{
                handleEvent(it)
            }
        }
    }

    abstract fun handleEvent(event: Event)

    fun setEvent(event: Event){
        viewModelScope.launch {
            _event.send(event)
        }
    }

    protected fun setState(state: State){
        _state.value = state
    }

    protected fun setEffect(effect: Effect){
        viewModelScope.launch {
            _effect.send(effect)
        }
    }
}

interface UiState
interface UiEvent
interface UiEffect