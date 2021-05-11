package com.gaz.activ.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.appsflyer.AppsFlyerLib
import com.gaz.activ.R
import com.gaz.activ.mvvm.model.MainViewModel
import com.gaz.activ.ui.BaseActivity
import com.gaz.activ.ui.navigation.MainActivityNavigator

class MainActivity : BaseActivity<MainViewModel>(R.layout.activity_main) {

    override val navigator = MainActivityNavigator(this)

    override fun getModelClass() = MainViewModel::class

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppsFlyerLib.getInstance().start(this)
        model.openVideoScreen()
    }

    companion object {

        fun newIntent(context: Context) = Intent(context, MainActivity::class.java)
    }
}
