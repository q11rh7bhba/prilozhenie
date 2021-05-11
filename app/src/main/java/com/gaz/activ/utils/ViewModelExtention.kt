package com.gaz.activ.utils

import androidx.lifecycle.Observer
import com.gaz.activ.mvvm.entity.Event
import timber.log.Timber

inline fun <T> observerEvent(
    crossinline onError: (Throwable?) -> Unit?,
    crossinline onSuccessful: (T?) -> Unit
) = Observer<Event<T>> {
    when (it.state) {
        Event.State.SUCCESSFUL -> onSuccessful.invoke(it.data)
        Event.State.ERROR -> onError.invoke(it.throwable)
        else -> Timber.d(it.state.toString())
    }
}
