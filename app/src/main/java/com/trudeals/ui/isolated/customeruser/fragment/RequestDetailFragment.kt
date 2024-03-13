package com.trudeals.ui.isolated.customeruser.fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import com.trudeals.R
import com.trudeals.databinding.RequestDetailFragmentBinding
import com.trudeals.di.component.FragmentComponent
import com.trudeals.ui.base.BaseFragment
import com.trudeals.ui.common.isolated.IsolatedFullActivity
import com.trudeals.ui.isolated.customeruser.bottomsheet.AddYourDetailsBottomsheet
import com.trudeals.ui.isolated.customeruser.bottomsheet.SelectDateAndTimeBottomsheet
import com.trudeals.ui.isolated.customeruser.dialog.DialogAppointmentReqSuccess
import com.trudeals.ui.isolated.dummydata.RequestList
import com.trudeals.ui.isolated.realestateuser.dialog.ReasonToCancelDialog
import com.trudeals.ui.isolated.realestateuser.fragment.BookingRequestFragment
import com.trudeals.ui.isolated.realestateuser.fragment.ScheduleOpenHouseTimingFragment
import com.trudeals.utils.RequestCategoryType
import com.trudeals.utils.extension.isVisible
import com.trudeals.utils.extension.trimmedText
import com.trudeals.utils.hideView
import com.trudeals.utils.showView

class RequestDetailFragment : BaseFragment<RequestDetailFragmentBinding>(), View.OnClickListener {

    private val addYourDetailDialog by lazy { AddYourDetailsBottomsheet() }
    private val selectDateAndTimeDialog by lazy { SelectDateAndTimeBottomsheet() }
    private val reasonToCancelDialog by lazy { ReasonToCancelDialog() }
    private val dialogAppointmentReqSuccess by lazy { DialogAppointmentReqSuccess() }
    private val requestType by lazy { arguments?.getParcelable<RequestCategoryType>(REQUEST_TYPE) }
    private val sourceScreen by lazy { arguments?.getString(SOURCE_SCREEN) }
    private val isScheduled by lazy { arguments?.getBoolean(IS_SCHEDULED) }

    override fun inject(fragmentComponent: FragmentComponent) {
        fragmentComponent.inject(this)
    }

    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        attachToRoot: Boolean
    ): RequestDetailFragmentBinding {
        return RequestDetailFragmentBinding.inflate(layoutInflater)
    }

    override fun bindData() {
        init()
        clickListener()
    }

    override fun setUpToolbar() = with(toolbar) {
        showToolbar(true)
            .showBackButton(true)
        when (sourceScreen) {
            ScheduleOpenHouseTimingFragment::class.java.simpleName -> {
                setToolbarTitle(getString(R.string.label_details))
            }
            else -> {
                setToolbarTitle(getString(R.string.label_details_request))
            }
        }
            .setToolbarElevation(R.dimen._1sdp)
            .build()
    }

    private fun init() = with(binding) {
        when (requestType) {
            RequestCategoryType.REQUESTED -> {
                when (sourceScreen) {
                    RequestFragment::class.java.simpleName -> {
                        setButtons(isChatButtonVisible = false, isCancelButtonVisible = false)
                    }
                    BookingRequestFragment::class.java.simpleName -> {
                        setButtons(isChatButtonVisible = false, isCancelButtonVisible = true)
                    }
                    else -> {}
                }
            }
            RequestCategoryType.ACCEPTED -> {
                setButtons(isChatButtonVisible = true)
            }
            RequestCategoryType.COMPLETED -> {
                hideView(constraintLayoutNavButtons)
            }
            else -> {}
        }

        when (sourceScreen) {
            ScheduleOpenHouseTimingFragment::class.java.simpleName -> {
                hideView(buttonModifyRequest, buttonReject, buttonCancel, buttonAccept, buttonChat)
                isScheduled?.let { buttonSchedule.isVisible(!it) }
                isScheduled?.let { buttonEditSchedule.isVisible(it) }
            }
            else -> {}
        }
    }

    private fun setButtons(
        isChatButtonVisible: Boolean = false,
        isCancelButtonVisible: Boolean = false
    ) = with(binding) {
        showView(constraintLayoutNavButtons)
        buttonModifyRequest.isVisible(!isChatButtonVisible)
        buttonReject.isVisible(!isChatButtonVisible)
        buttonCancel.isVisible(!isChatButtonVisible && isCancelButtonVisible)
        buttonAccept.isVisible(!isChatButtonVisible)
        buttonChat.isVisible(isChatButtonVisible)
    }

    private fun clickListener() = with(binding) {
        buttonModifyRequest.setOnClickListener(this@RequestDetailFragment)
        buttonReject.setOnClickListener(this@RequestDetailFragment)
        buttonCancel.setOnClickListener(this@RequestDetailFragment)
        buttonAccept.setOnClickListener(this@RequestDetailFragment)
        buttonChat.setOnClickListener(this@RequestDetailFragment)
        buttonSchedule.setOnClickListener(this@RequestDetailFragment)
        buttonEditSchedule.setOnClickListener(this@RequestDetailFragment)

        callBackFromSelectDateAndTimeBottomsheet()
        callBackFromAddYourDetailsBottomsheet()
        callBackFromReasonToCancelDialog()
    }


    override fun onClick(v: View) = with(binding) {
        when (v) {
            buttonModifyRequest -> {
                selectDateAndTimeDialog.show(
                    childFragmentManager,
                    SelectDateAndTimeBottomsheet::class.java.simpleName
                )
            }
            buttonReject -> {
                navigator.goBack()
            }
            buttonCancel -> {
                reasonToCancelDialog.show(
                    childFragmentManager,
                    ReasonToCancelDialog::class.java.simpleName
                )
            }
            buttonAccept -> {
                navigator.goBack()
            }
            buttonChat -> {
                navigator.loadActivity(
                    IsolatedFullActivity::class.java,
                    ChatFragment::class.java
                ).addBundle(ChatFragment.createBundle(textViewName.trimmedText)).start()
            }
            buttonSchedule -> {
                selectDateAndTimeDialog.show(
                    childFragmentManager,
                    SelectDateAndTimeBottomsheet::class.java.simpleName
                )
            }
            buttonEditSchedule -> {
                selectDateAndTimeDialog.show(
                    childFragmentManager,
                    SelectDateAndTimeBottomsheet::class.java.simpleName
                )
            }
        }
    }

    private fun callBackFromSelectDateAndTimeBottomsheet() {
        selectDateAndTimeDialog.setOnNextClick {
            selectDateAndTimeDialog.dismiss()
            when (sourceScreen) {
                ScheduleOpenHouseTimingFragment::class.java.simpleName -> {
                    dialogAppointmentReqSuccess.show(
                        childFragmentManager,
                        DialogAppointmentReqSuccess::class.java.simpleName
                    )
                }
                else -> {
                    addYourDetailDialog.show(
                        childFragmentManager,
                        AddYourDetailsBottomsheet::class.java.simpleName
                    )
                }
            }
        }
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

    companion object {
        private const val DATA = "DATA"
        private const val REQUEST_TYPE = "REQUEST_TYPE"
        private const val SOURCE_SCREEN = "SOURCE_SCREEN"
        private const val IS_SCHEDULED = "IS_SCHEDULED"
        fun createBundle(
            data: RequestList?,
            requestType: RequestCategoryType,
            sourceScreen: String
        ) =
            bundleOf(DATA to data, REQUEST_TYPE to requestType, SOURCE_SCREEN to sourceScreen)

        fun createScheduleBundle(
            data: RequestList?,
            isScheduled: Boolean,
            sourceScreen: String
        ) =
            bundleOf(DATA to data, IS_SCHEDULED to isScheduled, SOURCE_SCREEN to sourceScreen)
    }
}