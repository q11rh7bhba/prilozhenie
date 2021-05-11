package com.gaz.activ.mvvm.model

import androidx.annotation.CallSuper
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gaz.activ.mvvm.entity.Event
import io.reactivex.Completable
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy

abstract class BaseViewModel(
    private val computationScheduler: Scheduler
) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    @CallSuper
    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }

    protected fun <T : Any> subscribe(
        liveData: MutableLiveData<Event<T>>,
        upstream: Single<T>
    ) =
        if (isDisposed()) {
            null
        } else {
            upstream
                .subscribeOn(computationScheduler)
                .observeOn(computationScheduler)
                .subscribeBy(
                    onError = { t -> onError(liveData, t) },
                    onSuccess = { item -> onSuccess(liveData, item) }
                ).addTo(compositeDisposable)
        }

    protected fun subscribe(
        liveData: MutableLiveData<Event<Unit>>,
        upstream: Completable
    ) =
        if (isDisposed()) {
            null
        } else {
            upstream
                .subscribeOn(computationScheduler)
                .observeOn(computationScheduler)
                .subscribeBy({ t -> onError(liveData, t) }, { onComplete(liveData) })
                .addTo(compositeDisposable)
        }


    private fun <T : Any> onError(liveData: MutableLiveData<Event<T>>, throwable: Throwable) =
        liveData.postValue(
            liveData.value?.copy(
                throwable = throwable,
                state = Event.State.ERROR,
                progressVisible = false
            )
        )

    private fun <T : Any> onComplete(liveData: MutableLiveData<Event<T>>) =
        liveData.postValue(
            liveData.value?.copy(
                state = Event.State.SUCCESSFUL,
                progressVisible = false,
                throwable = null
            )
        )

    private fun <T : Any> onSuccess(liveData: MutableLiveData<Event<T>>, item: T) =
        liveData.postValue(
            liveData.value?.copy(
                data = item,
                state = Event.State.SUCCESSFUL,
                progressVisible = false
            )
        )

    private fun isDisposed(): Boolean = compositeDisposable.isDisposed
}