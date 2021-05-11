package com.gaz.activ.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import com.gaz.activ.R
import com.gaz.activ.mvvm.entity.User
import com.gaz.activ.mvvm.model.SplashViewModel
import com.gaz.activ.ui.BaseFragment
import com.gaz.activ.utils.observerEvent

class SplashFragment : BaseFragment<SplashViewModel>(R.layout.splash) {

    override fun getModelClass() = SplashViewModel::class

    private val timerObserver = observerEvent<Unit>(::onError) {
        onTimerFinished()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        model.startTimer()
    }

    override fun initObservers() {
        super.initObservers()
        model.timerLiveData.observe(this, timerObserver)
    }

    private fun onTimerFinished() {
        arguments?.getParcelable<User>(USER_ARG)?.let(model::openFinalScreen)
    }

    companion object {

        private const val USER_ARG = "USER_ARG"

        fun newInstance(user: User) =
            SplashFragment().apply {
                arguments = bundleOf(USER_ARG to user)
            }
    }
}