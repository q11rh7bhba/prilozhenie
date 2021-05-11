package com.gaz.activ.di

import android.content.Context
import org.koin.dsl.module

private const val SHARED_PREFERENCES_NAME = "PREFERENCES"

val dataModule = module {
    single {
        get<Context>().getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)
    }
}