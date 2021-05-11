package com.gaz.activ.di

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.koin.core.qualifier.named
import org.koin.dsl.module

const val IO = "IO"
const val COMPUTATION = "COMPUTATION"
const val TIMEOUT_SECONDS: Long = 120

val executionModule = module {
    single(named(IO)) { Schedulers.io() }
    single(named(COMPUTATION)) { AndroidSchedulers.mainThread() }
}