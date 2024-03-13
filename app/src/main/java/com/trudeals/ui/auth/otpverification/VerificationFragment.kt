package com.trudeals.ui.auth.otpverification

import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import com.trudeals.R
import com.trudeals.databinding.AuthVerificationFragmentBinding
import com.trudeals.di.component.FragmentComponent
import com.trudeals.exception.ApplicationException
import com.trudeals.ui.auth.dummydata.PhoneNumberData
import com.trudeals.ui.auth.forgotpassword.ForgotPasswordFragment
import com.trudeals.ui.auth.setnewpassword.SetNewPasswordFragment
import com.trudeals.ui.auth.signup.SignUpFragment
import com.trudeals.ui.base.BaseActivity
import com.trudeals.ui.base.BaseFragment
import com.trudeals.ui.common.isolated.IsolatedFullActivity
import com.trudeals.ui.home.businessuser.HomeActivityBU
import com.trudeals.ui.home.customeruser.HomeActivityCU
import com.trudeals.ui.home.realestateuser.HomeActivityREU
import com.trudeals.ui.home.realestateuser.fragment.SubscriptionFragment
import com.trudeals.utils.extension.empty
import com.trudeals.utils.extension.isVisible
import com.trudeals.utils.extension.notEmpty
import com.trudeals.utils.extension.removeIcon
import com.trudeals.utils.otpverificationtextwatcher.GenericKeyEvent
import com.trudeals.utils.otpverificationtextwatcher.GenericTextWatcher
import java.util.*
import java.util.concurrent.TimeUnit

class VerificationFragment : BaseFragment<AuthVerificationFragmentBinding>(),
    View.OnClickListener {

    private lateinit var countDownTimer: CountDownTimer
    private var start: Boolean = false

    override fun inject(fragmentComponent: FragmentComponent) {
        fragmentComponent.inject(this)
    }

    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        attachToRoot: Boolean
    ): AuthVerificationFragmentBinding {
        return AuthVerificationFragmentBinding.inflate(layoutInflater)
    }

    override fun bindData() {
        init()
        clickListeners()
        countDownTimer()
    }

    override fun setUpToolbar() = with(toolbar) {
        showToolbar(true)
            .showBackButton(true)
            .build()
    }

    private fun init() = with(binding) {
        val phoneNumberData = arguments?.getParcelable<PhoneNumberData>(PHONE)
        layoutPhoneNumber.textViewCountryCode.apply {
            //remove dropdown after country code
            removeIcon()
            //set data
            text = phoneNumberData?.countryCode
        }

        layoutPhoneNumber.editTextPhoneNumber.apply {
            //set data
            setText(phoneNumberData?.phoneNumber)
            //if phone number is not editable and if not editable then need to comment
            isClickable = false
            isFocusable = false
            isCursorVisible = false

            /*//if phone number is editable
            //set filters
            filters = arrayOf(layoutPhoneNumber.editTextPhoneNumber.applyFilter(),
            InputFilter.LengthFilter(resources.getInteger(R.integer.int_phone_number_max_length))*/
        }
    }

    private fun clickListeners() = with(binding) {
        setOTPChangeListener()
        buttonVerify.setOnClickListener(this@VerificationFragment)
        textViewResendOTP.setOnClickListener(this@VerificationFragment)

        /*//if phone number is editable
        layoutPhoneNumber.editTextPhoneNumber.setOnClickListener(this@VerificationFragment)*/
    }

    override fun onClick(v: View) = with(binding) {
        when (v) {
            buttonVerify -> {
                if (validation()) {
                    when (arguments?.getString(SOURCE_SCREEN)) {
                        ForgotPasswordFragment::class.java.simpleName -> {
                            navigator.load(SetNewPasswordFragment::class.java)
                                .setBundle(
                                    SetNewPasswordFragment.createBundle(
                                        VerificationFragment::class.java.simpleName
                                    )
                                )
                                .replace(true)
                        }

                        SignUpFragment::class.java.simpleName -> {
                            session.isLoggedIn = true
                            when {
                                session.isCustomerUser -> {
                                    navigator.loadActivity(HomeActivityCU::class.java)
                                        .byFinishingCurrent()
                                        .start()
                                }
                                session.isRealEstateUser -> {
                                    if (session.isREUSubscribed) {
                                        navigator.loadActivity(HomeActivityREU::class.java)
                                            .byFinishingCurrent()
                                            .start()
                                    } else {
                                        navigator.loadActivity(
                                            IsolatedFullActivity::class.java,
                                            SubscriptionFragment::class.java
                                        )
                                            .addBundle(SubscriptionFragment.createBundle(getString(R.string.label_real_estate)))
                                            .start()
                                    }
                                }
                                session.isBusinessOwnerUser -> {
                                    if (session.isBUSubscribed) {
                                        navigator.loadActivity(HomeActivityBU::class.java)
                                            .byFinishingCurrent()
                                            .start()
                                    } else {
                                        navigator.loadActivity(
                                            IsolatedFullActivity::class.java,
                                            SubscriptionFragment::class.java
                                        ).addBundle(SubscriptionFragment.createBundle(getString(R.string.label_business_advertising)))
                                            .start()
                                    }
                                }
                            }
                        }
                    }
                }
            }
            textViewResendOTP -> {
                showMessage(getString(R.string.msg_success_on_otp_send))
                clearFields()
                countDownTimer()
            }
            /*//if phone number is editable
            layoutPhoneNumber.textViewCountryCode -> {
                showMessage("open country code bottom sheet")
            }*/
        }
    }

    private fun clearFields() = with(binding) {
        textInputEditTextOtp1.text?.clear()
        textInputEditTextOtp2.text?.clear()
        textInputEditTextOtp3.text?.clear()
        textInputEditTextOtp4.text?.clear()
    }

    private fun countDownTimer() = with(binding) {
        textViewResendOTP.isEnabled = false
        textViewTimer.isVisible(true)
        textViewResendOTP.setTextColor(getColor(R.color.C_33ED1D26))
        countDownTimer = object : CountDownTimer(100000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                textViewTimer.text = String.format(
                    Locale.ENGLISH,
                    "%02d:%02d",
                    TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished),
                    TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(
                        TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished)
                    )
                ).plus("s")
            }

            override fun onFinish() {
                textViewResendOTP.isEnabled = true
                textViewTimer.isVisible(false)
                textViewResendOTP.setTextColor(getColor(R.color.C_ED1D26))
                start = true
            }
        }.start()
    }

    override fun onDestroyView() {
        countDownTimer.cancel()
        super.onDestroyView()
    }

    private fun setOTPChangeListener() = with(binding) {
        textInputEditTextOtp1.addTextChangedListener(
            GenericTextWatcher(
                textInputEditTextOtp1,
                textInputEditTextOtp2,
                requireActivity() as BaseActivity
            )
        )
        textInputEditTextOtp2.addTextChangedListener(
            GenericTextWatcher(
                textInputEditTextOtp2,
                textInputEditTextOtp3,
                requireActivity() as BaseActivity
            )
        )
        textInputEditTextOtp3.addTextChangedListener(
            GenericTextWatcher(
                textInputEditTextOtp3,
                textInputEditTextOtp4,
                requireActivity() as BaseActivity
            )
        )
        textInputEditTextOtp4.addTextChangedListener(
            GenericTextWatcher(
                textInputEditTextOtp4,
                null,
                requireActivity() as BaseActivity
            )
        )

        textInputEditTextOtp1.setOnKeyListener(
            GenericKeyEvent(
                textInputEditTextOtp1,
                null,
                requireActivity()
            )
        )
        textInputEditTextOtp2.setOnKeyListener(
            GenericKeyEvent(
                textInputEditTextOtp2,
                textInputEditTextOtp1,
                requireActivity()
            )
        )
        textInputEditTextOtp3.setOnKeyListener(
            GenericKeyEvent(
                textInputEditTextOtp3,
                textInputEditTextOtp2,
                requireActivity()
            )
        )
        textInputEditTextOtp4.setOnKeyListener(
            GenericKeyEvent(
                textInputEditTextOtp4,
                textInputEditTextOtp3,
                requireActivity()
            )
        )
    }

    private fun validation(): Boolean = with(binding) {
        try {
            if (textInputLayoutVerificationOtp1.editText!!.empty && textInputLayoutVerificationOtp2.editText!!.notEmpty
                && textInputLayoutVerificationOtp3.editText!!.notEmpty && textInputLayoutVerificationOtp4.editText!!.notEmpty
            ) {
                throw ApplicationException(getString(R.string.validation_enter_valid_otp))

            } else if (textInputLayoutVerificationOtp2.editText!!.empty && textInputLayoutVerificationOtp1.editText!!.notEmpty
                && textInputLayoutVerificationOtp3.editText!!.notEmpty && textInputLayoutVerificationOtp4.editText!!.notEmpty
            ) {
                throw ApplicationException(getString(R.string.validation_enter_valid_otp))

            } else if (textInputLayoutVerificationOtp3.editText!!.empty && textInputLayoutVerificationOtp1.editText!!.notEmpty
                && textInputLayoutVerificationOtp2.editText!!.notEmpty && textInputLayoutVerificationOtp4.editText!!.notEmpty
            ) {
                throw ApplicationException(getString(R.string.validation_enter_valid_otp))

            } else if (textInputLayoutVerificationOtp4.editText!!.empty && textInputLayoutVerificationOtp1.editText!!.notEmpty
                && textInputLayoutVerificationOtp2.editText!!.notEmpty && textInputLayoutVerificationOtp3.editText!!.notEmpty
            ) {
                throw ApplicationException(getString(R.string.validation_enter_valid_otp))

            } else if (textInputLayoutVerificationOtp1.editText!!.empty && textInputLayoutVerificationOtp2.editText!!.empty
                && textInputLayoutVerificationOtp3.editText!!.notEmpty && textInputLayoutVerificationOtp4.editText!!.notEmpty
            ) {
                throw ApplicationException(getString(R.string.validation_enter_valid_otp))

            } else if (textInputLayoutVerificationOtp1.editText!!.empty && textInputLayoutVerificationOtp3.editText!!.empty
                && textInputLayoutVerificationOtp2.editText!!.notEmpty && textInputLayoutVerificationOtp4.editText!!.notEmpty
            ) {
                throw ApplicationException(getString(R.string.validation_enter_valid_otp))

            } else if (textInputLayoutVerificationOtp1.editText!!.empty && textInputLayoutVerificationOtp4.editText!!.empty
                && textInputLayoutVerificationOtp2.editText!!.notEmpty && textInputLayoutVerificationOtp3.editText!!.notEmpty
            ) {
                throw ApplicationException(getString(R.string.validation_enter_valid_otp))

            } else if (textInputLayoutVerificationOtp2.editText!!.empty && textInputLayoutVerificationOtp3.editText!!.empty
                && textInputLayoutVerificationOtp1.editText!!.notEmpty && textInputLayoutVerificationOtp4.editText!!.notEmpty
            ) {
                throw ApplicationException(getString(R.string.validation_enter_valid_otp))

            } else if (textInputLayoutVerificationOtp2.editText!!.empty && textInputLayoutVerificationOtp4.editText!!.empty
                && textInputLayoutVerificationOtp1.editText!!.notEmpty && textInputLayoutVerificationOtp3.editText!!.notEmpty
            ) {
                throw ApplicationException(getString(R.string.validation_enter_valid_otp))

            } else if (textInputLayoutVerificationOtp3.editText!!.empty && textInputLayoutVerificationOtp4.editText!!.empty
                && textInputLayoutVerificationOtp1.editText!!.notEmpty && textInputLayoutVerificationOtp2.editText!!.notEmpty
            ) {
                throw ApplicationException(getString(R.string.validation_enter_valid_otp))

            } else if (textInputLayoutVerificationOtp1.editText!!.empty && textInputLayoutVerificationOtp2.editText!!.empty
                && textInputLayoutVerificationOtp3.editText!!.empty && textInputLayoutVerificationOtp4.editText!!.notEmpty
            ) {
                throw ApplicationException(getString(R.string.validation_enter_valid_otp))

            } else if (textInputLayoutVerificationOtp1.editText!!.empty && textInputLayoutVerificationOtp2.editText!!.empty
                && textInputLayoutVerificationOtp4.editText!!.empty && textInputLayoutVerificationOtp3.editText!!.notEmpty
            ) {
                throw ApplicationException(getString(R.string.validation_enter_valid_otp))

            } else if (textInputLayoutVerificationOtp1.editText!!.empty && textInputLayoutVerificationOtp3.editText!!.empty && textInputLayoutVerificationOtp4.editText!!.empty && textInputLayoutVerificationOtp2.editText!!.notEmpty
            ) {
                throw ApplicationException(getString(R.string.validation_enter_valid_otp))

            } else if (textInputLayoutVerificationOtp2.editText!!.empty && textInputLayoutVerificationOtp3.editText!!.empty
                && textInputLayoutVerificationOtp4.editText!!.empty && textInputLayoutVerificationOtp1.editText!!.notEmpty
            ) {
                throw ApplicationException(getString(R.string.validation_enter_valid_otp))

            } else if (textInputLayoutVerificationOtp1.editText!!.empty &&
                textInputLayoutVerificationOtp2.editText!!.empty &&
                textInputLayoutVerificationOtp3.editText!!.empty &&
                textInputLayoutVerificationOtp4.editText!!.empty
            ) {
                throw ApplicationException(getString(R.string.validation_empty_otp))
            }
            return true
        } catch (e: ApplicationException) {
            showMessage(e)
            return false
        }
    }

    /*private fun validation(): Boolean = with(binding) {
        return try {
            with(validator) {
                submit(layoutPhoneNumber.editTextPhoneNumber).checkEmpty()
                    .errorMessage(getString(R.string.validation_enter_mobile_number))
                    .checkMinDigits(resources.getInteger(R.integer.int_phone_number_min_length))
                    .errorMessage(getString(R.string.validation_min_digits_mobile_number))
                    .matchPatter(RegexConstant.CHECK_PHONE_NUMBER)
                    .errorMessage(getString(R.string.validation_enter_valid_mobile_number))
                    .check()
                true
            }
        } catch (e: ApplicationException) {
            showMessage(e)
            false
        }
    }*/

    companion object {
        private const val SOURCE_SCREEN = "SOURCE_SCREEN"
        private const val PHONE = "PHONE"

        fun createBundle(
            sourceScreen: String,
            phone: PhoneNumberData
        ) =
            bundleOf(
                SOURCE_SCREEN to sourceScreen,
                PHONE to phone
            )
    }
}