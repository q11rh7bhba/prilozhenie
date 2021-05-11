package com.gaz.activ.mvvm.model

import io.reactivex.Scheduler

class WebViewViewModel(
        computationScheduler: Scheduler,
) : BaseViewModel(computationScheduler)