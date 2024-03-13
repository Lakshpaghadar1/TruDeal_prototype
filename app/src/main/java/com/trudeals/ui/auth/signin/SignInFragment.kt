package com.trudeals.ui.auth.signin

import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.res.ResourcesCompat
import com.trudeals.R
import com.trudeals.databinding.AuthSigninFragmentBinding
import com.trudeals.di.component.FragmentComponent
import com.trudeals.exception.ApplicationException
import com.trudeals.ui.auth.forgotpassword.ForgotPasswordFragment
import com.trudeals.ui.auth.signup.SignUpFragment
import com.trudeals.ui.base.BaseFragment
import com.trudeals.ui.home.businessuser.HomeActivityBU
import com.trudeals.ui.home.customeruser.HomeActivityCU
import com.trudeals.ui.home.realestateuser.HomeActivityREU
import com.trudeals.ui.isolated.customeruser.adapter.UserSelectionAdapter
import com.trudeals.utils.constants.RegexConstant
import com.trudeals.utils.extension.applyFilter
import com.trudeals.utils.extension.isVisible
import com.trudeals.utils.extension.setPasswordConstraint
import com.trudeals.utils.extension.trimmedText
import com.trudeals.utils.isEmailValid
import com.trudeals.utils.textdecorator.TextDecorator

class SignInFragment : BaseFragment<AuthSigninFragmentBinding>(), View.OnClickListener {

    //temporary pass isREUSubscribed and isBUSubscribed flags to manage end icons of options in dialog adapter
    private val userSelectionAdapter by lazy { UserSelectionAdapter() }

    override fun inject(fragmentComponent: FragmentComponent) {
        fragmentComponent.inject(this)
    }

    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        attachToRoot: Boolean
    ): AuthSigninFragmentBinding {
        return AuthSigninFragmentBinding.inflate(layoutInflater)
    }

    override fun bindData() {
        init()
        clickListeners()
        setSpannable()
    }

    override fun setUpToolbar() = with(toolbar) {
        showToolbar(true)
            .showBackButton(true)
            .build()
    }

    private fun init() = with(binding) {
        //set visibility
        textViewContinueAsGuest.isVisible(session.isCustomerUser)

        //set filters
        editTextEmailOrUserName.filters = arrayOf(
            editTextEmailOrUserName.applyFilter(
                blockSpecialChar = true, applyEmojiFilter = true
            )
        )
        editTextPassword.filters = arrayOf(editTextPassword.applyFilter())
        editTextPassword.setPasswordConstraint(editTextPassword)
    }

    private fun clickListeners() = with(binding) {
        buttonSignIn.setOnClickListener(this@SignInFragment)
        imageViewPassword.setOnClickListener(this@SignInFragment)
        textViewForgetPass.setOnClickListener(this@SignInFragment)
    }

    override fun onClick(v: View) {
        binding.apply {
            when (v) {
                buttonSignIn -> {
                    if (validation()) {
                        session.isLoggedIn = true
                        when {
                            session.isCustomerUser -> {
                                navigator.loadActivity(HomeActivityCU::class.java)
                                    .byFinishingCurrent()
                                    .start()
                            }
                            session.isRealEstateUser -> {
                                //** temporary subscription management start **//
                                session.isREUSubscribed = true
                                userSelectionAdapter.getSubscribedFlags(
                                    isREUSubscribed = session.isREUSubscribed,
                                    isBUSubscribed = session.isBUSubscribed
                                )
                                //** temporary subscription management end **//
                                navigator.loadActivity(HomeActivityREU::class.java)
                                    .byFinishingCurrent()
                                    .start()
                            }
                            session.isBusinessOwnerUser -> {
                                //** temporary subscription management start **//
                                session.isBUSubscribed = true
                                userSelectionAdapter.getSubscribedFlags(
                                    isREUSubscribed = session.isREUSubscribed,
                                    isBUSubscribed = session.isBUSubscribed
                                )
                                //** temporary subscription management end **//
                                navigator.loadActivity(HomeActivityBU::class.java)
                                    .byFinishingCurrent()
                                    .start()
                            }
                            else -> {}
                        }
                    }
                }
                imageViewPassword -> {
                    showHidePassword(imageViewPassword)
                }
                textViewForgetPass -> {
                    navigator.load(ForgotPasswordFragment::class.java).replace(true)
                }
            }
        }
    }

    private fun setSpannable() = with(binding) {
        TextDecorator.decorate(textViewContinueAsGuest, textViewContinueAsGuest.trimmedText)
            .makeTextClickable(
                colorResId = getColor(R.color.C_ED1D26),
                underlineText = false,
                getString(R.string.label_clickable_guest)
            ) { _, text ->
                when (text) {
                    getString(R.string.label_clickable_guest) -> {
                        session.isGuestUser = true
                        /** user is just entered as guest user, not logged in yet,
                         * so as guest user we are not taking any credentials from user
                         * so logically switching from guest user to REU or BU
                         * should be restricted but currently nothing is decided yet
                         * and for that we are allowing guest user to switch
                         *
                         * NOTE: now as guest user we are not making params$[session -> isLoggedIn] as true
                         * so in this scenario - when guest user switches to REU or BU -> close app -> clear app from recent -> reopen app
                         * it will go to Customer user rather then REU or BU(after switching user)
                         *
                         * need to confirm this from iOS senior sir/Client
                         *
                         * currently as guest user making params[session -> isLoggedIn] as true to satisfy SplashActivity.kt ->
                         * (session.isRealEstateUser && session.isLoggedIn && session.isREUSubscribed) this condition
                         * (check management is in splash screen)
                         */
                        session.isLoggedIn = true
                        navigator.loadActivity(HomeActivityCU::class.java).byFinishingCurrent()
                            .start()
                    }
                }
            }.setTypeface(
                ResourcesCompat.getFont(
                    textViewContinueAsGuest.context,
                    R.font.cerebri_sans_semi_bold
                ), getString(R.string.label_clickable_guest)
            )
            .build()

        TextDecorator.decorate(textViewDontHaveAnAcc, textViewDontHaveAnAcc.trimmedText)
            .makeTextClickable(
                colorResId = getColor(R.color.colorPrimary),
                underlineText = false,
                getString(R.string.label_clickable_sign_up)
            ) { _, text ->
                when (text) {
                    getString(R.string.label_clickable_sign_up) -> {
                        navigator.load(SignUpFragment::class.java).replace(true)
                    }
                }
            }.setTypeface(
                ResourcesCompat.getFont(
                    textViewDontHaveAnAcc.context,
                    R.font.cerebri_sans_semi_bold
                ), getString(R.string.label_clickable_sign_up)
            )
            .build()
    }

    private fun showHidePassword(view: View) = with(binding) {
        if (view.id == R.id.imageViewPassword) {
            if (editTextPassword.transformationMethod.equals(PasswordTransformationMethod.getInstance())) {
                (view as AppCompatImageView).setImageResource(R.drawable.ic_password_show)

                //Show Password
                editTextPassword.transformationMethod =
                    HideReturnsTransformationMethod.getInstance()
                editTextPassword.setSelection(
                    editTextPassword.text?.length ?: 0
                )

            } else {
                (view as AppCompatImageView).setImageResource(R.drawable.ic_password_hide)
                //Hide Password
                editTextPassword.transformationMethod =
                    PasswordTransformationMethod.getInstance()
                editTextPassword.setSelection(
                    editTextPassword.text?.length ?: 0
                )
            }
        }
    }

    private fun validation(): Boolean = with(binding) {
        return try {
            with(validator) {
                submit(editTextEmailOrUserName).checkEmpty()
                    .errorMessage(getString(R.string.validation_enter_email_or_username))
                    .check()

                if (!editTextEmailOrUserName.text.isNullOrEmpty()) {
                    if (isEmailValid(editTextEmailOrUserName.trimmedText)) {
                        submit(editTextEmailOrUserName).checkValidEmail().errorMessage(
                            getString(R.string.validation_valid_email)
                        ).check()
                    } /*else {
                    validator.submit(editTextEmailOrUserName).matchPatter(RegexConstant.USER_NAME)
                        .errorMessage(getString(R.string.validation_valid_user_name)).check()
                }*/
                }

                /*//at the time of api calling just un-comment this part of password validation
                    submit(editTextPassword).checkEmpty()
                .errorMessage(getString(R.string.validation_enter_password))
                .check()*/
                //at the time of api calling just comment this part of password validation
                submit(editTextPassword).checkEmpty()
                    .errorMessage(getString(R.string.validation_enter_password))
                    .checkMinDigits(getInteger(R.integer.int_password_min_length))
                    .errorMessage(getString(R.string.validation_enter_valid_password_signin))
                    .matchPatter(RegexConstant.CHECK_UPPERCASE_LOWERCASE)
                    .errorMessage(getString(R.string.validation_enter_valid_password_signin))
                    .matchPatter(RegexConstant.CHECK_NUMBER_LETTERS)
                    .errorMessage(getString(R.string.validation_enter_valid_password_signin))
                    .matchPatter(RegexConstant.CHECK_ONE_SPECIAL_CHAR)
                    .errorMessage(getString(R.string.validation_enter_valid_password_signin))
                    .check()

                if (!checkboxRememberMe.isChecked) {
                    showMessage("OPEN SAVE PASSWORD BOTTOMSHEET TO ENSURE IS USER WANTS TO SAVE CREDENTIALS")
                }
                true
            }
        } catch (e: ApplicationException) {
            showMessage(e)
            false
        }
    }
}