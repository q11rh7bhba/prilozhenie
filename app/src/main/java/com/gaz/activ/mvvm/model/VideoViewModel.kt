package com.gaz.activ.mvvm.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.gaz.activ.mvvm.data.RegistrationRepository
import com.gaz.activ.mvvm.data.SettingsRepository
import com.gaz.activ.mvvm.entity.Event
import com.gaz.activ.mvvm.entity.RegistrationData
import com.gaz.activ.mvvm.entity.User
import com.gaz.activ.ui.navigation.Screens
import io.reactivex.Scheduler
import io.reactivex.Single
import ru.terrakok.cicerone.Router

class VideoViewModel(
    computationScheduler: Scheduler,
    private val router: Router,
    private val registrationRepository: RegistrationRepository,
    private val settingsRepository: SettingsRepository
) : BaseViewModel(computationScheduler), RegistrationViewModel {

    private val _registrationLiveData = MutableLiveData<Event<User>>(Event())
    val registrationLiveData: LiveData<Event<User>> = _registrationLiveData

    override fun register(user: User) {
        subscribe(
            liveData = _registrationLiveData,
            upstream =  settingsRepository.getRegistrationData().flatMap { registerUser(user, it) }
        )
    }

    private fun registerUser(user: User, registrationData: RegistrationData): Single<User> =
            registrationRepository.register(user, registrationData)
                    .flatMap {
                        settingsRepository.saveRegistrationMarker(true)
                                .andThen(Single.just(it))
                    }

    fun openFinalScreen(user: User) = router.newRootScreen(Screens.SplashMiddleScreen(user))
}