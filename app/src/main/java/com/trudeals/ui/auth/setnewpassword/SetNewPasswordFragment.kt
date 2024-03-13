package com.trudeals.ui.auth.setnewpassword

import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import com.trudeals.R
import com.trudeals.databinding.AuthSetNewPasswordFragmentBinding
import com.trudeals.di.component.FragmentComponent
import com.trudeals.exception.ApplicationException
import com.trudeals.ui.auth.otpverification.VerificationFragment
import com.trudeals.ui.auth.signin.SignInFragment
import com.trudeals.ui.base.BaseFragment
import com.trudeals.ui.home.customeruser.HomeActivityCU
import com.trudeals.ui.home.realestateuser.HomeActivityREU
import com.trudeals.utils.constants.RegexConstant
import com.trudeals.utils.extension.applyFilter
import com.trudeals.utils.extension.setPasswordConstraint
import com.trudeals.utils.hideView
import com.trudeals.utils.showView

class SetNewPasswordFragment : BaseFragment<AuthSetNewPasswordFragmentBinding>(),
    View.OnClickListener {

    override fun inject(fragmentComponent: FragmentComponent) {
        fragmentComponent.inject(this)
    }

    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        attachToRoot: Boolean
    ): AuthSetNewPasswordFragmentBinding {
        return AuthSetNewPasswordFragmentBinding.inflate(layoutInflater)
    }

    override fun bindData() {
        init()
        clickListeners()
    }

    override fun setUpToolbar() = with(toolbar) {
        showToolbar(true)
            .showBackButton(true)
            .build()
    }

    private fun init() = with(binding) {
        setFilter()
        setEditTextConstraint()
        setVisibility()
    }

    private fun setFilter() = with(binding) {
        editTextOldPassword.filters = arrayOf(editTextOldPassword.applyFilter())
        editTextNewPassword.filters = arrayOf(editTextNewPassword.applyFilter())
        editTextConfirmNewPassword.filters = arrayOf(editTextConfirmNewPassword.applyFilter())
    }

    private fun setEditTextConstraint() = with(binding) {
        editTextOldPassword.setPasswordConstraint(editTextOldPassword)
        editTextNewPassword.setPasswordConstraint(editTextNewPassword)
        editTextConfirmNewPassword.setPasswordConstraint(editTextConfirmNewPassword)
    }

    private fun setVisibility() = with(binding) {
        when (arguments?.getString(SOURCE_SCREEN)) {
            VerificationFragment::class.java.simpleName -> {
                hideView(editTextOldPassword, imageViewOldPassword)
                showView(textViewDescription)
            }
            HomeActivityCU::class.java.simpleName, HomeActivityREU::class.java.simpleName -> {
                showView(editTextOldPassword, imageViewOldPassword)
                hideView(textViewDescription)
            }
        }
    }

    private fun clickListeners() = with(binding) {
        buttonSave.setOnClickListener(this@SetNewPasswordFragment)
        imageViewOldPassword.setOnClickListener(this@SetNewPasswordFragment)
        imageViewNewPassword.setOnClickListener(this@SetNewPasswordFragment)
        imageViewConfirmPassword.setOnClickListener(this@SetNewPasswordFragment)
    }

    override fun onClick(v: View) = with(binding) {
        when (v) {
            buttonSave -> {
                if (validation()) {
                    when (arguments?.getString(SOURCE_SCREEN)) {
                        VerificationFragment::class.java.simpleName -> {
                            navigator.load(SignInFragment::class.java).replace(false)
                        }

                        HomeActivityCU::class.java.simpleName, HomeActivityREU::class.java.simpleName -> {
                            navigator.goBack()
                        }
                    }
                }
            }
            imageViewOldPassword -> {
                showHidePassword(imageViewOldPassword)
            }
            imageViewNewPassword -> {
                showHidePassword(imageViewNewPassword)
            }
            imageViewConfirmPassword -> {
                showHidePassword(imageViewConfirmPassword)
            }
        }
    }

    private fun showHidePassword(view: View) = with(binding) {
        if (view.id == R.id.imageViewOldPassword) {
            if (editTextOldPassword.transformationMethod.equals(PasswordTransformationMethod.getInstance())) {
                (view as AppCompatImageView).setImageResource(R.drawable.ic_password_show)

                //Show Password
                editTextOldPassword.transformationMethod =
                    HideReturnsTransformationMethod.getInstance()
                editTextOldPassword.setSelection(
                    editTextOldPassword.text?.length ?: 0
                )

            } else {
                (view as AppCompatImageView).setImageResource(R.drawable.ic_password_hide)
                //Hide Password
                editTextOldPassword.transformationMethod =
                    PasswordTransformationMethod.getInstance()
                editTextOldPassword.setSelection(
                    editTextOldPassword.text?.length ?: 0
                )
            }
        } else if (view.id == R.id.imageViewNewPassword) {
            if (editTextNewPassword.transformationMethod.equals(PasswordTransformationMethod.getInstance())) {
                (view as AppCompatImageView).setImageResource(R.drawable.ic_password_show)

                //Show Password
                editTextNewPassword.transformationMethod =
                    HideReturnsTransformationMethod.getInstance()
                editTextNewPassword.setSelection(
                    editTextNewPassword.text?.length ?: 0
                )

            } else {
                (view as AppCompatImageView).setImageResource(R.drawable.ic_password_hide)
                //Hide Password
                editTextNewPassword.transformationMethod =
                    PasswordTransformationMethod.getInstance()
                editTextNewPassword.setSelection(
                    editTextNewPassword.text?.length ?: 0
                )

            }
        } else if (view.id == R.id.imageViewConfirmPassword) {
            if (editTextConfirmNewPassword.transformationMethod.equals(
                    PasswordTransformationMethod.getInstance()
                )
            ) {
                (view as AppCompatImageView).setImageResource(R.drawable.ic_password_show)

                //Show Password
                editTextConfirmNewPassword.transformationMethod =
                    HideReturnsTransformationMethod.getInstance()
                editTextConfirmNewPassword.setSelection(
                    editTextConfirmNewPassword.text?.length ?: 0
                )

            } else {
                (view as AppCompatImageView).setImageResource(R.drawable.ic_password_hide)
                //Hide Password
                editTextConfirmNewPassword.transformationMethod =
                    PasswordTransformationMethod.getInstance()
                editTextConfirmNewPassword.setSelection(
                    editTextConfirmNewPassword.text?.length ?: 0
                )

            }
        }
    }

    private fun validation(): Boolean = with(binding) {
        return try {
            with(validator) {
                if (editTextOldPassword.isVisible) {
                    submit(editTextOldPassword).checkEmpty()
                        .errorMessage(getString(R.string.validation_enter_old_password)).check()
                }
                submit(editTextNewPassword).checkEmpty()
                    .errorMessage(getString(R.string.validation_enter_new_password))
                    .checkMinDigits(getInteger(R.integer.int_8))
                    .errorMessage(getString(R.string.validation_enter_valid_password_signup))
                    .matchPatter(RegexConstant.CHECK_UPPERCASE_LOWERCASE)
                    .errorMessage(getString(R.string.validation_enter_valid_password_signup))
                    .matchPatter(RegexConstant.CHECK_NUMBER_LETTERS)
                    .errorMessage(getString(R.string.validation_enter_valid_password_signup))
                    .matchPatter(RegexConstant.CHECK_ONE_SPECIAL_CHAR)
                    .errorMessage(getString(R.string.validation_enter_valid_password_signup))
                    .check()

                submit(editTextConfirmNewPassword).checkEmpty()
                    .errorMessage(getString(R.string.validation_enter_confirm_password))
                    .matchString(binding.editTextNewPassword.text.toString().trim())
                    .errorMessage(getString(R.string.validation_enter_valid_password))
                    .check()
                true
            }
        } catch (e: ApplicationException) {
            showMessage(e)
            false
        }
    }

    companion object {
        private const val SOURCE_SCREEN = "SOURCE_SCREEN"
        fun createBundle(sourceScreen: String) = bundleOf(SOURCE_SCREEN to sourceScreen)
    }
}