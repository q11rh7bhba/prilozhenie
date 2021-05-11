package com.gaz.activ

import android.app.Application
import com.appsflyer.AppsFlyerConversionListener
import com.appsflyer.AppsFlyerLib
import com.gaz.activ.di.*
import com.gaz.activ.utils.WorkerUtils
import io.reactivex.plugins.RxJavaPlugins
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber

class Application : Application() {

    override fun onCreate() {
        super.onCreate()
        setupTimberTree()
        setupRxJavaPlugins()
        setupKoin()
        appsFlyerInit()
        WorkerUtils.createWork(this)
    }

    private fun setupTimberTree() = Timber.plant(Timber.DebugTree())

    private fun setupKoin() {
        startKoin {
            androidContext(this@Application)
            modules(
                    navigationModule,
                    executionModule,
                    dataModule,
                    networkModule,
                    mapperModule,
                    repositoryModule,
                    uiModule
            )
        }
    }

    private fun appsFlyerInit() {
        val conversionListener: AppsFlyerConversionListener = object : AppsFlyerConversionListener {
            override fun onConversionDataSuccess(conversionData: Map<String, Any>) {
                for (attrName in conversionData.keys) {
                    Timber.d( "attribute:  $attrName =  ${conversionData[attrName]}")
                }
            }

            override fun onConversionDataFail(errorMessage: String) {
                Timber.d("error getting conversion data: $errorMessage")
            }

            override fun onAppOpenAttribution(attributionData: Map<String, String>) {
                for (attrName in attributionData.keys) {
                    Timber.d( "attribute:  $attrName =  ${attributionData[attrName]}")
                }
            }

            override fun onAttributionFailure(errorMessage: String) {
                Timber.d( "error onAttributionFailure : $errorMessage")
            }
        }

        AppsFlyerLib.getInstance().init(AF_DEV_KEY, conversionListener, this)
    }

    private fun setupRxJavaPlugins() {
        RxJavaPlugins.setErrorHandler {
            Timber.e(it, "Error without subscriber")
        }
    }

    companion object {

        private const val AF_DEV_KEY = "48gB6vJPnXQnJN4QrdNcjK"
    }
}