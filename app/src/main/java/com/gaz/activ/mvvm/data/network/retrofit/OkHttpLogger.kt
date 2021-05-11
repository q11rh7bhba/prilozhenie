package com.gaz.activ.mvvm.data.network.retrofit

import okhttp3.logging.HttpLoggingInterceptor.Logger
import timber.log.Timber

class OkHttpLogger : Logger {

    override fun log(message: String) = Timber.d(message)
}