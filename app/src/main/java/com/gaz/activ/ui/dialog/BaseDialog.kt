package com.gaz.activ.ui.dialog

import androidx.fragment.app.DialogFragment
import com.gaz.activ.ui.BaseActivity
import com.gaz.activ.ui.BaseFragment

open class BaseDialog : DialogFragment() {

    protected fun getBaseFragmentActivity(): BaseActivity<*>? =
        when {
            activity is BaseActivity<*> -> activity as BaseActivity<*>

            parentFragment is BaseFragment<*> ->
                (parentFragment as BaseFragment<*>).getBaseActivity()

            else -> throw IllegalStateException("Fragment is detached")
        }
}