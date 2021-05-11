package com.gaz.activ.di

import com.google.gson.GsonBuilder
import com.gaz.activ.BuildConfig
import com.gaz.activ.mvvm.data.network.retrofit.ApiService
import com.gaz.activ.mvvm.data.network.retrofit.OkHttpLogger
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val networkModule = module {

    single { GsonBuilder().setLenient().create() }

    single<Interceptor>(named<HttpLoggingInterceptor>()) {
        HttpLoggingInterceptor(OkHttpLogger()).setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    single {
        OkHttpClient.Builder()
            .connectTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .readTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .writeTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .apply {
                if (BuildConfig.DEBUG) addNetworkInterceptor(get<Interceptor>(named<HttpLoggingInterceptor>()))
            }
            .build()
    }

    single<ApiService> {
        Retrofit.Builder()
            .client(get())
            .baseUrl(BuildConfig.SERVER_ENDPOINT)
            .addConverterFactory(GsonConverterFactory.create(get()))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(get(named(IO))))
            .build()
            .create(ApiService::class.java)
    }
}