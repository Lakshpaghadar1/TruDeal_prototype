package com.trudeals.ui.isolated.customeruser.fragment

import android.text.InputFilter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.trudeals.R
import com.trudeals.databinding.CustomerLocalDealDetailFragmentBinding
import com.trudeals.databinding.LayoutShowMoreShowLessBinding
import com.trudeals.di.component.FragmentComponent
import com.trudeals.exception.ApplicationException
import com.trudeals.ui.base.BaseFragment
import com.trudeals.ui.common.isolated.IsolatedFullActivity
import com.trudeals.ui.home.dummydata.CustomerHomeListItem
import com.trudeals.ui.isolated.customeruser.adapter.BusinessHoursAdapter
import com.trudeals.ui.isolated.customeruser.adapter.ContactUsAdapter
import com.trudeals.ui.isolated.customeruser.adapter.HorizontalSliderAdapter
import com.trudeals.ui.isolated.customeruser.dialog.DialogRedeemNow
import com.trudeals.ui.isolated.realestateuser.fragment.MapFragment
import com.trudeals.utils.DataUtils
import com.trudeals.utils.MainCategoryType
import com.trudeals.utils.OnClick
import com.trudeals.utils.TabType
import com.trudeals.utils.constants.RegexConstant
import com.trudeals.utils.extension.applyFilter
import com.trudeals.utils.extension.scrollableText
import com.trudeals.utils.extension.trimmedText
import com.trudeals.utils.textdecorator.TextDecorator

class LocalDealDetailFragment : BaseFragment<CustomerLocalDealDetailFragmentBinding>(),
    View.OnClickListener {

    private val dialog by lazy { DialogRedeemNow() }

    private val horizontalSliderAdapter by lazy { HorizontalSliderAdapter() }

    private val businessHoursAdapter by lazy { BusinessHoursAdapter() }

    private val contactUsAdapter by lazy { ContactUsAdapter() }

    override fun inject(fragmentComponent: FragmentComponent) {
        fragmentComponent.inject(this)
    }

    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        attachToRoot: Boolean
    ): CustomerLocalDealDetailFragmentBinding {
        return CustomerLocalDealDetailFragmentBinding.inflate(layoutInflater)
    }

    override fun bindData() = with(binding) {
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

    private fun setData() = with(binding) {
        layoutAskQuestion.checkboxAllowSMS.text = getString(R.string.label_allow_sms_local_deals)

        val data = arguments?.getParcelable<CustomerHomeListItem>(DATA)
        /** set data at the time of API call based on selected tab
         * as of now each tab list data managed through single adapter
         * so its causing an issue for location and description for beauty and spa tab
         * because beauty and spa tab contains title, service name, location
         */
        textViewDealName.text = data?.title
        imageViewHeart.isSelected = data?.isLiked ?: false
        textViewServiceName.text = getString(R.string.dummy_service_name)
        textViewLocation.text =
            getString(R.string.dummy_beauty_and_spa_location)
        textViewDiscountedPrice.text = getString(R.string.label_price_x, "7,500")
        textViewOriginalPrice.text = getString(R.string.label_dollar_x, "8,500")
        setSpannable()

        if (saleDealCards.isVisible) {
            buttonSaleEnds.text = getString(R.string.label_sale_ends_next_line_date_x, "30/09/2022")
            buttonItemsLeft.text =
                getString(R.string.label_items_left_next_line_items_left_x, "16")
            buttonBought.text = getString(R.string.label_bought_next_line_count_x, "0")
        }

        layoutDealDescription.apply {
            textViewTitle.text = getString(R.string.label_deal_description)
            showMoreLess(
                layoutDealDescription,
                getString(R.string.dummy_deal_description)
            )
        }

        layoutTermsAndConditions.apply {
            textViewTitle.text = getString(R.string.label_terms_and_condition)
            showMoreLess(
                layoutTermsAndConditions,
                getString(R.string.dummy_tc_description_part_one)
                    .plus("\n").plus(getString(R.string.dummy_tc_description_part_two))
            )
        }

        layoutBusinessInfo.apply {
            textViewTitle.text = getString(R.string.label_business_info)
            showMoreLess(
                layoutBusinessInfo, getString(R.string.dummy_business_info_description)
            )
        }
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

    private fun setViewPager() = with(binding.viewPager) {
        horizontalSliderAdapter.setItems(DataUtils.localDealDetailsImages(), 1)
        adapter = horizontalSliderAdapter
        isUserInputEnabled = false
    }

    private fun setRecyclerView() = with(binding) {
        layoutBusinessHours.recyclerViewBusinessHours.apply {
            businessHoursAdapter.setItems(DataUtils.businessHours(), 1)
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            adapter = businessHoursAdapter
        }

        layoutContactUs.recyclerViewContactUs.apply {
            contactUsAdapter.setItems(DataUtils.contactUsDetails(), 1)
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            adapter = contactUsAdapter
        }
    }

    private fun clickListeners() = with(binding) {
        imageViewHeart.setOnClickListener(this@LocalDealDetailFragment)
        imageViewNext.setOnClickListener(this@LocalDealDetailFragment)
        imageViewPrevious.setOnClickListener(this@LocalDealDetailFragment)
        imageViewMapNavigatorBg.setOnClickListener(this@LocalDealDetailFragment)
        imageViewMapNavigator.setOnClickListener(this@LocalDealDetailFragment)
        buttonRedeem.setOnClickListener(this@LocalDealDetailFragment)
        buttonSave.setOnClickListener(this@LocalDealDetailFragment)
        buttonShare.setOnClickListener(this@LocalDealDetailFragment)
        onClickOfContactUsFields()
        layoutAskQuestion.buttonSendMessage.setOnClickListener(this@LocalDealDetailFragment)
        callBackFromRedeemDialog()
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
            buttonRedeem -> {
                dialog.show(childFragmentManager, DialogRedeemNow::class.java.simpleName)
            }
            buttonSave -> {
                buttonSave.isSelected = !buttonSave.isSelected
            }
            imageViewMapNavigator, imageViewMapNavigatorBg -> {
                navigator.loadActivity(IsolatedFullActivity::class.java, MapFragment::class.java)
                    .start()
            }
            buttonShare -> {
                showMessage("OPEN NATIVE SHARE DIALOG")
            }
            layoutAskQuestion.buttonSendMessage -> {
                if (validation() && layoutAskQuestion.checkboxAllowSMS.isChecked &&
                    layoutAskQuestion.checkboxNotifyUser.isChecked) {
                    showMessage(getString(R.string.msg_your_req_has_been_submitted_successfully))
                }
            }
        }
    }

    private fun callBackFromRedeemDialog() = with(binding) {
        dialog.setOnPositiveClick {
            dialog.dismiss()
            buttonRedeem.text = getString(R.string.btn_redeemed)
            buttonRedeem.isSelected = true
            buttonRedeem.isClickable = false
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
                    navigator.loadActivity(
                        IsolatedFullActivity::class.java,
                        MapFragment::class.java
                    ).start()
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

    private fun setSpannable() = with(binding) {
        TextDecorator.decorate(textViewDiscountedPrice, textViewDiscountedPrice.trimmedText)
            .setTypeface(
                ResourcesCompat.getFont(
                    textViewDiscountedPrice.context,
                    R.font.cerebri_sans_regular
                ), getString(R.string.label_price_dash)
            )
            .setTextColor(R.color.C_808080, getString(R.string.label_price_dash))
            .build()
    }

    private fun showMoreLess(layout: LayoutShowMoreShowLessBinding, description: String) =
        with(binding) {
            val maxLines = resources.getInteger(R.integer.int_2)

            layout.textViewDescription.maxLines = maxLines
            layout.textViewDescription.text = description

            layout.textViewShowMoreLess.setOnClickListener {
                val isExpanded = layout.textViewDescription.maxLines == Integer.MAX_VALUE
                layout.textViewDescription.maxLines =
                    if (isExpanded) maxLines else Integer.MAX_VALUE
                layout.textViewShowMoreLess.text =
                    if (isExpanded) context?.getString(R.string.label_show_more)
                    else context?.getString(R.string.label_show_less)
            }
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
                    showMessage(getString(R.string.validation_allow_sms_local_deals))
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
        private const val CURRENT_TAB_TYPE = "CURRENT_TAB_TYPE"
        fun createBundle(
            data: CustomerHomeListItem,
            currentTabType: TabType,
            mainCategoryType: MainCategoryType
        ) =
            bundleOf(
                DATA to data,
                CURRENT_TAB_TYPE to currentTabType,
                MAIN_CATEGORY_TYPE to mainCategoryType
            )
    }
}