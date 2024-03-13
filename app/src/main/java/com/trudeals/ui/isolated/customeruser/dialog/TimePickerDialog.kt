package com.trudeals.ui.isolated.customeruser.dialog

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.trudeals.databinding.TimePickerDialogBinding
import com.trudeals.ui.base.basedialogfragment.BaseDialogComponent
import com.trudeals.ui.base.basedialogfragment.BaseDialogFragment


class TimePickerDialog : BaseDialogFragment<TimePickerDialogBinding>(), View.OnClickListener {

    private var selectedHour: Int? = 0
    private var selectedMinute: Int? = 0
    private var onClickOfSelect: ((selectedHour: Int, selectedMinute: Int) -> Unit)? = null

    override fun inject(baseDialogComponent: BaseDialogComponent) {
        baseDialogComponent.inject(this)
    }

    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        attachToRoot: Boolean
    ): TimePickerDialogBinding {
        return TimePickerDialogBinding.inflate(layoutInflater)
    }

    override fun bindData() {
        init()
        clickListener()
    }

    private fun init() = with(binding) {
        // Set up hour picker
        //hourPicker.setFormatter { i -> String.format("%02d", i) }
        hourPicker.apply {
            minValue = 0
            maxValue = 12
            value = selectedHour ?: minValue
        }

        // Set up minute picker
        //minutePicker.setFormatter { i -> String.format("%02d", i) }
        minutePicker.apply {
            minValue = 0
            maxValue = 59
            value = selectedMinute ?: minValue
        }
    }

    private fun clickListener() = with(binding) {
        buttonSelect.setOnClickListener(this@TimePickerDialog)
        textViewCancel.setOnClickListener(this@TimePickerDialog)
    }


    override fun onClick(v: View): Unit = with(binding) {
        when (v) {
            buttonSelect -> {
                onClickOfSelect?.invoke(hourPicker.value, minutePicker.value)
            }
            textViewCancel -> {
                this@TimePickerDialog.dismiss()
            }
        }
    }

    fun setOnSelectClick(onClickOfSelect: (selectedHour: Int, selectedMinute: Int) -> Unit) {
        this.onClickOfSelect = onClickOfSelect
    }

    fun getSelectedTime(selectedHour: Int, selectedMinute: Int) {
        this.selectedHour = selectedHour
        this.selectedMinute = selectedMinute
    }
}