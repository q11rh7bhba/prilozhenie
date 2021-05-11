package com.gaz.activ.ui.navigation

import android.content.Context
import com.gaz.activ.mvvm.entity.User
import com.gaz.activ.ui.activity.MainActivity
import com.gaz.activ.ui.activity.SplashActivity
import com.gaz.activ.ui.fragment.SplashFragment
import com.gaz.activ.ui.fragment.VideoFragment
import com.gaz.activ.ui.fragment.WaitOperatorFragment
import com.gaz.activ.ui.fragment.WebViewFragment
import ru.terrakok.cicerone.android.support.SupportAppScreen

class Screens {

    class MainScreen : SupportAppScreen() {

        override fun getActivityIntent(context: Context) = MainActivity.newIntent(context)
    }

    class VideoScreen : SupportAppScreen() {

        override fun getFragment() = VideoFragment.newInstance()
    }

    class WaitOperatorScreen(private val user: User) : SupportAppScreen() {

        override fun getFragment() = WaitOperatorFragment.newInstance(user)
    }

    class SplashMiddleScreen(private val user: User) : SupportAppScreen() {

        override fun getFragment() = SplashFragment.newInstance(user)
    }

    class WebViewScreen(private val user: User) : SupportAppScreen() {

        override fun getFragment() = WebViewFragment.newInstance(user)
    }
}