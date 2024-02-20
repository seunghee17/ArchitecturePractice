package com.example.architecturepractice

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.lang.RuntimeException
import java.nio.file.Files.copy
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(): BaseViewModel<MainContract.Event, MainContract.State, MainContract.Effect>() {
    override fun createInitialState(): MainContract.State {
        return MainContract.State(data = 0)
    }

    override fun handleEvent(event: MainContract.Event) {
        when(event){
            is MainContract.Event.Increase -> {
                val newState = currentState.copy(data = currentState.data + 1)
                setState(newState)
            }
            is MainContract.Event.Decrease -> {
                val newState = currentState.copy(data = currentState.data-1)
                setState(newState)
            }
        }
    }

    fun showEffect(){
        setEffect(MainContract.Effect.showToast)
    }


}