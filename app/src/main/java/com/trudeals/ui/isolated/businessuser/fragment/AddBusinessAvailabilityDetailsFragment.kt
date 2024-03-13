package com.trudeals.ui.isolated.businessuser.fragment

import android.app.Activity
import android.app.TimePickerDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.trudeals.R
import com.trudeals.databinding.AddBusinessAvailabilityDetailsFragmentBinding
import com.trudeals.di.component.FragmentComponent
import com.trudeals.ui.base.BaseFragment
import com.trudeals.ui.common.isolated.IsolatedFullActivity
import com.trudeals.ui.home.businessuser.HomeActivityBU
import com.trudeals.ui.home.businessuser.fragment.HomeFragmentBU
import com.trudeals.ui.home.dummydata.BusinessHomeListItem
import com.trudeals.ui.isolated.businessuser.adapter.SelectBusinessTimeSlotsAdapter
import com.trudeals.ui.isolated.dummydata.BusinessDeal
import com.trudeals.ui.isolated.dummydata.BusinessProfileDetailMain
import com.trudeals.ui.isolated.dummydata.SelectBusinessTimeSlots
import com.trudeals.utils.*
import java.util.*

class AddBusinessAvailabilityDetailsFragment :
    BaseFragment<AddBusinessAvailabilityDetailsFragmentBinding>(), View.OnClickListener {

    private val selectBusinessTimeSlotsAdapter by lazy { SelectBusinessTimeSlotsAdapter() }
    private val previousScreenData by lazy {
        arguments?.getParcelable<BusinessProfileDetailMain>(DATA)
    }
    private val sourceScreen by lazy { arguments?.getString(SOURCE_SCREEN) }


    override fun inject(fragmentComponent: FragmentComponent) {
        fragmentComponent.inject(this)
    }

    override fun createViewBinding(
        inflater: LayoutInflater, container: ViewGroup?, attachToRoot: Boolean
    ): AddBusinessAvailabilityDetailsFragmentBinding {
        return AddBusinessAvailabilityDetailsFragmentBinding.inflate(layoutInflater)
    }

    override fun bindData() {
        setData()
        setRecyclerView()
        clickListener()
    }

    override fun setUpToolbar() {}

    private fun setData() {
        when (sourceScreen) {
            HomeFragmentBU::class.java.simpleName, HomeActivityBU::class.java.simpleName -> {
                setDefaultData()
            }
        }
    }

    private fun setDefaultData() {
        //set at api time
    }

    val result =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                result.data?.extras?.getBoolean("IS_EDIT").let { isEdit ->
                    if (isEdit == true) {
                        (parentFragment as CreateBusinessProfileMainFragment).navigateToPreviousStep(
                            StepsCount.STEP_ONE
                        )
                    }
                }
            }
        }

    private fun setRecyclerView() = with(binding.recyclerViewSelectTimeSlots) {
        selectBusinessTimeSlotsAdapter.setItems(DataUtils.selectBusinessTimeSlots(), 1)
        layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        adapter = selectBusinessTimeSlotsAdapter
    }

    private fun clickListener() = with(binding) {
        buttonPreview.setOnClickListener(this@AddBusinessAvailabilityDetailsFragment)
        buttonSave.setOnClickListener(this@AddBusinessAvailabilityDetailsFragment)
        selectBusinessTimeSlotsAdapter.setOnClickOfView { _, subPosition, position, onClick ->
            when (onClick) {
                OnClick.CANCEL -> {
                    selectBusinessTimeSlotsAdapter.removeChildItem(position, subPosition)
                }
                OnClick.START_TIME -> {
                    showStartTimePickerDialog(position, subPosition)
                }
                OnClick.END_TIME -> {
                    checkOnClickOfEndTime(position, subPosition)
                }
                OnClick.ADD_SLOT -> {
                    addChildItem(position)
                }
                else -> {}
            }
        }
    }

    private fun showStartTimePickerDialog(parentItemPosition: Int, childItemPosition: Int) {
        val currentTime = Calendar.getInstance()
        val hour = currentTime.get(Calendar.HOUR_OF_DAY)
        val minute = currentTime.get(Calendar.MINUTE)

        TimePickerDialog(requireContext(), { _, selectedHour, selectedMinute ->
            selectBusinessTimeSlotsAdapter.updateChildIStartItem(
                parentItemPosition,
                childItemPosition,
                startTime = getTimeString(selectedHour, selectedMinute)
            )
        }, hour, minute, false).show()
    }

    //check on click of end time
    private fun checkOnClickOfEndTime(parentPosition: Int, childItemPosition: Int) {
        val parentItem = selectBusinessTimeSlotsAdapter.getItem(parentPosition)

        if (parentItem.listOfTimeSlots.isNotEmpty()) {
            val lastChildItem = parentItem.listOfTimeSlots.last()
            val isInvalidStartTime =
                isInvalidStartTime(lastChildItem.startTime!!) //start time is 00

            if (!isInvalidStartTime) {
                showEndTimePickerDialog(
                    parentPosition,
                    childItemPosition
                )  //if start time is not 00 then open time picker dialog to select end time
            } else {
                showMessage("Please select start time")  //if start time is 00 that means end time click was before selecting start time
            }
        }
    }

    private fun showEndTimePickerDialog(parentItemPosition: Int, childItemPosition: Int) {
        val currentTime = Calendar.getInstance()
        val hour = currentTime.get(Calendar.HOUR_OF_DAY)
        val minute = currentTime.get(Calendar.MINUTE)

        TimePickerDialog(requireContext(), { _, selectedHour, selectedMinute ->
            val selectedEndTime = getTimeString(selectedHour, selectedMinute)
            updateChildItemEndTime(parentItemPosition, childItemPosition, selectedEndTime)
        }, hour, minute, false).show()
    }

    private fun updateChildItemEndTime(
        parentItemPosition: Int,
        childItemPosition: Int,
        selectedEndTime: String
    ) {
        val childItem =
            selectBusinessTimeSlotsAdapter.getItem(parentItemPosition).listOfTimeSlots.last()
        val startTime = childItem.startTime?.let { timeFormat.parse(it) }
        val endTime = timeFormat.parse(selectedEndTime)

        if (startTime != null && endTime != null) {
            when {
                endTime.before(startTime) -> showMessage("End time should be greater than start time")
                endTime == startTime -> showMessage("End time and start time cannot be the same")
                else -> selectBusinessTimeSlotsAdapter.updateChildIEndItem(
                    parentItemPosition, childItemPosition, endTime = selectedEndTime
                )
            }
        }
    }

    private fun isInvalidStartTime(startTime: String): Boolean {
        return startTime == getString(R.string.dummy_00_00_am) || startTime == getString(R.string.dummy_00_00_pm)
    }

    private fun isInvalidEndTime(endTime: String): Boolean {
        return endTime == getString(R.string.dummy_00_00_am) || endTime == getString(R.string.dummy_00_00_pm)
    }

    private fun addChildItem(position: Int) {
        val parentItem = selectBusinessTimeSlotsAdapter.getItem(position)

        if (parentItem.listOfTimeSlots.isNotEmpty()) {
            val lastChildItem = parentItem.listOfTimeSlots.last()
            val isInvalidStartTime = isInvalidStartTime(lastChildItem.startTime!!)
            val isInvalidEndTime = isInvalidEndTime(lastChildItem.endTime!!)

            if (!isInvalidStartTime && !isInvalidEndTime) {
                selectBusinessTimeSlotsAdapter.addChildItem(position)  //if start and end time is selected already then add new slot
            } else {
                showMessage("Please select start time")  //if start time and end time is 00
            }
        }
    }

    private fun checkAndShowMessage(): Boolean {
        val checkedItems = selectBusinessTimeSlotsAdapter.items?.filter { it.isChecked }

        if (checkedItems.isNullOrEmpty()) {
            showMessage("Please select one day for availability")
            return false
        }

        checkedItems.forEach { item ->
            if (item.listOfTimeSlots.isNotEmpty()) {
                val lastChildItem = item.listOfTimeSlots.last()
                val isInvalidStartTime = isInvalidStartTime(lastChildItem.startTime!!)
                val isInvalidEndTime = isInvalidEndTime(lastChildItem.endTime!!)

                when {
                    isInvalidStartTime -> {
                        showMessage("Please enter start time")
                        return false
                    }
                    isInvalidEndTime -> {
                        showMessage("Please enter end time")
                        return false
                    }
                }
            }
        }
        return true
    }

    override fun onClick(v: View) = with(binding) {
        when (v) {
            buttonPreview -> {
                if (checkAndShowMessage()) {
                    navigator.loadActivity(
                        IsolatedFullActivity::class.java, BusinessDealDetailFragment::class.java
                    ).onResultActivity(result).addBundle(
                        BusinessDealDetailFragment.createBundle(
                            BusinessHomeListItem(), //temporary managed 2 data class that is why - passing 2 data class here - will manage at API time
                            BusinessDeal(),
                            AddBusinessAvailabilityDetailsFragment::class.java.simpleName
                        )
                    ).start()
                }
            }
            buttonSave -> {
                if (checkAndShowMessage()) {
                    session.isBUSubscribed = true
                    defaultUserSelection()
                    session.isBusinessOwnerUser = true
                    navigator.loadActivity(HomeActivityBU::class.java).start()
                }
            }
        }
    }

    private fun defaultUserSelection() {
        session.isGuestUser = false
        session.isCustomerUser = false
        session.isRealEstateUser = false
        session.isBusinessOwnerUser = false
    }

    private fun getData(): BusinessProfileDetailMain = with(binding) {
        val data = BusinessProfileDetailMain()
        data.businessProfileDetail = previousScreenData?.businessProfileDetail
        data.businessMediaDetail = previousScreenData?.businessMediaDetail
        data.businessMediaDetail = previousScreenData?.businessMediaDetail
        data.apply {
            listOfAvailability =
                selectBusinessTimeSlotsAdapter.items as ArrayList<SelectBusinessTimeSlots>
        }
    }

    /*  private fun onEndTimeSelected(parentItemPosition: Int, selectedTime: String) {
          val childItem = selectBusinessTimeSlotsAdapter.getItem(parentItemPosition).listOfTimeSlots.last()
          val startTime = childItem.startTime?.let { timeFormat.parse(it) }
          val endTime = timeFormat.parse(selectedTime)

          if (startTime != null && endTime != null)
              if (endTime.before(startTime)) {
                  showMessage("End time should be greater than start time")
              } else if (endTime.equals(startTime)) {
                  showMessage("End time and start time cannot be same")
              } else {
                  showEndTimePickerDialog(parentItemPosition, selectedTime, OnClick.END_TIME)
              }
      }*/

    companion object {
        private const val DATA = "DATA"
        private const val SOURCE_SCREEN = "SOURCE_SCREEN"

        fun createBundle(data: BusinessProfileDetailMain) = bundleOf(DATA to data)

        fun createBundleSS(sourceString: String) = bundleOf(SOURCE_SCREEN to sourceString)
    }
}