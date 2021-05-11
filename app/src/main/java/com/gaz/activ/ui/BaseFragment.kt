package com.gaz.activ.ui

import android.os.Bundle
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import com.gaz.activ.mvvm.model.BaseViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber
import kotlin.reflect.KClass

abstract class BaseFragment<M : BaseViewModel>(@LayoutRes layoutId: Int) : Fragment(layoutId) {

    protected val model: M by viewModel(getModelClass())

    fun getBaseActivity() = activity as? BaseActivity<*>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initObservers()
    }

    @CallSuper
    protected open fun initObservers() = Timber.d("initObservers")

    protected abstract fun getModelClass(): KClass<M>

    @CallSuper
    protected open fun onError(throwable: Throwable?) = getBaseActivity()?.onError(throwable)
}