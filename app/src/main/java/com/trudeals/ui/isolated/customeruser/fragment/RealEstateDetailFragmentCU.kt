package com.trudeals.ui.isolated.customeruser.fragment

import android.text.*
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.core.os.bundleOf
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.trudeals.R
import com.trudeals.databinding.CustomerRealEstateDetailFragmentBinding
import com.trudeals.di.component.FragmentComponent
import com.trudeals.exception.ApplicationException
import com.trudeals.ui.base.BaseFragment
import com.trudeals.ui.common.isolated.IsolatedFullActivity
import com.trudeals.ui.home.dummydata.CustomerHomeListItem
import com.trudeals.ui.isolated.customeruser.adapter.ContactUsAdapter
import com.trudeals.ui.isolated.customeruser.adapter.DocumentAdapter
import com.trudeals.ui.isolated.customeruser.adapter.HorizontalSliderAdapter
import com.trudeals.ui.isolated.customeruser.adapter.PropertyDetailsAdapter
import com.trudeals.ui.isolated.customeruser.bottomsheet.AddYourDetailsBottomsheet
import com.trudeals.ui.isolated.customeruser.bottomsheet.SelectDateAndTimeBottomsheet
import com.trudeals.ui.isolated.customeruser.dialog.DialogAppointmentReqSuccess
import com.trudeals.ui.isolated.realestateuser.fragment.MapFragment
import com.trudeals.utils.*
import com.trudeals.utils.constants.RegexConstant
import com.trudeals.utils.extension.applyFilter
import com.trudeals.utils.extension.scrollableText
import com.trudeals.utils.extension.trimmedText
import com.trudeals.utils.textdecorator.TextDecorator
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class RealEstateDetailFragmentCU : BaseFragment<CustomerRealEstateDetailFragmentBinding>(),
    View.OnClickListener {

    private val addYourDetailDialog by lazy { AddYourDetailsBottomsheet() }
    private val selectDateAndTimeDialog by lazy { SelectDateAndTimeBottomsheet() }
    private val dialogAppointmentReqSuccess by lazy { DialogAppointmentReqSuccess() }

    private val horizontalSliderAdapter by lazy {
        HorizontalSliderAdapter()
    }

    private val propertyDetailsAdapter by lazy {
        PropertyDetailsAdapter()
    }

    private val documentAdapter by lazy {
        DocumentAdapter()
    }

    private val contactUsAdapter by lazy {
        ContactUsAdapter()
    }

    override fun inject(fragmentComponent: FragmentComponent) {
        fragmentComponent.inject(this)
    }

    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        attachToRoot: Boolean
    ): CustomerRealEstateDetailFragmentBinding {
        return CustomerRealEstateDetailFragmentBinding.inflate(layoutInflater)
    }

    override fun bindData() {
        init()
        setViewPager()
        setRecyclerView()
        clickListeners()
    }

    override fun setUpToolbar() = with(toolbar) {
        showToolbar(true)
            .showBackButton(true)
            .setToolbarTitle(getString(R.string.label_details))
            .setToolbarElevation(R.dimen._1sdp).build()
    }

    private fun init() = with(binding) {
        layoutAskQuestion.editTextMessage.scrollableText()
        setFilters()
        setData()

    }

    private fun setFilters() = with(binding.layoutAskQuestion) {
       editTextFirstName.filters = arrayOf(
            editTextFirstName.applyFilter
                (blockSpecialChar = true, blockNumbersChar = true, applyEmojiFilter = true),
           InputFilter.LengthFilter(getInteger(R.integer.int_name_max_length))
        )
        editTextLastName.filters = arrayOf(
            editTextLastName.applyFilter
                (blockSpecialChar = true, blockNumbersChar = true, applyEmojiFilter = true),
            InputFilter.LengthFilter(getInteger(R.integer.int_name_max_length))
        )
        editTextEmailAddress.filters = arrayOf(
            editTextEmailAddress.applyFilter(blockSpecialChar = true, applyEmojiFilter = true)
        )
        editTextPhoneNumber.filters = arrayOf(editTextPhoneNumber.applyFilter(),
            InputFilter.LengthFilter(resources.getInteger(R.integer.int_phone_number_max_length)))
        editTextMessage.filters = arrayOf(editTextPhoneNumber.applyFilter())
    }

    private fun setData() = with(binding) {
        layoutAskQuestion.checkboxAllowSMS.text = getString(R.string.label_allow_sms_real_estate)

        val data = arguments?.getParcelable<CustomerHomeListItem>(DATA)
        val amenities = data?.numberOfAmenities

        showView(layoutAmenities.textViewBuiltYear)
        layoutAmenities.textViewBuiltYear.text =
            getString(R.string.label_built_in_year_x, "2015")
        layoutAmenities.textViewNoOfBeds.text =
            getString(R.string.label_x_beds, amenities?.numberOfBeds)
        layoutAmenities.textViewNoOfBath.text =
            getString(R.string.label_x_bath, amenities?.numberOfBath)
        layoutAmenities.textViewNoOfGarage.text =
            getString(R.string.label_x_garage, amenities?.numberOfGarage)
        layoutAmenities.textViewNoOfSqFt.text =
            getString(R.string.label_x_sq_ft, amenities?.numberOfSqFt)

        textViewLocation.text = data?.location
        textViewTitle.text = data?.title
        textViewAreaType.text =
            getString(R.string.label_area_x, getString(R.string.dummy_label_residential_area))
        textViewPrice.text = getString(R.string.label_price_x, data?.amount)
        textViewAddress.text =
            getString(R.string.label_address_x, data?.location)
        imageViewHeart.isSelected = data?.isLiked ?: false

        setSpannable()
        showMoreLess()
    }

    private fun setViewPager() = with(binding.viewPager) {
        horizontalSliderAdapter.setItems(DataUtils.realEstateDetailsImages(), 1)
        adapter = horizontalSliderAdapter
        isUserInputEnabled = false
    }

    private fun setRecyclerView() = with(binding) {
        recyclerViewPropertyDetails.apply {
            propertyDetailsAdapter.setItems(DataUtils.propertyDetails(), 1)
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            adapter = propertyDetailsAdapter
        }

        layoutContactUs.recyclerViewContactUs.apply {
            contactUsAdapter.setItems(DataUtils.contactUsDetails(), 1)
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            adapter = contactUsAdapter
        }

        recyclerViewDocuments.apply {
            documentAdapter.setItems(DataUtils.documentPdfs(), 1)
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            adapter = documentAdapter
        }
    }

    private fun clickListeners() = with(binding) {
        imageViewHeart.setOnClickListener(this@RealEstateDetailFragmentCU)
        imageViewNext.setOnClickListener(this@RealEstateDetailFragmentCU)
        imageViewPrevious.setOnClickListener(this@RealEstateDetailFragmentCU)
        imageViewMapNavigatorBg.setOnClickListener(this@RealEstateDetailFragmentCU)
        imageViewMapNavigator.setOnClickListener(this@RealEstateDetailFragmentCU)
        onClickOfContactUsFields()
        layoutAskQuestion.buttonSendMessage.setOnClickListener(this@RealEstateDetailFragmentCU)
        buttonShare.setOnClickListener(this@RealEstateDetailFragmentCU)
        buttonSchedule.setOnClickListener(this@RealEstateDetailFragmentCU)
        callBackFromSelectDateAndTimeBottomsheet()
        callBackFromAddYourDetailsBottomsheet()
        onCLickOfDownloadPdf()
    }

    override fun onClick(v: View) = with(binding) {
        when (v) {
            imageViewNext -> {
                val currentItem = viewPager.currentItem
                if (currentItem < DataUtils.realEstateDetailsImages().size - 1)
                    viewPager.currentItem = currentItem + 1
            }
            imageViewPrevious -> {
                val currentItem = viewPager.currentItem
                if (currentItem > 0) viewPager.currentItem = currentItem - 1
            }
            imageViewHeart -> {
                imageViewHeart.isSelected = !imageViewHeart.isSelected
            }
            imageViewMapNavigator, imageViewMapNavigatorBg -> {
                navigator.loadActivity(IsolatedFullActivity::class.java, MapFragment::class.java)
                    .start()
            }
            layoutAskQuestion.buttonSendMessage -> {
                if (validation() && layoutAskQuestion.checkboxAllowSMS.isChecked &&
                    layoutAskQuestion.checkboxNotifyUser.isChecked) {
                    showMessage(getString(R.string.msg_your_req_has_been_submitted_successfully))
                }
            }
            buttonShare -> {
                showMessage("OPEN NATIVE SHARE DIALOG")
            }
            buttonSchedule -> {
                selectDateAndTimeDialog.show(
                    childFragmentManager,
                    SelectDateAndTimeBottomsheet::class.java.simpleName
                )
            }
        }
    }

    private fun onClickOfContactUsFields() = with(binding.layoutContactUs) {
        imageViewIconFacebook.setOnClickListener {
            showMessage("FACEBOOK")
        }
        imageViewIconInstagram.setOnClickListener {
            showMessage("INSTAGRAM")
        }
        imageViewIconTwitter.setOnClickListener {
            showMessage("TWITTER")
        }
        imageViewIconWhatsapp.setOnClickListener {
            showMessage("WHATSAPP")
        }
        contactUsAdapter.setOnItemClickPositionListener { item, _ ->
            when (item.onClick) {
                OnClick.CONTACT -> {
                    showMessage("OPEN NATIVE DIAL PAD")
                }
                OnClick.ADDRESS -> {
                    navigator.loadActivity(IsolatedFullActivity::class.java, MapFragment::class.java)
                        .start()
                }
                OnClick.WEBSITE -> {
                    showMessage("OPEN WEBSITE")
                }
                OnClick.EMAIL_ID -> {
                    showMessage("OPEN MAILBOX")
                }
                else -> {}
            }
        }
    }

    private fun onCLickOfDownloadPdf() {
        documentAdapter.setOnItemClickPositionListener { _, _ ->
            lifecycleScope.launch {
                showMessage("Downloading...")
                delay(500)
                showMessage("Downloaded...")
            }
        }
    }

    /**
     * store received data in a variable from setOnNextClick call back
     * (before dismissing selectDateAndTimeDialog)
     * eg :- selectDateAndTimeDialog.setOnNextClick { tempData ->
    val tempData = tempData
    selectDateAndTimeDialog.dismiss()
    //optional :- pass received data to next screen
    //optional :- now rest code to open another bottomsheet, dialog, load fragment
    }
     */
    private fun callBackFromSelectDateAndTimeBottomsheet() {
        selectDateAndTimeDialog.setOnNextClick {
            selectDateAndTimeDialog.dismiss()
            addYourDetailDialog.show(
                childFragmentManager,
                AddYourDetailsBottomsheet::class.java.simpleName
            )
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

    private fun setSpannable() = with(binding) {
        TextDecorator.decorate(textViewAreaType, textViewAreaType.trimmedText)
            .setTypeface(
                ResourcesCompat.getFont(
                    textViewAreaType.context,
                    R.font.cerebri_sans_regular
                ), getString(R.string.label_area)
            )
            .setTextColor(R.color.C_808080, getString(R.string.label_area))
            .build()

        TextDecorator.decorate(textViewPrice, textViewPrice.trimmedText)
            .setTypeface(
                ResourcesCompat.getFont(
                    textViewPrice.context,
                    R.font.cerebri_sans_regular
                ), getString(R.string.label_price_dash)
            )
            .setTextColor(R.color.C_808080, getString(R.string.label_price_dash))
            .build()

        TextDecorator.decorate(textViewAddress, textViewAddress.trimmedText)
            .setTypeface(
                ResourcesCompat.getFont(
                    textViewAddress.context,
                    R.font.cerebri_sans_regular
                ), getString(R.string.label_address_dash)
            )
            .setTextColor(R.color.C_808080, getString(R.string.label_address_dash))
            .build()
    }

    private fun showMoreLess() = with(binding) {
        val maxLines = resources.getInteger(R.integer.int_2)
        val title = context?.getString(R.string.label_description_dash).plus(" ")
        val description = title.plus(context?.getString(R.string.dummy_description_detail))

        textViewDescription.maxLines = maxLines
        textViewDescription.text = description
        setSpannableOnDescription(title)

        textViewShowMoreLess.setOnClickListener {
            val isExpanded = textViewDescription.maxLines == Integer.MAX_VALUE
            textViewDescription.maxLines = if (isExpanded) maxLines else Integer.MAX_VALUE
            textViewShowMoreLess.text =
                if (isExpanded) context?.getString(R.string.label_show_more)
                else context?.getString(R.string.label_show_less)
            setSpannableOnDescription(title)
        }
    }

    private fun setSpannableOnDescription(title: String) = with(binding) {
        TextDecorator.decorate(textViewDescription, textViewDescription.trimmedText)
            .setTextColor(R.color.C_808080, title)
            .build()
    }

    private fun validation(): Boolean = with(binding.layoutAskQuestion) {
        return try {
            with(validator) {
                submit(editTextFirstName).checkEmpty()
                    .errorMessage(getString(R.string.validation_enter_first_name))
                    .check()

                submit(editTextLastName).checkEmpty()
                    .errorMessage(getString(R.string.validation_enter_last_name))
                    .check()

                submit(editTextEmailAddress).checkEmpty()
                    .errorMessage(getString(R.string.validation_enter_email_address))
                    .checkValidEmail().errorMessage(getString(R.string.validation_valid_email))
                    .check()

                submit(editTextPhoneNumber).checkEmpty()
                    .errorMessage(getString(R.string.validation_enter_phone_number))
                    .checkMinDigits(resources.getInteger(R.integer.int_phone_number_min_length))
                    .errorMessage(getString(R.string.validation_min_digits_phone_number))
                    .matchPatter(RegexConstant.CHECK_PHONE_NUMBER)
                    .errorMessage(getString(R.string.validation_enter_valid_phone_number))
                    .check()

                submit(editTextMessage).checkEmpty()
                    .errorMessage(getString(R.string.validation_enter_message))
                    .check()

                if (!checkboxAllowSMS.isChecked) {
                    showMessage(getString(R.string.validation_allow_sms_real_estate))
                } else if (!checkboxNotifyUser.isChecked) {
                    showMessage(getString(R.string.validation_allow_notification))
                }
                true
            }
        } catch (e: ApplicationException) {
            showMessage(e)
            false
        }
    }

    companion object {
        private const val DATA = "DATA"
        private const val MAIN_CATEGORY_TYPE = "MAIN_CATEGORY_TYPE"
        private const val CURRENT_TAB = "CURRENT_TAB"
        fun createBundle(
            data: CustomerHomeListItem,
            mainCategoryType: MainCategoryType,
            currentTab: TabType
        ) =
            bundleOf(
                DATA to data,
                MAIN_CATEGORY_TYPE to mainCategoryType,
                CURRENT_TAB to currentTab
            )
    }
}
