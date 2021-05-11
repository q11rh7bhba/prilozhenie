package com.gaz.activ.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import com.appsflyer.AFInAppEventParameterName
import com.appsflyer.AFInAppEventType
import com.appsflyer.AppsFlyerLib
import com.gaz.activ.R
import com.gaz.activ.mvvm.entity.User
import com.gaz.activ.mvvm.model.WaitOperatorViewModel
import com.gaz.activ.ui.BaseFragment
import kotlinx.android.synthetic.main.fragment_wait_operator.*

class WaitOperatorFragment : BaseFragment<WaitOperatorViewModel>(R.layout.fragment_wait_operator) {

    override fun getModelClass() = WaitOperatorViewModel::class

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val eventValues = HashMap<String, Any>()
        arguments?.getParcelable<User>(USER_ARG)?.let {
            nameTextView.text = it.name
            surnameTextView.text = it.surname
            eventValues.put(AFInAppEventParameterName.COUNTRY, it.country)
        }

        AppsFlyerLib.getInstance().logEvent(requireActivity(), AFInAppEventType.COMPLETE_REGISTRATION, eventValues)
    }

    companion object {

        private const val USER_ARG = "USER_ARG"

        fun newInstance(user: User) =
            WaitOperatorFragment().apply {
                arguments = bundleOf(USER_ARG to user)
            }
    }
}