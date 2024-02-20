package com.example.architecturepractice

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.reduce
import kotlinx.coroutines.launch

interface UiState
interface UiEffect
interface UiEvent

abstract class BaseViewModel<Event: UiEvent, State : UiState, Effect : UiEffect> : ViewModel() {
    private val initialState : State by lazy {createInitialState()}
    abstract fun createInitialState() : State

    private val _uiState : MutableStateFlow<State> = MutableStateFlow(initialState)
    val currentState: State get() = _uiState.value

    val uiState =  _uiState.asStateFlow()

    private val _event : MutableSharedFlow<Event> = MutableSharedFlow()
    val event = _event.asSharedFlow()

    private val _effect : Channel<Effect> = Channel()
    val effect = _effect.receiveAsFlow()

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
    abstract fun handleEvent(event : Event)

    fun setEvent(event:Event){
        val newEvent = event
        viewModelScope.launch {
            _event.emit(newEvent)
        }
    }
    protected fun setState(state: State){
        _uiState.value = state
    }
    //부수효과 처리하기 위해 정의된 메서드
    //이 함수를 정의한 클래스 또는 하위클래스에서만 접근할 수 있다
    protected fun setEffect(effect : Effect){
        viewModelScope.launch {
            _effect.send(effect)
        }
    }
}