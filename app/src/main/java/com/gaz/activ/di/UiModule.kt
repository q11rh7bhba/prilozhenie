package com.gaz.activ.di

import com.gaz.activ.mvvm.model.*
import com.gaz.activ.ui.BaseFragment
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.viewmodel.ext.android.getViewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val uiModule = module {

    viewModel { MainViewModel(get(named(COMPUTATION)), get()) }

    viewModel { SplashViewModel(get(named(COMPUTATION)), get(), get()) }

    viewModel { VideoViewModel(get(named(COMPUTATION)), get(), get(), get()) }

    viewModel { WaitOperatorViewModel(get(named(COMPUTATION))) }

    viewModel { WebViewViewModel(get(named(COMPUTATION))) }

    factory<RegistrationViewModel> { (fragment: BaseFragment<*>) ->
        fragment.getViewModel(VideoViewModel::class)
    }
}