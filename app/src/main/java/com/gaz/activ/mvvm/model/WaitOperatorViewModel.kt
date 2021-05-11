package com.gaz.activ.mvvm.model

import io.reactivex.Scheduler
import ru.terrakok.cicerone.Router

class WaitOperatorViewModel(
    computationScheduler: Scheduler,
) : BaseViewModel(computationScheduler)