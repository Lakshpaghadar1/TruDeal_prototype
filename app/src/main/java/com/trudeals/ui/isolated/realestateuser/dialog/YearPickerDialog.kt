package com.trudeals.ui.isolated.realestateuser.dialog

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.trudeals.databinding.YearPickerDialogBinding
import com.trudeals.ui.base.basedialogfragment.BaseDialogComponent
import com.trudeals.ui.base.basedialogfragment.BaseDialogFragment
import java.util.*


class YearPickerDialog : BaseDialogFragment<YearPickerDialogBinding>(), View.OnClickListener {

    private var selectedYear: Int? = Calendar.getInstance().get(Calendar.YEAR)

    private var onClickOfSelect: ((selectedYear: Int) -> Unit)? = null

    override fun inject(baseDialogComponent: BaseDialogComponent) {
        baseDialogComponent.inject(this)
    }

    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        attachToRoot: Boolean
    ): YearPickerDialogBinding {
        return YearPickerDialogBinding.inflate(layoutInflater)
    }

    override fun bindData() {
        init()
        clickListener()
    }

    private fun init() = with(binding) {
        // Set up year picker
        yearPicker.apply {
            minValue = 1975
            maxValue = 2025
            value = selectedYear!!
        }
    }

    private fun clickListener() = with(binding) {
        buttonSelect.setOnClickListener(this@YearPickerDialog)
        textViewCancel.setOnClickListener(this@YearPickerDialog)
    }

    override fun onClick(v: View): Unit = with(binding) {
        when (v) {
            buttonSelect -> {
                onClickOfSelect?.invoke(yearPicker.value)
            }
            textViewCancel -> {
                this@YearPickerDialog.dismiss()
            }
        }
    }

    fun setOnSelectClick(onClickOfSelect: (selectedYear: Int) -> Unit) {
        this.onClickOfSelect = onClickOfSelect
    }

    fun getSelectedTime(selectedYear: Int) {
        this.selectedYear = selectedYear
    }
}