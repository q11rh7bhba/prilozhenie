package com.gaz.activ.mvvm.entity

data class Event<T>(
    val state: State = State.EMPTY,
    val progressVisible: Boolean = false,
    val data: T? = null,
    val throwable: Throwable? = null
) {

    enum class State {
        EMPTY,
        SUCCESSFUL,
        ERROR
    }
}