package com.trudeals.ui.isolated.realestateuser.fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.marcohc.robotocalendar.RobotoCalendarView
import com.trudeals.R
import com.trudeals.databinding.ScheduleAnOpenHouseTimingFragmentBinding
import com.trudeals.di.component.FragmentComponent
import com.trudeals.ui.base.BaseFragment
import com.trudeals.ui.common.isolated.IsolatedFullActivity
import com.trudeals.ui.isolated.customeruser.bottomsheet.SelectDateAndTimeBottomsheet
import com.trudeals.ui.isolated.customeruser.dialog.DialogAppointmentReqSuccess
import com.trudeals.ui.isolated.customeruser.fragment.RequestDetailFragment
import com.trudeals.ui.isolated.realestateuser.adapter.OpenHousePropertyListAdapter
import com.trudeals.ui.isolated.realestateuser.adapter.ScheduleOpenHouseTimingAdapter
import com.trudeals.utils.DataUtils
import java.util.*

class ScheduleOpenHouseTimingFragment : BaseFragment<ScheduleAnOpenHouseTimingFragmentBinding>(),
    RobotoCalendarView.RobotoCalendarListener {

    //private val scheduleOpenHouseTimingAdapter by lazy { ScheduleOpenHouseTimingAdapter() }
    private val openHousePropertyListAdapter by lazy { OpenHousePropertyListAdapter() }
    private val dialogAppointmentReqSuccess by lazy { DialogAppointmentReqSuccess() }
    private val selectDateAndTimeDialog by lazy { SelectDateAndTimeBottomsheet() }

    override fun inject(fragmentComponent: FragmentComponent) {
        fragmentComponent.inject(this)
    }

    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        attachToRoot: Boolean
    ): ScheduleAnOpenHouseTimingFragmentBinding {
        return ScheduleAnOpenHouseTimingFragmentBinding.inflate(layoutInflater)
    }

    override fun bindData() {
        /*lifecycleScope.launch {
            showLoader()
            delay(1000)
            hideLoader()
            setCal()
        }*/
        //setTimingSlotRecyclerView()
        //clickListener()
        clickOnItem()
        clickOnViewOfItem()
        setListingRecyclerView()
    }

    override fun setUpToolbar() = with(toolbar) {
        showToolbar(true)
            .showBackButton(true)
            .setToolbarTitle(getString(R.string.label_schedule))
            .setToolbarElevation(R.dimen._1sdp)
            .build()
    }

    /*private fun setCal() = with(binding) {
        customCal.setRobotoCalendarListener(this@ScheduleOpenHouseTimingFragment)
        customCal.setShortWeekDays(true)
        //val dateStrings = arrayOf("29 Apr 2023", "30 Apr 2023")
        //customCal.parseDates(dateStrings)
    }*/

    override fun onDayClick(date: Date?) {}

    override fun onDayLongClick(date: Date?) {}

    override fun onRightButtonClick(date: Date?) {}

    override fun onLeftButtonClick(date: Date?) {}


    /*private fun setTimingSlotRecyclerView() = with(binding.recyclerViewTiming) {
        scheduleOpenHouseTimingAdapter.setItems(DataUtils.timeSlotForOpenHouse(), 1)
        layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
        adapter = scheduleOpenHouseTimingAdapter
    }*/

    /*private fun clickListener() {
        scheduleOpenHouseTimingAdapter.setOnItemClickPositionListener { item, position ->
            scheduleOpenHouseTimingAdapter.changeSelection(position, true)
            when (item.timeSlot) {
                "10:30 AM - 11:30 AM" -> {
                    openHousePropertyListAdapter.setItems(DataUtils.openHousePropertyListOne(), 1)
                }
                "12:00 PM - 12:30 PM" -> {
                    openHousePropertyListAdapter.setItems(DataUtils.openHousePropertyListTwo(), 1)
                }
                "11:30 AM - 12:30 AM" -> {
                    openHousePropertyListAdapter.setItems(DataUtils.openHousePropertyListThree(), 1)
                }
                "01:30 AM - 02:00 AM" -> {
                    openHousePropertyListAdapter.setItems(DataUtils.openHousePropertyListOne(), 1)
                }
                "02:30 AM - 05:30 AM" -> {
                    openHousePropertyListAdapter.setItems(DataUtils.openHousePropertyListTwo(), 1)
                }
            }
        }
    }*/

    private fun setListingRecyclerView() = with(binding.recyclerViewRequestList) {
        openHousePropertyListAdapter.setItems(DataUtils.openHousePropertyListOne(), 1)
        layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        adapter = openHousePropertyListAdapter
    }

    private fun clickOnItem() {
        openHousePropertyListAdapter.setOnItemClickPositionListener { item, _ ->
            navigator.loadActivity(
                IsolatedFullActivity::class.java,
                RequestDetailFragment::class.java
            ).addBundle(
                RequestDetailFragment.createScheduleBundle(
                    null,
                    item.isScheduled,
                    ScheduleOpenHouseTimingFragment::class.java.simpleName
                )
            ).start()
        }
    }

    private fun clickOnViewOfItem() {
        openHousePropertyListAdapter.setOnClickOfItemView { _, _, _ ->
            selectDateAndTimeDialog.show(
                childFragmentManager,
                SelectDateAndTimeBottomsheet::class.java.simpleName
            )
            callBackFromSelectDateAndTimeBottomsheet()
        }
    }

    private fun callBackFromSelectDateAndTimeBottomsheet() {
        selectDateAndTimeDialog.setOnNextClick {
            selectDateAndTimeDialog.dismiss()
            dialogAppointmentReqSuccess.show(
                childFragmentManager,
                DialogAppointmentReqSuccess::class.java.simpleName
            )
        }
    }
}