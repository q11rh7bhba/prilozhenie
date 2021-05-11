package com.gaz.activ.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.android.installreferrer.api.InstallReferrerClient
import com.appsflyer.AppsFlyerLib
import com.gaz.activ.R
import com.gaz.activ.mvvm.model.SplashViewModel
import com.gaz.activ.ui.BaseActivity
import com.gaz.activ.ui.navigation.SplashActivityNavigator
import com.gaz.activ.utils.observerEvent

class SplashActivity : BaseActivity<SplashViewModel>(R.layout.splash) {

    override val navigator = SplashActivityNavigator(this)

    private val referrerObserver = observerEvent<Unit>(::onError) { model.openMainScreen() }

    override fun getModelClass() = SplashViewModel::class

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppsFlyerLib.getInstance().start(this)
        val referrerClient: InstallReferrerClient = InstallReferrerClient.newBuilder(this).build()
        model.initParam(referrerClient)
    }

    override fun initObservers() {
        super.initObservers()
        model.referrerLiveData.observe(this, referrerObserver)
    }

    companion object {

        fun newIntent(context: Context) = Intent(context, SplashActivity::class.java)
    }
}