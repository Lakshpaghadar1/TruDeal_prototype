package com.trudeals.ui.isolated.realestateuser.fragment

import android.app.Activity
import android.content.Intent
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.marcohc.robotocalendar.RobotoCalendarView
import com.trudeals.R
import com.trudeals.databinding.SelectRentDurationBinding
import com.trudeals.di.component.FragmentComponent
import com.trudeals.ui.base.BaseFragment
import com.trudeals.ui.common.isolated.IsolatedFullActivity
import com.trudeals.ui.isolated.dummydata.RealEstatePropertyList
import com.trudeals.ui.isolated.dummydata.SelectRentDurationType
import com.trudeals.ui.isolated.optionsbottomsheet.OptionsBottomSheet
import com.trudeals.utils.DataUtils
import com.trudeals.utils.OnClick
import com.trudeals.utils.PropertyListTag
import com.trudeals.utils.StepsCount
import com.trudeals.utils.extension.datePicker
import com.trudeals.utils.extension.isVisible
import com.trudeals.utils.extension.trimmedText
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*

class SelectRentDurationFragment : BaseFragment<SelectRentDurationBinding>(), View.OnClickListener,
    RobotoCalendarView.RobotoCalendarListener {

    private val currentTab by lazy { arguments?.getParcelable<PropertyListTag>(CURRENT_TAB) }

    //private val onClick by lazy { arguments?.getParcelable<OnClick>(ON_CLICK) }
    private val data by lazy { arguments?.getParcelable<RealEstatePropertyList>(DATA) }

    override fun inject(fragmentComponent: FragmentComponent) {
        fragmentComponent.inject(this)
    }

    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        attachToRoot: Boolean
    ): SelectRentDurationBinding {
        return SelectRentDurationBinding.inflate(layoutInflater)
    }

    override fun bindData() {
        clickListener()
        setVisibility()
    }

    override fun setUpToolbar() {}

    private fun setVisibility() = with(binding) {
        when (currentTab) {
            PropertyListTag.HOME_FOR_RENT -> {
                editTextSelectDuration.setText(getString(R.string.label_short_term))
                setView(isCustomCalVisible = false)
            }
            PropertyListTag.VACATION_RENTAL -> {
                editTextSelectDuration.setText(getString(R.string.label_vacation_rental))
                textViewSelectDuration.text = getString(R.string.label_select_available_duration)
                setView(isCustomCalVisible = true)
            }
            else -> {}
        }
    }

    private fun setView(isCustomCalVisible: Boolean) = with(binding) {
        cardViewStartDate.isVisible(!isCustomCalVisible)
        cardViewEndDate.isVisible(!isCustomCalVisible)
        customCal.isVisible(isCustomCalVisible)
        if (customCal.isVisible) {
            lifecycleScope.launch {
                showLoader()
                delay(1000)
                hideLoader()
                setCal()
            }
        }
    }

    private val dataAsResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val bundle = result.data?.extras
                bundle?.getParcelable<RealEstatePropertyList>("DATA").let { data ->
                    bundle?.getParcelable<PropertyListTag>("CURRENT_TAB").let { currentTab ->
                        bundle?.getBoolean("IS_EDIT").let { isEdit ->
                            if (isEdit == true) {
                                (parentFragment as AddOrEditPropertyDetailsMainFragment).navigateToPreviousStep(
                                    StepsCount.STEP_ONE
                                )
                            } else {
                                val intent = Intent()
                                intent.putExtra("DATA", data)
                                intent.putExtra("CURRENT_TAB", currentTab as Parcelable)
                                requireActivity().setResult(Activity.RESULT_OK, intent)
                                navigator.goBack()
                            }
                        }
                    }
                }
            }
        }

    private fun clickListener() = with(binding) {
        editTextSelectDuration.setOnClickListener(this@SelectRentDurationFragment)
        textViewStartDateIs.setOnClickListener(this@SelectRentDurationFragment)
        textViewEndDateIs.setOnClickListener(this@SelectRentDurationFragment)
        buttonPreview.setOnClickListener(this@SelectRentDurationFragment)
        buttonSave.setOnClickListener(this@SelectRentDurationFragment)
    }

    override fun onClick(v: View) = with(binding) {
        when (v) {
            editTextSelectDuration -> {
                when (currentTab) {
                    PropertyListTag.HOME_FOR_RENT -> {
                        hideKeyBoard()
                        OptionsBottomSheet<SelectRentDurationType>().setTitle("Select Rent Duration")
                            .setOptionsList(DataUtils.rentDurationTypeOptions())
                            .setOnPositiveButtonClickListener { selectedOption ->
                                editTextSelectDuration.setText(selectedOption.option)
                            }.setSelectedOption(editTextSelectDuration.trimmedText)
                            .show(childFragmentManager, "SELECT_RENT_DURATION")
                    }
                    PropertyListTag.VACATION_RENTAL -> {
                        hideKeyBoard()
                        OptionsBottomSheet<SelectRentDurationType>().setTitle("Select Rent Duration")
                            .setOptionsList(DataUtils.vacationRentDurationTypeOptions())
                            .setOnPositiveButtonClickListener { selectedOption ->
                                editTextSelectDuration.setText(selectedOption.option)
                            }.setSelectedOption(editTextSelectDuration.trimmedText)
                            .show(childFragmentManager, "SELECT_RENT_DURATION")
                    }
                    else -> {}
                }
            }
            textViewStartDateIs -> {
                textViewStartDateIs.datePicker(textViewStartDateIs)
            }
            textViewEndDateIs -> {
                textViewEndDateIs.datePicker(textViewEndDateIs)
            }
            buttonPreview -> {
                navigator.loadActivity(
                    IsolatedFullActivity::class.java,
                    PreviewOrSavePropertyDetailFragment::class.java
                ).onResultActivity(dataAsResult).addBundle(
                    PreviewOrSavePropertyDetailFragment.createBundle(
                        SelectRentDurationFragment::class.java.simpleName,
                        data ?: RealEstatePropertyList(),//set data
                        currentTab
                    )
                ).start()
            }
            buttonSave -> {
                val intent = Intent()
                intent.putExtra("DATA", data ?: RealEstatePropertyList())//set data
                intent.putExtra("CURRENT_TAB", currentTab as Parcelable)
                requireActivity().setResult(Activity.RESULT_OK, intent)
                navigator.goBack()
            }
        }
    }

    private fun setCal() = with(binding) {
        customCal.setRobotoCalendarListener(this@SelectRentDurationFragment)
        customCal.setShortWeekDays(true)
    }

    override fun onDayClick(date: Date?) {}

    override fun onDayLongClick(date: Date?) {}

    override fun onRightButtonClick(date: Date?) {}

    override fun onLeftButtonClick(date: Date?) {}

    companion object {
        private const val ON_CLICK = "ON_CLICK"
        private const val CURRENT_TAB = "CURRENT_TAB"
        private const val DATA = "DATA"

        fun createBundle(
            onClick: OnClick?,
            currentTab: PropertyListTag?,
            data: RealEstatePropertyList
        ) =
            bundleOf(ON_CLICK to onClick, CURRENT_TAB to currentTab, DATA to data)
    }
}