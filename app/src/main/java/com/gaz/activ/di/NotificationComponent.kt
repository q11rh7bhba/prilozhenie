package com.gaz.activ.di

import com.gaz.activ.mvvm.data.SettingsRepository
import org.koin.core.KoinComponent
import org.koin.core.inject

class NotificationComponent : KoinComponent {

    val settingRepository by inject<SettingsRepository>()
}