package com.trudeals.ui.isolated.customeruser.bottomsheet

import android.view.LayoutInflater
import android.view.View
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.lifecycleScope
import com.marcohc.robotocalendar.RobotoCalendarView.RobotoCalendarListener
import com.trudeals.R
import com.trudeals.databinding.SelectDateAndTimeBottomsheetBinding
import com.trudeals.di.component.BottomSheetComponent
import com.trudeals.ui.base.BaseBottomSheet
import com.trudeals.ui.isolated.customeruser.dialog.TimePickerDialog
import com.trudeals.ui.isolated.realestateuser.fragment.BookingRequestFragment
import com.trudeals.utils.AmPm
import com.trudeals.utils.TimeType
import com.trudeals.utils.extension.trimmedText
import com.trudeals.utils.textdecorator.TextDecorator
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class SelectDateAndTimeBottomsheet : BaseBottomSheet<SelectDateAndTimeBottomsheetBinding>(),
    View.OnClickListener, RobotoCalendarListener {

    var sourceScreen = "SOURCE_SCREEN"
    private var selectedHr: Int = 0
    private var selectedMin: Int = 0
    private val timePickerDialog by lazy { TimePickerDialog() }
    private var onNextClick: (() -> Unit)? = null

    override fun createViewBinding(inflater: LayoutInflater): SelectDateAndTimeBottomsheetBinding {
        return SelectDateAndTimeBottomsheetBinding.inflate(inflater)
    }

    override fun inject(bottomSheetComponent: BottomSheetComponent) {
        bottomSheetComponent.inject(this)
    }

    override fun bindData() {
        init()
        clickListeners()
    }

    override fun destroyViewBinding() {}

    private fun init() {
        setTitleAndButton()
        setDefaultTime()

        lifecycleScope.launch {
            showLoader()
            delay(1000)
            hideLoader()
            setCal()
        }
    }

    private fun setTitleAndButton() = with(binding) {
        when (sourceScreen) {
            /*RealEstateDetailFragmentREU::class.java.simpleName,*/ BookingRequestFragment::class.java.simpleName -> {
            textViewSelectTitle.text = getString(R.string.label_select_open_house_timing)
            buttonNext.text = getString(R.string.btn_schedule)
        }
            else -> {
                textViewSelectTitle.text = getString(R.string.label_select_a_showing_date_and_time)
                buttonNext.text = getString(R.string.btn_next)
            }
        }
    }

    private fun clickListeners() = with(binding) {
        buttonNext.setOnClickListener(this@SelectDateAndTimeBottomsheet)
        textViewCancel.setOnClickListener(this@SelectDateAndTimeBottomsheet)
        textViewStartTimeAM.setOnClickListener(this@SelectDateAndTimeBottomsheet)
        textViewStartTimePM.setOnClickListener(this@SelectDateAndTimeBottomsheet)
        textViewStartTimeHour.setOnClickListener(this@SelectDateAndTimeBottomsheet)
        textViewStartTimeMinute.setOnClickListener(this@SelectDateAndTimeBottomsheet)
        textViewEndTimeAM.setOnClickListener(this@SelectDateAndTimeBottomsheet)
        textViewEndTimePM.setOnClickListener(this@SelectDateAndTimeBottomsheet)
        textViewEndTimeHour.setOnClickListener(this@SelectDateAndTimeBottomsheet)
        textViewEndTimeMinute.setOnClickListener(this@SelectDateAndTimeBottomsheet)
    }

    override fun onClick(v: View): Unit = with(binding) {
        when (v) {
            buttonNext -> {
                if (validateEndTime()) {
                    onNextClick?.invoke()
                }
            }
            textViewCancel -> {
                this@SelectDateAndTimeBottomsheet.dismiss()
            }
            textViewStartTimeAM, textViewStartTimePM -> {
                textViewStartTimeAM.isSelected = v == textViewStartTimeAM
                textViewStartTimePM.isSelected = v == textViewStartTimePM
                validateEndTime()
            }
            textViewStartTimeHour, textViewStartTimeMinute -> {
                openTimerDialog(TimeType.START_TIME)
            }
            textViewEndTimeAM, textViewEndTimePM -> {
                textViewEndTimeAM.isSelected = v == textViewEndTimeAM
                textViewEndTimePM.isSelected = v == textViewEndTimePM
                validateEndTime()
            }
            textViewEndTimeHour, textViewEndTimeMinute -> {
                openTimerDialog(TimeType.END_TIME)
            }
            else -> {}
        }
    }


    private fun openTimerDialog(timeType: TimeType) = with(binding) {
        timePickerDialog.show(childFragmentManager, TimePickerDialog::class.java.simpleName)
        timePickerDialog.getSelectedTime(selectedHr, selectedMin)
        callbackFromTimerDialog(timeType)
    }

    private fun callbackFromTimerDialog(timeType: TimeType) = with(binding) {
        timePickerDialog.setOnSelectClick { selectedHour, selectedMinute ->
            selectedHr = selectedHour
            selectedMin = selectedMinute
            timePickerDialog.dismiss()
            when (timeType) {
                TimeType.START_TIME -> {
                    textViewStartTimeHour.text =
                        getString(R.string.label_x_hour, String.format("%02d", selectedHour))
                    textViewStartTimeMinute.text =
                        getString(
                            R.string.label_x_minute,
                            String.format("%02d", selectedMinute)
                        )
                    textViewEndTimeHour.text =
                        getString(R.string.label_x_hour, String.format("%02d", 0))
                    textViewEndTimeMinute.text =
                        getString(
                            R.string.label_x_minute,
                            String.format("%02d", 0)
                        )
                }
                TimeType.END_TIME -> {
                    textViewEndTimeHour.text =
                        getString(R.string.label_x_hour, String.format("%02d", selectedHour))
                    textViewEndTimeMinute.text =
                        getString(
                            R.string.label_x_minute,
                            String.format("%02d", selectedMinute)
                        )
                    validateEndTime()
                }
            }
            setTypeface()
        }
    }

    private fun validateEndTime(): Boolean = with(binding) {
        // Get the selected start time
        val startHourText = textViewStartTimeHour.text.toString()
        val startMinuteText = textViewStartTimeMinute.text.toString()
        val startAmPm = if (textViewStartTimeAM.isSelected) AmPm.AM else AmPm.PM

        // Extract the numeric values from start time
        val startHour = extractNumericValue(startHourText).toString()
        val startMinute = extractNumericValue(startMinuteText).toString()

        // Get the selected end time
        val endHourText = textViewEndTimeHour.text.toString()
        val endMinuteText = textViewEndTimeMinute.text.toString()
        val endAmPm = if (textViewEndTimeAM.isSelected) AmPm.AM else AmPm.PM

        // Extract the numeric values from end time
        val endHour = extractNumericValue(endHourText).toString()
        val endMinute = extractNumericValue(endMinuteText).toString()

        val formatter = SimpleDateFormat("hh:mm:aa", Locale.getDefault())
        val startTimeIn24 = formatter.parse("$startHour:$startMinute:$startAmPm")
        val endTimeIn24 = formatter.parse("$endHour:$endMinute:$endAmPm")

        if (startTimeIn24 != null && endTimeIn24 != null) {
            when {
                endTimeIn24.before(startTimeIn24) -> {
                    showMessage("End time should be greater than start time")
                    return@with false
                }
                endTimeIn24 == startTimeIn24 -> {
                    showMessage("End time and start time cannot be the same")
                    return@with false
                }
            }
        }
        return@with true
    }

    private fun extractNumericValue(text: String): Int {
        // Remove any non-numeric characters from the text and parse it as an integer
        return text.replace(Regex("[^0-9]"), "").toInt()
    }

    private fun setCurrentTime() = with(binding) {
        val currentTime = Calendar.getInstance().time
        val calendar = Calendar.getInstance()
        calendar.time = currentTime

        selectedHr = calendar.get(Calendar.HOUR)
        selectedMin = calendar.get(Calendar.MINUTE)

        textViewStartTimeHour.text =
            getString(R.string.label_x_hour, String.format("%02d", selectedHr))
        textViewStartTimeMinute.text =
            getString(R.string.label_x_minute, String.format("%02d", selectedMin))

        textViewEndTimeHour.text =
            getString(R.string.label_x_hour, String.format("%02d", selectedHr))
        textViewEndTimeMinute.text =
            getString(R.string.label_x_minute, String.format("%02d", selectedMin))

        setTypeface()

        val amPm = when (calendar.get(Calendar.AM_PM)) {
            Calendar.AM -> AmPm.AM
            else -> AmPm.PM
        }
        setCurrentAmPm(amPm)
    }

    private fun setDefaultTime() = with(binding) {
        val calendar = Calendar.getInstance()

        textViewStartTimeHour.text =
            getString(R.string.label_x_hour, String.format("%02d", selectedHr))
        textViewStartTimeMinute.text =
            getString(R.string.label_x_minute, String.format("%02d", selectedMin))

        textViewEndTimeHour.text =
            getString(R.string.label_x_hour, String.format("%02d", selectedHr))
        textViewEndTimeMinute.text =
            getString(R.string.label_x_minute, String.format("%02d", selectedMin))

        setTypeface()

        val amPm = when (calendar.get(Calendar.AM_PM)) {
            Calendar.AM -> AmPm.AM
            else -> AmPm.PM
        }
        setCurrentAmPm(amPm)
    }

    private fun setCurrentAmPm(amPm: AmPm) = with(binding) {
        when (amPm) {
            AmPm.AM -> {
                textViewStartTimeAM.isSelected = true
                textViewStartTimePM.isSelected = false
                textViewEndTimeAM.isSelected = true
                textViewEndTimePM.isSelected = false
            }
            AmPm.PM -> {
                textViewStartTimeAM.isSelected = false
                textViewStartTimePM.isSelected = true
                textViewEndTimeAM.isSelected = false
                textViewEndTimePM.isSelected = true
            }
        }
    }

    private fun setCal() = with(binding) {
        customCal.setRobotoCalendarListener(this@SelectDateAndTimeBottomsheet)
        customCal.setShortWeekDays(true)
    }

    override fun onDayClick(date: Date?) {}

    override fun onDayLongClick(date: Date?) {}

    override fun onRightButtonClick(date: Date?) {}

    override fun onLeftButtonClick(date: Date?) {}

    fun setOnNextClick(onNextClick: () -> Unit) {
        this.onNextClick = onNextClick
    }

    private fun setTypeface() = with(binding) {
        TextDecorator.decorate(textViewStartTimeHour, textViewStartTimeHour.trimmedText)
            .setTypeface(
                ResourcesCompat.getFont(
                    textViewStartTimeHour.context,
                    R.font.cerebri_sans_regular
                ), getString(R.string.label_hour)
            )
            .build()

        TextDecorator.decorate(textViewStartTimeMinute, textViewStartTimeMinute.trimmedText)
            .setTypeface(
                ResourcesCompat.getFont(
                    textViewStartTimeMinute.context,
                    R.font.cerebri_sans_regular
                ), getString(R.string.label_minute)
            )
            .build()

        TextDecorator.decorate(textViewEndTimeHour, textViewEndTimeHour.trimmedText)
            .setTypeface(
                ResourcesCompat.getFont(
                    textViewEndTimeHour.context,
                    R.font.cerebri_sans_regular
                ), getString(R.string.label_hour)
            )
            .build()

        TextDecorator.decorate(textViewEndTimeMinute, textViewEndTimeMinute.trimmedText)
            .setTypeface(
                ResourcesCompat.getFont(
                    textViewEndTimeMinute.context,
                    R.font.cerebri_sans_regular
                ), getString(R.string.label_minute)
            )
            .build()
    }
}