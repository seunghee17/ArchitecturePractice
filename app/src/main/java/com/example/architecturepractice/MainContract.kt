package com.example.architecturepractice


class MainContract{
    sealed class Event: UiEvent{
        object Increase : Event()
        object Decrease : Event()
    }

    data class State(
        val data : Int
    ):UiState


    sealed class Effect : UiEffect{
        object showToast : Effect()
    }
}

