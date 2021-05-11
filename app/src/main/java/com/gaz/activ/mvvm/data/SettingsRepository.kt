package com.gaz.activ.mvvm.data

import androidx.annotation.CheckResult
import com.gaz.activ.mvvm.entity.RegistrationData
import io.reactivex.Completable
import io.reactivex.Single

interface SettingsRepository {

    fun saveRegistrationMarker(marker: Boolean): Completable

    fun getRegistrationMarker(): Single<Boolean>

    @CheckResult
    fun getRegistrationData(): Single<RegistrationData>

    @CheckResult
    fun saveRegistrationData(registrationData: RegistrationData): Completable
}