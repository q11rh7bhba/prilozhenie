package com.gaz.activ.di

import com.gaz.activ.mvvm.data.mapper.UserToUserRequestMapper
import org.koin.dsl.module

val mapperModule = module {

    single { UserToUserRequestMapper() }
}