package com.gaz.activ.mvvm.model

import android.os.RemoteException
import android.text.TextUtils
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.android.installreferrer.api.InstallReferrerClient
import com.android.installreferrer.api.InstallReferrerStateListener
import com.gaz.activ.mvvm.data.SettingsRepository
import com.gaz.activ.mvvm.entity.Event
import com.gaz.activ.mvvm.entity.RegistrationData
import com.gaz.activ.mvvm.entity.User
import com.gaz.activ.ui.navigation.Screens
import io.reactivex.Completable
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.SingleEmitter
import ru.terrakok.cicerone.Router
import timber.log.Timber
import java.util.concurrent.TimeUnit

class SplashViewModel(
    computationScheduler: Scheduler,
    private val router: Router,
    private val settingsRepository: SettingsRepository,
) : BaseViewModel(computationScheduler) {

    private val _timerLiveData = MutableLiveData<Event<Unit>>(Event())
    var timerLiveData: LiveData<Event<Unit>> = _timerLiveData

    private val _referrerLiveData = MutableLiveData<Event<Unit>>(Event())
    var referrerLiveData: LiveData<Event<Unit>> = _referrerLiveData

    fun initParam(referrerClient: InstallReferrerClient) {
        subscribe(
            _referrerLiveData,
            settingsRepository.getRegistrationData().flatMapCompletable {
                if (it.site.isNullOrEmpty()) {
                    initReferrer(referrerClient)
                        .flatMapCompletable { data ->
                            settingsRepository.saveRegistrationData(data)
                                .andThen(Completable.fromCallable { referrerClient.endConnection() })
                        }
                } else {
                    Completable.complete().delay(TIMEOUT, TimeUnit.SECONDS)
                }
            }
        )
    }

    fun startTimer() =
        subscribe(_timerLiveData, Completable.complete().delay(TIMEOUT, TimeUnit.SECONDS))

    fun openMainScreen() = router.newRootScreen(Screens.MainScreen())

    fun openFinalScreen(user: User) =
            user.url?.let {
                router.newRootScreen(Screens.WebViewScreen(user))
            } ?: router.newRootScreen(Screens.WaitOperatorScreen(user))

    private fun initReferrer(referrerClient: InstallReferrerClient) =
        Single.create<RegistrationData> {
            try {
                referrerClient.startConnection(getReferrerListener(referrerClient, it))
            } catch (e: Exception) {
                it.onSuccess(RegistrationData())
            }
        }

    private fun getReferrerListener(
        referrerClient: InstallReferrerClient,
        singleEmitter: SingleEmitter<RegistrationData>
    ) =
        object : InstallReferrerStateListener {
            override fun onInstallReferrerSetupFinished(responseCode: Int) {
                when (responseCode) {
                    InstallReferrerClient.InstallReferrerResponse.OK -> {
                        var registrationData = RegistrationData()
                        try {
                            val response = referrerClient.installReferrer
                            registrationData = getRegistrationData(response.installReferrer)
                        } catch (e: RemoteException) {
                            Timber.e(e)
                        }
                        singleEmitter.onSuccess(registrationData)
                    }

                    InstallReferrerClient.InstallReferrerResponse.FEATURE_NOT_SUPPORTED -> {
                        singleEmitter.onSuccess(RegistrationData())
                    }

                    InstallReferrerClient.InstallReferrerResponse.SERVICE_UNAVAILABLE -> {
                        singleEmitter.onSuccess(RegistrationData())
                    }
                    else -> singleEmitter.onSuccess(RegistrationData())
                }
            }

            override fun onInstallReferrerServiceDisconnected() {
                singleEmitter.onSuccess(RegistrationData())
            }
        }

    private fun getRegistrationData(referrerUrl: String) : RegistrationData {
        var registrationData = RegistrationData()
        if (!TextUtils.isEmpty(referrerUrl)) {
            val utms = referrerUrl.split("&").toTypedArray()
            for (utm in utms) {
                if (utm.contains("utm_medium")) {
                    val site = utm.substring(utm.indexOf("=") + 1)
                    registrationData = registrationData.copy(site = site)
                }
            }
        }
        return registrationData
    }

    companion object {

        const val TIMEOUT: Long = 1
    }
}