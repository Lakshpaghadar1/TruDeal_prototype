package com.trudeals.ui.isolated.realestateuser.dialog

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.trudeals.R
import com.trudeals.databinding.ReasonToCancelDialogBinding
import com.trudeals.exception.ApplicationException
import com.trudeals.ui.base.basedialogfragment.BaseDialogComponent
import com.trudeals.ui.base.basedialogfragment.BaseDialogFragment
import com.trudeals.ui.isolated.dummydata.SelectReasonToCancel
import com.trudeals.ui.isolated.optionsbottomsheet.OptionsBottomSheet
import com.trudeals.utils.DataUtils
import com.trudeals.utils.extension.setIcon
import com.trudeals.utils.extension.trimmedText


class ReasonToCancelDialog : BaseDialogFragment<ReasonToCancelDialogBinding>(),
    View.OnClickListener {

    private var onPositiveButtonClick: ((reason: String) -> Unit)? = null

    override fun inject(baseDialogComponent: BaseDialogComponent) {
        baseDialogComponent.inject(this)
    }

    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        attachToRoot: Boolean
    ): ReasonToCancelDialogBinding {
        return ReasonToCancelDialogBinding.inflate(layoutInflater)
    }

    override fun bindData() {
        init()
        clickListener()
    }

    private fun init() = with(binding) {
        textInputEditTextSelectReason.setIcon(0)
    }

    private fun clickListener() = with(binding) {
        textInputEditTextSelectReason.setOnClickListener(this@ReasonToCancelDialog)
        buttonYes.setOnClickListener(this@ReasonToCancelDialog)
        buttonNo.setOnClickListener(this@ReasonToCancelDialog)
    }

    override fun onClick(v: View): Unit = with(binding) {
        when (v) {
            textInputEditTextSelectReason -> {
                OptionsBottomSheet<SelectReasonToCancel>().setTitle("Select Reason")
                    .setOptionsList(DataUtils.selectReasonToCancel())
                    .setOnPositiveButtonClickListener { selectedOption ->
                        textInputEditTextSelectReason.setText(selectedOption.option)
                    }.setSelectedOption(textInputEditTextSelectReason.trimmedText)
                    .show(childFragmentManager, "SELECT_REASON")
            }
            buttonYes -> {
                if (validation()) onPositiveButtonClick?.invoke(textInputEditTextSelectReason.trimmedText)
            }
            buttonNo -> {
                this@ReasonToCancelDialog.dismiss()
            }
        }
    }

    fun setOnPositiveButtonClick(onPositiveButtonClick: (reason: String) -> Unit) {
        this.onPositiveButtonClick = onPositiveButtonClick
    }

    private fun validation(): Boolean = with(binding) {
        return try {
            with(validator) {
                submit(textInputEditTextSelectReason).checkEmpty()
                    .errorMessage(getString(R.string.validation_select_reason))
                    .check()
                true
            }
        } catch (e: ApplicationException) {
            showMessage(e)
            false
        }
    }

}