package com.trudeals.ui.isolated.businessuser.fragment

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.trudeals.R
import com.trudeals.databinding.BusinessDealDetailFragmentBinding
import com.trudeals.databinding.LayoutShowMoreShowLessBinding
import com.trudeals.di.component.FragmentComponent
import com.trudeals.ui.base.BaseFragment
import com.trudeals.ui.common.isolated.IsolatedFullActivity
import com.trudeals.ui.home.businessuser.HomeActivityBU
import com.trudeals.ui.home.businessuser.fragment.HomeFragmentBU
import com.trudeals.ui.home.businessuser.fragment.MyFavOrViewAllListFragmentBU
import com.trudeals.ui.home.dummydata.BusinessHomeListItem
import com.trudeals.ui.isolated.customeruser.adapter.BusinessHoursAdapter
import com.trudeals.ui.isolated.customeruser.adapter.ContactUsAdapter
import com.trudeals.ui.isolated.customeruser.adapter.HorizontalSliderAdapter
import com.trudeals.ui.isolated.customeruser.fragment.ChatFragment
import com.trudeals.ui.isolated.dummydata.BusinessDeal
import com.trudeals.ui.isolated.realestateuser.fragment.MapFragment
import com.trudeals.utils.DataUtils
import com.trudeals.utils.OnClick
import com.trudeals.utils.extension.trimmedText
import com.trudeals.utils.hideView
import com.trudeals.utils.showView
import com.trudeals.utils.textdecorator.TextDecorator

class BusinessDealDetailFragment : BaseFragment<BusinessDealDetailFragmentBinding>(),
    View.OnClickListener {

    private val sourceScreen by lazy { arguments?.getString(SOURCE_SCREEN) }
    private val dealData by lazy { arguments?.getParcelable<BusinessDeal>(DEAL_DATA) }
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
    ): BusinessDealDetailFragmentBinding {
        return BusinessDealDetailFragmentBinding.inflate(layoutInflater)
    }

    override fun bindData() = with(binding) {
        setButtons()
        setData()
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

    private fun setButtons() = with(binding) {
        when (sourceScreen) {
            HomeFragmentBU::class.java.simpleName, MyFavOrViewAllListFragmentBU::class.java.simpleName -> {
                showView(buttonChat)
                hideView(buttonEditDeal, buttonPlaceLive)
            }
            AddOrEditDealDetailsFragment::class.java.simpleName, AddBusinessAvailabilityDetailsFragment::class.java.simpleName -> {
                hideView(buttonChat)
                showView(buttonEditDeal, buttonPlaceLive)
            }
        }
    }

    private fun setData() = with(binding) {
        //val data = arguments?.getParcelable<BusinessHomeListItem>(DATA)
        /** set data at the time of API call based on selected tab
         * as of now each tab list data managed through single adapter
         * so its causing an issue for location and description for beauty and spa tab
         * because beauty and spa tab contains title, service name, location
         */
        textViewDealName.text = getString(R.string.label_x, "Adventure High Ropes")
        textViewServiceName.text = getString(R.string.dummy_deal_name)
        textViewLocation.text =
            getString(R.string.dummy_beauty_and_spa_location)
        textViewDiscountedPrice.text = getString(R.string.label_price_x, "129")
        textViewOriginalPrice.text = getString(R.string.label_dollar_x, "200")
        textViewBadge.text = getString(R.string.label_x, "10")
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

    private fun setViewPager() = with(binding.viewPager) {
        horizontalSliderAdapter.setItems(DataUtils.automotiveImages(), 1)
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
        imageViewHeart.setOnClickListener(this@BusinessDealDetailFragment)
        imageViewNext.setOnClickListener(this@BusinessDealDetailFragment)
        imageViewPrevious.setOnClickListener(this@BusinessDealDetailFragment)
        imageViewMapNavigatorBg.setOnClickListener(this@BusinessDealDetailFragment)
        imageViewMapNavigator.setOnClickListener(this@BusinessDealDetailFragment)
        buttonShare.setOnClickListener(this@BusinessDealDetailFragment)
        buttonChat.setOnClickListener(this@BusinessDealDetailFragment)
        buttonEditDeal.setOnClickListener(this@BusinessDealDetailFragment)
        buttonPlaceLive.setOnClickListener(this@BusinessDealDetailFragment)
        onClickOfContactUsFields()
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
            buttonShare -> {
                showMessage("OPEN NATIVE SHARE DIALOG")
            }
            buttonChat -> {
                navigator.loadActivity(
                    IsolatedFullActivity::class.java,
                    ChatFragment::class.java
                ).addBundle(ChatFragment.createBundle(textViewDealName.trimmedText)).start()
            }
            buttonEditDeal -> {
                val intent = Intent()
                intent.putExtra("IS_EDIT", true)
                requireActivity().setResult(Activity.RESULT_OK, intent)
                navigator.goBack()
            }
            buttonPlaceLive -> {
                when(sourceScreen) {
                    AddBusinessAvailabilityDetailsFragment::class.java.simpleName -> {
                        session.isBUSubscribed = true
                        defaultUserSelection()
                        session.isBusinessOwnerUser = true
                        navigator.loadActivity(HomeActivityBU::class.java).start()
                    }
                    else -> {
                        val intent = Intent()
                        intent.putExtra(DATA, dealData) //set data
                        requireActivity().setResult(Activity.RESULT_OK, intent)
                        navigator.goBack()
                    }
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

    companion object {
        private const val DATA = "DATA"
        private const val DEAL_DATA = "DEAL_DATA"
        private const val SOURCE_SCREEN = "SOURCE_SCREEN"

        fun createBundle(data: BusinessHomeListItem, dealData: BusinessDeal, sourceScreen: String) =
            bundleOf(DATA to data, DEAL_DATA to dealData, SOURCE_SCREEN to sourceScreen)
    }
}