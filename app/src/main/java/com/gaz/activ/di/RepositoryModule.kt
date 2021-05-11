package com.gaz.activ.di

import com.gaz.activ.mvvm.data.RegistrationRepository
import com.gaz.activ.mvvm.data.SettingsRepository
import com.gaz.activ.mvvm.data.local.SettingRepositoryLocal
import com.gaz.activ.mvvm.data.network.repository.RegistrationRepositoryNetwork
import org.koin.core.qualifier.named
import org.koin.dsl.module

val repositoryModule = module {

    single<RegistrationRepository> {
        RegistrationRepositoryNetwork(get(), get(named(COMPUTATION)), get())
    }

    single<SettingsRepository> { SettingRepositoryLocal(get()) }
}