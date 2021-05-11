package com.gaz.activ.di

import org.koin.dsl.module
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.Router

val navigationModule = module {
    single { Cicerone.create(Router()) }
    single { get<Cicerone<Router>>().router }
    single { get<Cicerone<Router>>().navigatorHolder }
}
