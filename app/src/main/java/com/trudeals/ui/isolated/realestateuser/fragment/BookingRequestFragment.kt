package com.trudeals.ui.isolated.realestateuser.fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.marcohc.robotocalendar.RobotoCalendarView
import com.trudeals.R
import com.trudeals.databinding.BookingRequestFragmentBinding
import com.trudeals.di.component.FragmentComponent
import com.trudeals.ui.base.BaseFragment
import com.trudeals.ui.common.isolated.IsolatedFullActivity
import com.trudeals.ui.isolated.customeruser.bottomsheet.AddYourDetailsBottomsheet
import com.trudeals.ui.isolated.customeruser.bottomsheet.SelectDateAndTimeBottomsheet
import com.trudeals.ui.isolated.customeruser.dialog.DialogAppointmentReqSuccess
import com.trudeals.ui.isolated.customeruser.fragment.ChatFragment
import com.trudeals.ui.isolated.customeruser.fragment.RequestDetailFragment
import com.trudeals.ui.isolated.realestateuser.adapter.BookingRequestListAdapter
import com.trudeals.ui.isolated.realestateuser.dialog.ReasonToCancelDialog
import com.trudeals.utils.DataUtils
import com.trudeals.utils.OnClick
import com.trudeals.utils.RequestCategoryType
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*

class BookingRequestFragment : BaseFragment<BookingRequestFragmentBinding>(), View.OnClickListener,
    RobotoCalendarView.RobotoCalendarListener {

    private val reasonToCancelDialog by lazy { ReasonToCancelDialog() }
    private var selectedCategoryType: RequestCategoryType = RequestCategoryType.REQUESTED
    private val requestListAdapter by lazy { BookingRequestListAdapter() }
    private val addYourDetailDialog by lazy { AddYourDetailsBottomsheet() }
    private val dialogAppointmentReqSuccess by lazy { DialogAppointmentReqSuccess() }
    private val selectDateAndTimeDialog by lazy { SelectDateAndTimeBottomsheet() }

    override fun inject(fragmentComponent: FragmentComponent) {
        fragmentComponent.inject(this)
    }

    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        attachToRoot: Boolean
    ): BookingRequestFragmentBinding {
        return BookingRequestFragmentBinding.inflate(layoutInflater)
    }

    override fun bindData() {
        lifecycleScope.launch {
            showLoader()
            delay(1000)
            hideLoader()
            setCal()
        }
        setRequestedAsDefaultSelection()
        setRecyclerView()
        clickListener()
    }

    override fun setUpToolbar() = with(toolbar) {
        showToolbar(true)
            .showBackButton(true)
            .setToolbarTitle(getString(R.string.label_request))
            .setToolbarElevation(R.dimen._1sdp)
            .build()
    }

    private fun setCal() = with(binding) {
        customCal.setRobotoCalendarListener(this@BookingRequestFragment)
        customCal.setShortWeekDays(true)
        customCal.setSelectedDates(DataUtils.listOfDates())
    }

    override fun onDayClick(date: Date?) {}

    override fun onDayLongClick(date: Date?) {}

    override fun onRightButtonClick(date: Date?) {
        //when next - need to pass list of date from API (need to pass year and month)
    }

    override fun onLeftButtonClick(date: Date?) {
        //when next - need to pass list of date from API (need to pass year and month)
    }

    private fun clickListener() = with(binding) {
        buttonRequested.setOnClickListener(this@BookingRequestFragment)
        buttonAccepted.setOnClickListener(this@BookingRequestFragment)
        buttonCompleted.setOnClickListener(this@BookingRequestFragment)

        clickOnItem()
        clickOnViewOfItem()
        callBackFromReasonToCancelDialog()
    }

    private fun clickOnItem() {
        requestListAdapter.setOnItemClickPositionListener { _, _ ->
            navigator.loadActivity(
                IsolatedFullActivity::class.java,
                RequestDetailFragment::class.java
            ).addBundle(
                RequestDetailFragment.createBundle(
                    null,
                    selectedCategoryType,
                    BookingRequestFragment::class.java.simpleName
                )
            ).start()
        }
    }

    private fun clickOnViewOfItem() {
        requestListAdapter.setOnClickOfItemView { item, _, onClick ->
            when (onClick) {
                OnClick.MODIFY_REQUEST -> {
                    selectDateAndTimeDialog.show(
                        childFragmentManager,
                        SelectDateAndTimeBottomsheet::class.java.simpleName
                    )
                    callBackFromSelectDateAndTimeBottomsheet()
                }
                OnClick.CANCEL -> {
                    reasonToCancelDialog.show(
                        childFragmentManager,
                        ReasonToCancelDialog::class.java.simpleName
                    )
                }
                OnClick.ACCEPT -> {
                    showMessage("ACCEPTED")
                }
                OnClick.CHAT -> {
                    navigator.loadActivity(
                        IsolatedFullActivity::class.java,
                        ChatFragment::class.java
                    ).addBundle(ChatFragment.createBundle(item.userName)).start()
                }
                else -> {}
            }
        }
    }

    private fun callBackFromSelectDateAndTimeBottomsheet() {
        selectDateAndTimeDialog.setOnNextClick {
            selectDateAndTimeDialog.dismiss()
            addYourDetailDialog.show(
                childFragmentManager,
                AddYourDetailsBottomsheet::class.java.simpleName
            )
        }
        callBackFromAddYourDetailsBottomsheet()
    }

    private fun callBackFromAddYourDetailsBottomsheet() {
        addYourDetailDialog.setOnRequestShowingClick {
            addYourDetailDialog.dismiss()
            dialogAppointmentReqSuccess.show(
                childFragmentManager,
                DialogAppointmentReqSuccess::class.java.simpleName
            )
        }
    }

    private fun callBackFromReasonToCancelDialog() {
        reasonToCancelDialog.setOnPositiveButtonClick {
            reasonToCancelDialog.dismiss()
            navigator.goBack()
            showMessage(it)
        }
    }

    override fun onClick(v: View): Unit = with(binding) {
        when (v) {
            buttonRequested -> {
                setSelection(RequestCategoryType.REQUESTED)
            }
            buttonAccepted -> {
                setSelection(RequestCategoryType.ACCEPTED)
            }
            buttonCompleted -> {
                setSelection(RequestCategoryType.COMPLETED)
            }
        }
    }

    private fun setSelection(selection: RequestCategoryType) {
        when (selection) {
            RequestCategoryType.REQUESTED -> {
                setRequestedAsDefaultSelection()
            }
            RequestCategoryType.ACCEPTED -> {
                setAcceptedSelection()
            }
            RequestCategoryType.COMPLETED -> {
                setCompletedSelection()
            }
        }
    }

    private fun setRequestedAsDefaultSelection() = with(binding) {
        setDefaultSelection()
        buttonRequested.isSelected = true
        selectedCategoryType = RequestCategoryType.REQUESTED
        requestListAdapter.getSelectedRequestType(selectedCategoryType)
        requestListAdapter.setItems(DataUtils.requestedList(), 1)
    }

    private fun setAcceptedSelection() = with(binding) {
        setDefaultSelection()
        buttonAccepted.isSelected = true
        selectedCategoryType = RequestCategoryType.ACCEPTED
        requestListAdapter.getSelectedRequestType(selectedCategoryType)
        requestListAdapter.setItems(DataUtils.acceptedList(), 1)
    }

    private fun setCompletedSelection() = with(binding) {
        setDefaultSelection()
        buttonCompleted.isSelected = true
        selectedCategoryType = RequestCategoryType.COMPLETED
        requestListAdapter.getSelectedRequestType(selectedCategoryType)
        requestListAdapter.setItems(DataUtils.completedList(), 1)
    }

    private fun setDefaultSelection() = with(binding) {
        buttonRequested.isSelected = false
        buttonAccepted.isSelected = false
        buttonCompleted.isSelected = false
    }

    private fun setRecyclerView() = with(binding.recyclerViewRequestList) {
        layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        adapter = requestListAdapter
    }
}