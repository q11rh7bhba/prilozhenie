package com.gaz.activ.ui

import android.os.Bundle
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import com.gaz.activ.mvvm.model.BaseViewModel
import com.gaz.activ.ui.navigation.BaseNavigator
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.terrakok.cicerone.NavigatorHolder
import timber.log.Timber
import kotlin.reflect.KClass

abstract class BaseActivity<M : BaseViewModel>(@LayoutRes layoutId: Int) :
    AppCompatActivity(layoutId) {

    protected abstract val navigator: BaseNavigator

    protected val navigatorHolder by inject<NavigatorHolder>()

    protected val model: M by viewModel(getModelClass())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initObservers()
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        navigatorHolder.removeNavigator()
        super.onPause()
    }

    @CallSuper
    protected open fun initObservers() = Timber.d("initObservers")

    protected abstract fun getModelClass(): KClass<M>

    open fun onError(throwable: Throwable?) = Timber.e(throwable)
}