package com.gaz.activ.mvvm.data.local

import android.content.SharedPreferences
import androidx.core.content.edit
import com.gaz.activ.mvvm.data.SettingsRepository
import com.gaz.activ.mvvm.entity.RegistrationData
import io.reactivex.Completable
import io.reactivex.Single

class SettingRepositoryLocal(
        private val preferences: SharedPreferences
) : SettingsRepository {

    override fun getRegistrationMarker() =
            Single.fromCallable {
                preferences.getBoolean(REGISTRATION_MARKER, false)
            }

    override fun saveRegistrationMarker(marker: Boolean) =
            Completable.fromCallable {
                preferences.edit(true) {
                    putBoolean(REGISTRATION_MARKER, marker)
                }
            }

    override fun getRegistrationData() =
        Single.fromCallable {
            val site = preferences.getString(SITE_KEY, null)
            RegistrationData(site)
        }

    override fun saveRegistrationData(registrationData: RegistrationData) =
        Completable.fromCallable {
            preferences.edit(true) {
                putString(SITE_KEY, registrationData.site)
            }
        }

    companion object {

        private const val SITE_KEY = "SITE_KEY"

        private const val REGISTRATION_MARKER = "REGISTRATION_MARKER"
    }
}