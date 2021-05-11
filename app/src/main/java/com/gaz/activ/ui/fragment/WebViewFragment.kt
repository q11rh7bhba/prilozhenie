package com.gaz.activ.ui.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.webkit.WebViewClient
import androidx.core.os.bundleOf
import com.appsflyer.AFInAppEventParameterName
import com.appsflyer.AppsFlyerLib
import com.gaz.activ.R
import com.gaz.activ.mvvm.entity.User
import com.gaz.activ.mvvm.model.WebViewViewModel
import com.gaz.activ.ui.BaseFragment
import kotlinx.android.synthetic.main.fragment_webview.*

class WebViewFragment : BaseFragment<WebViewViewModel>(R.layout.fragment_webview) {

    override fun getModelClass() = WebViewViewModel::class

    @SuppressLint("SetJavaScriptEnabled")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val eventValues = HashMap<String, Any>()

        arguments?.getParcelable<User>(USER_ARG)?.let { user ->
            eventValues[AFInAppEventParameterName.COUNTRY] = user.country

            user.url?.let {
                webView.settings.javaScriptEnabled = true
                webView.webViewClient = WebViewClient()
                webView.loadUrl(it)
            }
        }

        AppsFlyerLib.getInstance().logEvent(requireActivity(), COMPLETE_REGISTRATION, eventValues)
    }

    companion object {

        private const val COMPLETE_REGISTRATION = "af_complete_registration_url"

        private const val USER_ARG = "USER_ARG"

        fun newInstance(user: User) =
                WebViewFragment().apply {
                    arguments = bundleOf(
                            USER_ARG to user
                    )
                }
    }
}