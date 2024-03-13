package com.trudeals.ui.isolated.customeruser.bottomsheet

import android.text.InputFilter
import android.view.LayoutInflater
import android.view.View
import com.trudeals.R
import com.trudeals.databinding.AddYourDetailBottomsheetBinding
import com.trudeals.di.component.BottomSheetComponent
import com.trudeals.exception.ApplicationException
import com.trudeals.ui.base.BaseBottomSheet
import com.trudeals.utils.constants.RegexConstant
import com.trudeals.utils.extension.applyFilter

class AddYourDetailsBottomsheet : BaseBottomSheet<AddYourDetailBottomsheetBinding>(),
    View.OnClickListener {

    private var onRequestShowingClick: (() -> Unit)? = null

    override fun createViewBinding(inflater: LayoutInflater): AddYourDetailBottomsheetBinding {
        return AddYourDetailBottomsheetBinding.inflate(inflater)
    }

    override fun inject(bottomSheetComponent: BottomSheetComponent) {
        bottomSheetComponent.inject(this)
    }

    override fun bindData() {
        setFilters()
        clickListeners()
    }

    override fun destroyViewBinding() {}

    private fun setFilters() = with(binding) {
        editTextFullName.filters = arrayOf(
            editTextFullName.applyFilter
                (blockSpecialChar = true, blockNumbersChar = true, applyEmojiFilter = true),
            InputFilter.LengthFilter(getInteger(R.integer.int_name_max_length))
        )
        editTextPhoneNumber.filters = arrayOf(editTextPhoneNumber.applyFilter(),
            InputFilter.LengthFilter(getInteger(R.integer.int_phone_number_max_length)))
        editTextNotes.filters = arrayOf(editTextNotes.applyFilter())
    }

    private fun clickListeners() = with(binding) {
        buttonRequestShowing.setOnClickListener(this@AddYourDetailsBottomsheet)
        textViewCancel.setOnClickListener(this@AddYourDetailsBottomsheet)
        imageViewBackButton.setOnClickListener(this@AddYourDetailsBottomsheet)
    }

    override fun onClick(v: View): Unit = with(binding) {
        when (v) {
            buttonRequestShowing -> {
                if(validation()) onRequestShowingClick?.invoke()
            }
            textViewCancel -> {
                this@AddYourDetailsBottomsheet.dismiss()
            }
            imageViewBackButton -> {
                this@AddYourDetailsBottomsheet.dismiss()
            }
        }
    }

    private fun validation(): Boolean = with(binding) {
        return try {
            with(validator) {
                submit(editTextFullName).checkEmpty()
                    .errorMessage(getString(R.string.validation_enter_full_name))
                    .check()

                submit(editTextPhoneNumber).checkEmpty()
                    .errorMessage(getString(R.string.validation_enter_phone_number))
                    .checkMinDigits(getInteger(R.integer.int_phone_number_min_length))
                    .errorMessage(getString(R.string.validation_min_digits_phone_number))
                    .matchPatter(RegexConstant.CHECK_PHONE_NUMBER)
                    .errorMessage(getString(R.string.validation_enter_valid_phone_number))
                    .check()

                submit(editTextNotes).checkEmpty()
                    .errorMessage(getString(R.string.validation_enter_notes))
                    .check()

                true
            }
        } catch (e: ApplicationException) {
            showMessage(e)
            false
        }
    }

    fun setOnRequestShowingClick(onRequestShowingClick: () -> Unit) {
        this.onRequestShowingClick = onRequestShowingClick
    }
}