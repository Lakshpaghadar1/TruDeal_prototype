package com.trudeals.ui.auth.signup

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.InputFilter
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.github.dewinjm.monthyearpicker.MonthYearPickerDialog
import com.github.dewinjm.monthyearpicker.MonthYearPickerDialogFragment
import com.trudeals.R
import com.trudeals.data.URLFactory
import com.trudeals.data.pojo.ResponseBody
import com.trudeals.databinding.AuthSignupFragmentBinding
import com.trudeals.di.component.FragmentComponent
import com.trudeals.exception.ApplicationException
import com.trudeals.ui.auth.dummydata.PhoneNumberData
import com.trudeals.ui.auth.otpverification.VerificationFragment
import com.trudeals.ui.auth.signin.AuthViewModel
import com.trudeals.ui.auth.signin.SignInFragment
import com.trudeals.ui.base.BaseFragment
import com.trudeals.ui.common.isolated.IsolatedFullActivity
import com.trudeals.ui.common.webview.WebViewFragment
import com.trudeals.ui.home.customeruser.HomeActivityCU
import com.trudeals.ui.isolated.dummydata.SelectedUserType
import com.trudeals.ui.isolated.optionsbottomsheet.OptionsBottomSheet
import com.trudeals.utils.DataUtils
import com.trudeals.utils.constants.RegexConstant
import com.trudeals.utils.countrycodepicker.CountryCodeConstants
import com.trudeals.utils.countrycodepicker.CountryCodeFragment
import com.trudeals.utils.extension.*
import com.trudeals.utils.hideView
import com.trudeals.utils.showView
import com.trudeals.utils.textdecorator.TextDecorator
import java.util.*

class SignUpFragment : BaseFragment<AuthSignupFragmentBinding>(), View.OnClickListener,
    MonthYearPickerDialog.OnDateSetListener {

    // For month year picker
    private lateinit var monthPickerDialog: MonthYearPickerDialogFragment
    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[AuthViewModel::class.java]
    }

    //For country code
    private lateinit var countryStartForResult: ActivityResultLauncher<Intent>

    override fun inject(fragmentComponent: FragmentComponent) {
        fragmentComponent.inject(this)
    }

    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        attachToRoot: Boolean
    ): AuthSignupFragmentBinding {
        return AuthSignupFragmentBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //observable()
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

    override fun bindData() {
        init()
        setUpMonthPickerDialog()
        clickListeners()
        setSpannable()
        //getCountryList()
    }

    private fun getCountryList() {
        viewModel.countryList().apply { showLoader() }
    }

    override fun setUpToolbar() = with(toolbar) {
        showToolbar(true)
            .showBackButton(true)
            .build()
    }

    private fun init() = with(binding) {
        setMobileNumberEditText(
            layoutPhoneNumber.viewBottomLine,
            layoutPhoneNumber.editTextPhoneNumber
        )
        //set visibility
        setVisibility()

        if (session.isCustomerUser) {
            editTextSelectUser.setText(getString(R.string.label_user))
            showView(textViewDescription)
        } else if (session.isRealEstateUser) {
            editTextSelectUser.setText(getString(R.string.label_real_estate))
            hideView(textViewDescription)
        } else if (session.isBusinessOwnerUser) {
            editTextSelectUser.setText(getString(R.string.label_business))
            hideView(textViewDescription)
        }

        //set filters
        editTextUserName.filters =
            arrayOf(
                editTextUserName.applyFilter
                    (blockSpecialWithAtChar = true, applyEmojiFilter = true)
            )

        editTextFirstName.setTextConstraint()
        editTextFirstName.filters = arrayOf(
            editTextFirstName.applyFilter(applyEmojiFilter = true),
            InputFilter.LengthFilter(getInteger(R.integer.int_name_max_length))
        )
        editTextLastName.setTextConstraint()
        editTextLastName.filters = arrayOf(
            editTextLastName.applyFilter(applyEmojiFilter = true),
            InputFilter.LengthFilter(getInteger(R.integer.int_name_max_length))
        )
        editTextEmailId.filters = arrayOf(
            editTextEmailId.applyFilter(blockSpecialChar = true, applyEmojiFilter = true)
        )
        layoutPhoneNumber.editTextPhoneNumber.filters = arrayOf(
            layoutPhoneNumber.editTextPhoneNumber.applyFilter(),
            InputFilter.LengthFilter(getInteger(R.integer.int_phone_number_max_length))
        )
        editTextAddress.filters = arrayOf(editTextAddress.applyFilter(applyEmojiFilter = true))
        editTextPassword.filters = arrayOf(editTextPassword.applyFilter())
        editTextPromotionalCode.setTextConstraint(allowAlphanumeric = true, allowSpace = false)
        editTextPromotionalCode.filters = arrayOf(
            editTextFirstName.applyFilter(applyEmojiFilter = true)
        )

        editTextCardNumber.filters = arrayOf(
            editTextCardNumber.applyFilter(blockSpecialChar = true),
            InputFilter.LengthFilter(getInteger(R.integer.int_card_max_length))
        )
        editTextPassword.setPasswordConstraint(editTextPassword)

        //set max length
        editTextCVV.filters =
            arrayOf<InputFilter>(InputFilter.LengthFilter(getInteger(R.integer.int_cvv_max_length)))
    }

    private fun setVisibility() = with(binding) {
        textViewContinueAsGuest.isVisible(session.isCustomerUser)
        constraintLayoutPaymentInfo.isVisible(!session.isCustomerUser)
    }

    private fun clickListeners() = with(binding) {
        editTextSelectUser.setOnClickListener(this@SignUpFragment)
        buttonRegister.setOnClickListener(this@SignUpFragment)
        imageViewPassword.setOnClickListener(this@SignUpFragment)
        editTextExpDate.setOnClickListener(this@SignUpFragment)
        layoutPhoneNumber.textViewCountryCode.setOnClickListener(this@SignUpFragment)
        monthPickerDialog.setOnDateSetListener(this@SignUpFragment)
    }

    override fun onClick(v: View) = with(binding) {
        when (v) {
            editTextSelectUser -> {
                hideKeyBoard()
                OptionsBottomSheet<SelectedUserType>().setTitle("Select User Type")
                    .setOptionsList(DataUtils.selectedUserType())
                    .setOnPositiveButtonClickListener { selectedOption ->
                        editTextSelectUser.setText(selectedOption.option)
                        when (selectedOption.option) {
                            getString(R.string.label_user) -> {
                                setDefaultUser()
                                session.isCustomerUser = true
                                setVisibility()
                                showView(textViewDescription)
                            }
                            getString(R.string.label_real_estate) -> {
                                setDefaultUser()
                                session.isRealEstateUser = true
                                setVisibility()
                                hideView(textViewDescription)
                            }
                            getString(R.string.label_business) -> {
                                setDefaultUser()
                                session.isBusinessOwnerUser = true
                                setVisibility()
                                hideView(textViewDescription)
                            }
                        }
                    }.setSelectedOption(editTextSelectUser.trimmedText)
                    .show(childFragmentManager, "SELECT_USER_TYPE")
            }
            buttonRegister -> {
                if (validation() && checkboxAgreeCondition.isChecked) {
                    navigator.load(VerificationFragment::class.java)
                        .setBundle(
                            VerificationFragment.createBundle(
                                sourceScreen = SignUpFragment::class.java.simpleName,
                                phone = PhoneNumberData(
                                    countryCode = layoutPhoneNumber.textViewCountryCode.trimmedText,
                                    phoneNumber = layoutPhoneNumber.editTextPhoneNumber.trimmedText
                                )
                            )
                        ).replace(true)
                }
            }
            imageViewPassword -> {
                showHidePassword(imageViewPassword)
            }
            editTextExpDate -> {
                monthPickerDialog.show(
                    childFragmentManager,
                    this@SignUpFragment::class.java.simpleName
                )
            }
            layoutPhoneNumber.textViewCountryCode -> {
                navigator.loadActivity(
                    IsolatedFullActivity::class.java,
                    CountryCodeFragment::class.java
                ).onResultActivity(countryStartForResult).start()
            }
        }
    }

    private fun setDefaultUser() {
        session.isCustomerUser = false
        session.isRealEstateUser = false
        session.isBusinessOwnerUser = false
    }

    override fun onDateSet(year: Int, monthOfYear: Int) = with(binding) {
        val selectedDate = String.format(
            Locale.ENGLISH,
            "%02d - %d",
            monthOfYear + 1,
            year.toString().toInt()
        )
        editTextExpDate.setText(selectedDate)
    }

    private fun setSpannable() = with(binding) {
        TextDecorator.decorate(checkboxAgreeCondition, checkboxAgreeCondition.trimmedText)
            .makeTextClickable(
                colorResId = getColor(R.color.C_ED1D26),
                underlineText = true,
                getString(R.string.label_clickable_terms_of_use),
                getString(R.string.label_clickable_privacy_policy)
            ) { _, text ->
                when (text) {
                    getString(R.string.label_clickable_terms_of_use) -> {
                        navigator.loadActivity(
                            IsolatedFullActivity::class.java,
                            WebViewFragment::class.java
                        ).addBundle(
                            WebViewFragment.createBundle(
                                title = R.string.label_clickable_terms_of_use,
                                url = getString(R.string.dummy_url_webView)
                            )
                        ).start()
                    }
                    getString(R.string.label_clickable_privacy_policy) -> {
                        navigator.loadActivity(
                            IsolatedFullActivity::class.java,
                            WebViewFragment::class.java
                        ).addBundle(
                            WebViewFragment.createBundle(
                                title = R.string.label_privacy_policy,
                                url = getString(R.string.dummy_url_webView)
                            )
                        ).start()
                    }
                }
            }.setTypeface(
                ResourcesCompat.getFont(
                    checkboxAgreeCondition.context,
                    R.font.cerebri_sans_semi_bold
                ),
                getString(R.string.label_clickable_terms_of_use),
                getString(R.string.label_clickable_privacy_policy)
            )
            .build()

        TextDecorator.decorate(textViewContinueAsGuest, textViewContinueAsGuest.trimmedText)
            .makeTextClickable(
                colorResId = getColor(R.color.C_ED1D26),
                underlineText = false,
                getString(R.string.label_clickable_guest)
            ) { _, text ->
                when (text) {
                    getString(R.string.label_clickable_guest) -> {
                        session.isGuestUser = true
                        session.isLoggedIn = true  //check sign in fragment comments
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

        TextDecorator.decorate(textViewAlreadyHaveAnAcc, textViewAlreadyHaveAnAcc.trimmedText)
            .makeTextClickable(
                colorResId = getColor(R.color.C_ED1D26),
                underlineText = false,
                getString(R.string.label_clickable_sign_in)
            ) { _, text ->
                when (text) {
                    getString(R.string.label_clickable_sign_in) -> {
                        navigator.load(SignInFragment::class.java).replace(false)
                    }
                }
            }.setTypeface(
                ResourcesCompat.getFont(
                    textViewAlreadyHaveAnAcc.context,
                    R.font.cerebri_sans_semi_bold
                ), getString(R.string.label_clickable_sign_in)
            )
            .build()
    }

    private fun setUpMonthPickerDialog() {
        val calendar = Calendar.getInstance()
        val yearSelected = calendar[Calendar.YEAR]
        val monthSelected = calendar[Calendar.MONTH]

        val minDate = calendar.timeInMillis

        calendar.clear()
        calendar[3000, 11] = 31 // Set maximum date to show in dialog
        val maxDate = calendar.timeInMillis // Get milliseconds of the modified date

        monthPickerDialog = MonthYearPickerDialogFragment
            .getInstance(
                monthSelected,
                yearSelected,
                minDate,
                maxDate
            )
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
                submit(editTextUserName).checkEmpty()
                    .errorMessage(getString(R.string.validation_enter_user_name))
                    /*.matchPatter(RegexConstant.USER_NAME)
                    .errorMessage(getString(R.string.validation_valid_user_name))*/.check()

                submit(editTextFirstName).checkEmpty()
                    .errorMessage(getString(R.string.validation_enter_first_name))
                    .check()

                submit(editTextLastName).checkEmpty()
                    .errorMessage(getString(R.string.validation_enter_last_name))
                    .check()

                submit(editTextEmailId).checkEmpty()
                    .errorMessage(getString(R.string.validation_enter_email))
                    .checkValidEmail().errorMessage(getString(R.string.validation_valid_email))
                    .check()

                submit(layoutPhoneNumber.editTextPhoneNumber).checkEmpty()
                    .errorMessage(getString(R.string.validation_enter_phone_number))
                    .checkMinDigits(getInteger(R.integer.int_phone_number_min_length))
                    .errorMessage(getString(R.string.validation_min_digits_phone_number))
                    .matchPatter(RegexConstant.CHECK_PHONE_NUMBER)
                    .errorMessage(getString(R.string.validation_enter_valid_phone_number))
                    .check()

                submit(editTextAddress).checkEmpty()
                    .errorMessage(getString(R.string.validation_enter_address))
                    .check()

                submit(editTextPassword).checkEmpty()
                    .errorMessage(getString(R.string.validation_enter_password))
                    .checkMinDigits(getInteger(R.integer.int_password_min_length))
                    .errorMessage(getString(R.string.validation_enter_valid_password_signup))
                    .matchPatter(RegexConstant.CHECK_UPPERCASE_LOWERCASE)
                    .errorMessage(getString(R.string.validation_enter_valid_password_signup))
                    .matchPatter(RegexConstant.CHECK_NUMBER_LETTERS)
                    .errorMessage(getString(R.string.validation_enter_valid_password_signup))
                    .matchPatter(RegexConstant.CHECK_ONE_SPECIAL_CHAR)
                    .errorMessage(getString(R.string.validation_enter_valid_password_signup))
                    .check()

                /*================ card validation start ================*/
                if (constraintLayoutPaymentInfo.isVisible) {
                    //promotional code validation pending
                    submit(editTextCardNumber).checkEmpty()
                        .errorMessage(R.string.validation_enter_card_number)
                        .checkMinDigits(resources.getInteger(R.integer.int_card_min_length))
                        .errorMessage(R.string.validation_enter_valid_card_number)
                        .check()

                    submit(editTextExpDate).checkEmpty()
                        .errorMessage(getString(R.string.validation_select_exp_date))
                        .check()

                    submit(editTextCVV).checkEmpty()
                        .errorMessage(R.string.validation_enter_cvv)
                        .checkMinDigits(resources.getInteger(R.integer.int_cvv_min_length))
                        .errorMessage(R.string.validation_enter_valid_cvv)
                        .check()
                }
                if (!checkboxAgreeCondition.isChecked) {
                    showMessage(getString(R.string.validation_agrees_terms_of_use_and_pp))
                }
                /*================ card validation end ================*/
                true
            }
        } catch (e: ApplicationException) {
            showMessage(e)
            false
        }
    }

    private fun observable() {
        viewModel.countryList.observe(this, this::handleCountryList) {
            hideLoader()
            true
        }
    }

    private fun handleCountryList(response: ResponseBody<Any>) {
        hideLoader()
        when (response.responseCode) {
            URLFactory.ResponseCode.SUCCESS -> {
                Log.e("TAG", "handleCountryList: success ${response.data}", )
            }
            URLFactory.ResponseCode.NO_DATA_FOUND, URLFactory.ResponseCode.INVALID_OR_FAILURE -> {
                Log.e("TAG", "handleCountryList: fail ${response.message}", )
            }
        }
    }
}