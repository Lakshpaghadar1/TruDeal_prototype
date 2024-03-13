package com.trudeals.ui.isolated.customeruser.fragment

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.InputFilter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.trudeals.R
import com.trudeals.databinding.EditProfileFragmentBinding
import com.trudeals.di.component.FragmentComponent
import com.trudeals.exception.ApplicationException
import com.trudeals.ui.base.BaseFragment
import com.trudeals.ui.common.isolated.IsolatedFullActivity
import com.trudeals.utils.constants.RegexConstant
import com.trudeals.utils.countrycodepicker.CountryCodeConstants
import com.trudeals.utils.countrycodepicker.CountryCodeFragment
import com.trudeals.utils.extension.applyFilter
import com.trudeals.utils.extension.loadImageFromServerAny
import com.trudeals.utils.imagepicker.MediaSelector

class EditProfileFragment : BaseFragment<EditProfileFragmentBinding>(),
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
    ): EditProfileFragmentBinding {
        return EditProfileFragmentBinding.inflate(layoutInflater)
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

    override fun bindData() {
        setFilters()
        clickListener()
        setData()
        setImagePicker()
    }

    override fun setUpToolbar() = with(toolbar) {
        showToolbar(true)
            .showBackButton(true)
            .setToolbarTitle(getString(R.string.label_edit))
            .build()
    }

    /*Media Selection with callback*/
    private fun setImagePicker() = with(binding) {
        mediaSelectHelper.canSelectMultipleImages(false)
        mediaSelectHelper.registerCallback(object : MediaSelector {
            override fun onImageUri(uri: Uri) {
                uri.path?.let {
                    imageViewProfile.loadImageFromServerAny(uri)
                }
            }
        })
    }

    private fun setFilters() = with(binding) {
        editTextUserName.filters =
            arrayOf(
                editTextFirstName.applyFilter
                    (blockSpecialChar = true, applyEmojiFilter = true)
            )
        editTextFirstName.filters = arrayOf(
            editTextFirstName.applyFilter
                (blockSpecialChar = true, blockNumbersChar = true, applyEmojiFilter = true),
            InputFilter.LengthFilter(getInteger(R.integer.int_name_max_length))
        )
        editTextLastName.filters = arrayOf(
            editTextLastName.applyFilter
                (blockSpecialChar = true, blockNumbersChar = true, applyEmojiFilter = true),
            InputFilter.LengthFilter(getInteger(R.integer.int_name_max_length))
        )
        editTextEmailId.filters = arrayOf(
            editTextEmailId.applyFilter(blockSpecialChar = true, applyEmojiFilter = true)
        )
        layoutPhoneNumber.editTextPhoneNumber.filters =
            arrayOf(
                layoutPhoneNumber.editTextPhoneNumber.applyFilter(),
                InputFilter.LengthFilter(resources.getInteger(R.integer.int_phone_number_max_length))
            )
        editTextAddress.filters = arrayOf(editTextAddress.applyFilter(applyEmojiFilter = true))
    }

    private fun clickListener() = with(binding) {
        imageViewProfile.setOnClickListener(this@EditProfileFragment)
        buttonSave.setOnClickListener(this@EditProfileFragment)
        layoutPhoneNumber.textViewCountryCode.setOnClickListener(this@EditProfileFragment)
    }

    override fun onClick(v: View): Unit = with(binding) {
        when (v) {
            imageViewProfile -> {
                mediaSelectHelper.selectOptionsForImagePicker(true)
            }
            buttonSave -> {
                if (validation()) {
                    navigator.goBack()
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

    private fun setData() = with(binding) {
        editTextUserName.setText(getString(R.string.dummy_user_name))
        editTextFirstName.setText(getString(R.string.dummy_first_name))
        editTextLastName.setText(getString(R.string.dummy_last_name))
        editTextEmailId.setText(getString(R.string.dummy_email))
        layoutPhoneNumber.editTextPhoneNumber.setText("1415245121")
        editTextAddress.setText("B103/4, Abc com, AA Road, BB area, CA")
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
                    .errorMessage(getString(R.string.validation_enter_email_or_username))
                    .checkValidEmail().errorMessage(getString(R.string.validation_valid_email))
                    .check()

                submit(layoutPhoneNumber.editTextPhoneNumber).checkEmpty()
                    .errorMessage(getString(R.string.validation_enter_phone_number))
                    .checkMinDigits(resources.getInteger(R.integer.int_phone_number_min_length))
                    .errorMessage(getString(R.string.validation_min_digits_phone_number))
                    .matchPatter(RegexConstant.CHECK_PHONE_NUMBER)
                    .errorMessage(getString(R.string.validation_enter_valid_phone_number))
                    .check()

                submit(editTextAddress).checkEmpty()
                    .errorMessage(getString(R.string.validation_enter_address))
                    .check()
                true
            }
        } catch (e: ApplicationException) {
            showMessage(e)
            false
        }
    }
}