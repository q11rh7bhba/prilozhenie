package com.gaz.activ.ui.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.core.util.PatternsCompat
import androidx.core.view.isGone
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.FragmentManager
import com.gaz.activ.R
import com.gaz.activ.mvvm.entity.User
import com.gaz.activ.mvvm.model.RegistrationViewModel
import kotlinx.android.synthetic.main.fragment_registration.view.*
import org.koin.android.ext.android.get
import org.koin.core.parameter.parametersOf

class RegistrationDialog : BaseDialog() {

    private var viewDialog: View? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val view = LayoutInflater.from(context).inflate(R.layout.fragment_registration, null)
        view.registrationButton.setOnClickListener { onRegistrationClick(view) }
        view.nameEditText.addTextChangedListener { onEditListener(view) }
        view.surnameEditText.addTextChangedListener { onEditListener(view) }
        view.emailEditText.addTextChangedListener { onEditListener(view) }
        view.phoneEditText.addTextChangedListener { onEditListener(view) }
        viewDialog = view
        return AlertDialog
            .Builder(getBaseFragmentActivity(), R.style.AlertDialog)
            .setView(view)
            .create()
    }

    fun show(manager: FragmentManager) = super.show(manager, REGISTRATION_TAG)

    private fun onRegistrationClick(view: View) {
        if (validate(view)) {
            view.registrationButton.isEnabled = false
            val name = view.nameEditText.text.toString()
            val surname = view.surnameEditText.text.toString()
            val email = view.emailEditText.text.toString()
            val phone = view.phoneEditText.text.toString()
            val country = view.codePicker.selectedCountryNameCode
            val user = User(name, surname, email, phone, country, null)
            get<RegistrationViewModel> {  parametersOf(targetFragment) }.register(user)
        }
    }

    private fun validate(view: View): Boolean {
        val email = view.emailEditText.text.toString()
        val isValid = PatternsCompat.EMAIL_ADDRESS.matcher(email).matches()
        view.emailErrorTextView.isGone = isValid
        return isValid
    }

    private fun onEditListener(view: View) {
        view.registrationButton.isEnabled = isRegisterButtonEnable(view)
    }

    private fun isRegisterButtonEnable(view: View) =
        view.nameEditText.text.toString().isNotBlank() &&
                view.surnameEditText.text.toString().isNotBlank() &&
                view.emailEditText.text.toString().isNotBlank() &&
                view.phoneEditText.text.toString().isNotBlank()

    private fun enableRegistration() {
        viewDialog?.registrationButton?.isEnabled = true
    }

    companion object {

        private const val REGISTRATION_TAG = "REGISTRATION_TAG"

        fun dismissAllowingStateLoss(manager: FragmentManager) {
            val dialog = manager.findFragmentByTag(REGISTRATION_TAG) as RegistrationDialog?
            dialog?.dismissAllowingStateLoss()
        }

        fun enableRegistration(manager: FragmentManager) {
            val dialog = manager.findFragmentByTag(REGISTRATION_TAG) as RegistrationDialog?
            dialog?.enableRegistration()
        }
    }
}