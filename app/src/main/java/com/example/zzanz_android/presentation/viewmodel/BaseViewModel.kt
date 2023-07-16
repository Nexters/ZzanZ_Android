package com.example.zzanz_android.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel: ViewModel() {
    private val initialState : UiState by lazy { createInitialState() }
    abstract fun createInitialState() : UiState

    private val _state : MutableStateFlow<UiState> = MutableStateFlow(initialState)
    val uiState = _state.asStateFlow()

    private val _effect : Channel<UiEffect> = Channel()
    val effect = _effect.receiveAsFlow()

    private val _event : Channel<UiEvent> = Channel()
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

    abstract fun handleEvent(event: UiEvent)

    fun setEvent(event: UiEvent){
        viewModelScope.launch {
            _event.send(event)
        }
    }

    protected fun setState(state: UiState){
        _state.value = state
    }

    protected fun setEffect(effect: UiEffect){
        viewModelScope.launch {
            _effect.send(effect)
        }
    }
}

interface UiState
interface UiEvent
interface UiEffect