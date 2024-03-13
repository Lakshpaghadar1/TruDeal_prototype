package com.trudeals.ui.auth.forgotpassword

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.InputFilter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.trudeals.R
import com.trudeals.databinding.AuthForgetPasswordFragmentBinding
import com.trudeals.di.component.FragmentComponent
import com.trudeals.exception.ApplicationException
import com.trudeals.ui.auth.dummydata.PhoneNumberData
import com.trudeals.ui.auth.otpverification.VerificationFragment
import com.trudeals.ui.base.BaseFragment
import com.trudeals.ui.common.isolated.IsolatedFullActivity
import com.trudeals.utils.constants.RegexConstant
import com.trudeals.utils.countrycodepicker.CountryCodeConstants
import com.trudeals.utils.countrycodepicker.CountryCodeFragment
import com.trudeals.utils.extension.applyFilter
import com.trudeals.utils.extension.trimmedText

class ForgotPasswordFragment : BaseFragment<AuthForgetPasswordFragmentBinding>(),
    View.OnClickListener {

    //For country code
    private lateinit var countryStartForResult: ActivityResultLauncher<Intent>

    override fun inject(fragmentComponent: FragmentComponent) {
        fragmentComponent.inject(this)
    }

    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        attachToRoot: Boolean
    ): AuthForgetPasswordFragmentBinding {
        return AuthForgetPasswordFragmentBinding.inflate(layoutInflater)
    }

    override fun bindData() {
        setFilters()
        clickListeners()
    }

    override fun setUpToolbar() = with(toolbar) {
        showToolbar(true)
            .showBackButton(true)
            .build()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        countryStartForResult =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    val bundle = result.data?.extras

                    bundle?.let {
                        bundle.getString(CountryCodeConstants.COUNTRY_CODE)?.let {
                            binding.layoutPhoneNumber.textViewCountryCode.text = it
                        }
                    }
                }
            }
    }

    private fun setFilters() = with(binding) {
        layoutPhoneNumber.editTextPhoneNumber.filters =
            arrayOf(layoutPhoneNumber.editTextPhoneNumber.applyFilter(),
                InputFilter.LengthFilter(getInteger(R.integer.int_phone_number_max_length)))
    }

    private fun clickListeners() = with(binding) {
        buttonNext.setOnClickListener(this@ForgotPasswordFragment)
        layoutPhoneNumber.textViewCountryCode.setOnClickListener(this@ForgotPasswordFragment)
    }

    override fun onClick(v: View) = with(binding) {
        when (v) {
            buttonNext -> {
                if (validation()) {
                    navigator.load(VerificationFragment::class.java)
                        .setBundle(
                            VerificationFragment.createBundle(
                                sourceScreen = ForgotPasswordFragment::class.java.simpleName,
                                phone = PhoneNumberData(
                                    countryCode = layoutPhoneNumber.textViewCountryCode.trimmedText,
                                    phoneNumber = layoutPhoneNumber.editTextPhoneNumber.trimmedText
                                )
                            )
                        ).replace(true)
                }
            }
            layoutPhoneNumber.textViewCountryCode -> {
                navigator.loadActivity(
                    IsolatedFullActivity::class.java,
                    CountryCodeFragment::class.java
                ).onResultActivity(countryStartForResult).start()
            }
        }
    }

    private fun validation(): Boolean = with(binding) {
        return try {
            with(validator) {
                submit(layoutPhoneNumber.editTextPhoneNumber).checkEmpty()
                    .errorMessage(getString(R.string.validation_enter_phone_number))
                    .checkMinDigits(getInteger(R.integer.int_phone_number_min_length))
                    .errorMessage(getString(R.string.validation_min_digits_phone_number))
                    .matchPatter(RegexConstant.CHECK_PHONE_NUMBER)
                    .errorMessage(getString(R.string.validation_enter_valid_phone_number))
                    .check()
                true
            }
        } catch (e: ApplicationException) {
            showMessage(e)
            false
        }
    }
}