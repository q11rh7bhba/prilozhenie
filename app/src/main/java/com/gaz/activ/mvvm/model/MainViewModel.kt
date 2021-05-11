package com.gaz.activ.mvvm.model

import com.gaz.activ.ui.navigation.Screens
import io.reactivex.Scheduler
import ru.terrakok.cicerone.Router

class MainViewModel constructor(
    computationScheduler: Scheduler,
    private val router: Router
) : BaseViewModel(computationScheduler) {

    fun openVideoScreen() = router.newRootScreen(Screens.VideoScreen())
}