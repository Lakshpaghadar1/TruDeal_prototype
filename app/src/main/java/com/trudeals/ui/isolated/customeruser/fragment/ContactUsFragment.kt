package com.trudeals.ui.isolated.customeruser.fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import com.trudeals.R
import com.trudeals.databinding.ContactUsFragmentBinding
import com.trudeals.di.component.FragmentComponent
import com.trudeals.exception.ApplicationException
import com.trudeals.ui.base.BaseFragment
import com.trudeals.utils.extension.applyFilter
import com.trudeals.utils.extension.scrollableText

class ContactUsFragment : BaseFragment<ContactUsFragmentBinding>(), View.OnClickListener {
    override fun inject(fragmentComponent: FragmentComponent) {
        fragmentComponent.inject(this)
    }

    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        attachToRoot: Boolean
    ): ContactUsFragmentBinding {
        return ContactUsFragmentBinding.inflate(layoutInflater)
    }

    override fun bindData() {
        init()
        clickListeners()
    }

    override fun setUpToolbar() = with(toolbar) {
        showToolbar(true)
            .setToolbarTitle(getString(R.string.label_contact_us))
            .showBackButton(true)
            .setToolbarElevation(R.dimen._1sdp)
            .build()
    }

    private fun init() = with(binding) {
        editTextDescriptions.scrollableText()
        //set filters
        editTextEmail.filters = arrayOf(
            editTextEmail.applyFilter(blockSpecialChar = true, applyEmojiFilter = true))
        editTextSubject.filters = arrayOf(editTextSubject.applyFilter(applyEmojiFilter = true))
        editTextDescriptions.filters = arrayOf(editTextDescriptions.applyFilter())
    }

    private fun clickListeners() = with(binding) {
        buttonSubmit.setOnClickListener(this@ContactUsFragment)
    }

    override fun onClick(v: View) = with(binding) {
        when (v) {
            buttonSubmit -> {
                if (validation()) navigator.goBack()
            }
        }
    }

    private fun validation(): Boolean = with(binding) {
        return try {
            with(validator) {
                submit(editTextEmail).checkEmpty()
                    .errorMessage(getString(R.string.validation_enter_email))
                    .checkValidEmail().errorMessage(getString(R.string.validation_valid_email))
                    .check()

                submit(editTextSubject).checkEmpty()
                    .errorMessage(getString(R.string.validation_enter_subject))
                    .check()

                submit(editTextDescriptions).checkEmpty()
                    .errorMessage(getString(R.string.validation_enter_description))
                    .check()
                true
            }
        } catch (e: ApplicationException) {
            showMessage(e)
            false
        }
    }
}