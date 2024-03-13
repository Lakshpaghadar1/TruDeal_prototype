package com.trudeals.ui.isolated.realestateuser.fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.core.os.bundleOf
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.trudeals.R
import com.trudeals.databinding.RealEstateDetailFragmentBinding
import com.trudeals.di.component.FragmentComponent
import com.trudeals.ui.base.BaseFragment
import com.trudeals.ui.common.isolated.IsolatedFullActivity
import com.trudeals.ui.home.dummydata.RealEstateHomeListItem
import com.trudeals.ui.isolated.customeruser.adapter.DocumentAdapter
import com.trudeals.ui.isolated.customeruser.adapter.HorizontalSliderAdapter
import com.trudeals.ui.isolated.customeruser.adapter.PropertyDetailsAdapter
import com.trudeals.ui.isolated.customeruser.bottomsheet.SelectDateAndTimeBottomsheet
import com.trudeals.ui.isolated.customeruser.dialog.DialogAppointmentReqSuccess
import com.trudeals.ui.isolated.customeruser.fragment.ChatListFragment
import com.trudeals.ui.isolated.customeruser.fragment.ViewImageVideoFragment
import com.trudeals.ui.isolated.customeruser.fragment.ViewImageVideoFragment.Companion.IS_VIDEO
import com.trudeals.utils.*
import com.trudeals.utils.extension.trimmedText
import com.trudeals.utils.textdecorator.TextDecorator
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class RealEstateDetailFragmentREU : BaseFragment<RealEstateDetailFragmentBinding>(),
    View.OnClickListener {

    private val selectDateAndTimeDialog by lazy { SelectDateAndTimeBottomsheet() }
    private val dialogAppointmentReqSuccess by lazy { DialogAppointmentReqSuccess() }
    private val data by lazy { arguments?.getParcelable<RealEstateHomeListItem>(DATA) }
    private val currentTab by lazy { arguments?.getParcelable<TabType>(CURRENT_TAB) }

    private val horizontalSliderAdapter by lazy {
        HorizontalSliderAdapter()
    }

    private val propertyDetailsAdapter by lazy {
        PropertyDetailsAdapter()
    }

    private val documentAdapter by lazy {
        DocumentAdapter()
    }

    override fun inject(fragmentComponent: FragmentComponent) {
        fragmentComponent.inject(this)
    }

    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        attachToRoot: Boolean
    ): RealEstateDetailFragmentBinding {
        return RealEstateDetailFragmentBinding.inflate(layoutInflater)
    }

    override fun bindData() {
        init()
        clickListener()
    }

    override fun setUpToolbar() = with(toolbar) {
        showToolbar(true)
            .showBackButton(true)
            .setToolbarTitle(getString(R.string.label_details))
            .setToolbarElevation(R.dimen._1sdp).build()
    }

    private fun init() = with(binding) {
        setData()
        setViewPager()
        setRecyclerView()
        Glide.with(requireContext()).asGif().load(R.drawable.ic_clock_white).into(imageViewClock)
    }

    private fun setData() = with(binding) {
        val amenities = data?.numberOfAmenities

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
        imageViewHeart.isSelected = data?.isLiked ?: false
        textViewAreaType.text =
            getString(R.string.label_area_x, getString(R.string.dummy_label_residential_area))
        textViewPrice.text = getString(R.string.label_price_x, data?.amount)
        textViewAddress.text =
            getString(R.string.label_address_x, data?.location)

        statusVisibility(currentTab ?: TabType.ALL, data?.status ?: StatusType.NONE)
        setSpannable()
        showMoreLess()
    }

    private fun statusVisibility(currentTab: TabType, status: StatusType) =
        with(binding.layoutStatus) {
            when (currentTab) {
                TabType.ALL -> {
                    setStatusForAllTab(status)
                }
                TabType.HOME_FOR_SALE -> {
                    showView(textViewForSale, binding.constraintLayoutOpenHouse)
                    textViewForSale.text = getString(R.string.label_for_sale)
                }
                TabType.RENTALS -> {
                    showView(textViewForSale)
                    hideView(binding.constraintLayoutOpenHouse)
                    textViewForSale.text = getString(R.string.label_for_rent)
                }
                TabType.VACATION_RENTALS -> {
                    showView(textViewForSale)
                    hideView(binding.constraintLayoutOpenHouse)
                    textViewForSale.text = getString(R.string.label_for_vacation_rent)
                }
                else -> {}
            }
        }

    private fun setStatusForAllTab(status: StatusType) =
        with(binding.layoutStatus) {
            when (status) {
                StatusType.FOR_SALE -> {
                    showView(textViewForSale, binding.constraintLayoutOpenHouse)
                    textViewForSale.text = getString(R.string.label_for_sale)
                }
                StatusType.FEATURED -> {
                    showView(textViewFeatured)
                    hideView(binding.constraintLayoutOpenHouse)
                }
                StatusType.FOR_RENT -> {
                    showView(textViewForSale)
                    hideView(binding.constraintLayoutOpenHouse)
                    textViewForSale.text = getString(R.string.label_for_rent)
                }
                StatusType.DISCOUNT -> {
                    showView(textViewDiscount)
                    hideView(binding.constraintLayoutOpenHouse)
                }
                StatusType.FEATURED_AND_FORSALE -> {
                    showView(textViewFeatured, textViewForSale, binding.constraintLayoutOpenHouse)
                    textViewForSale.text = getString(R.string.label_for_sale)
                }
                StatusType.FOR_VACATION_RENT -> {
                    showView(textViewForSale)
                    hideView(binding.constraintLayoutOpenHouse)
                    textViewForSale.text = getString(R.string.label_for_vacation_rent)
                }
                StatusType.NONE -> {
                    hideView(this.root, binding.constraintLayoutOpenHouse)
                }
            }
        }


    private fun setViewPager() = with(binding.viewPager) {
        horizontalSliderAdapter.setItems(DataUtils.realEstateDetailsImages(), 1)
        adapter = horizontalSliderAdapter
        isUserInputEnabled = false
    }

    private fun setRecyclerView() = with(binding) {
        recyclerViewPropertyDetails.apply {
            propertyDetailsAdapter.setItems(DataUtils.realEstatePropertyDetails(), 1)
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            adapter = propertyDetailsAdapter

            //manage click on nested video view
            propertyDetailsAdapter.setOnClick { onCLick, item ->
                when (onCLick) {
                    IS_VIDEO -> {
                        loadImageFragment(true, item.imageForVideo.toString(), false)
                    }
                }
            }
        }

        recyclerViewDocuments.apply {
            documentAdapter.setItems(DataUtils.documentPdfs(), 1)
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            adapter = documentAdapter
        }

    }

    private fun loadImageFragment(isVideo: Boolean, link: String, isDrawable: Boolean) {
        navigator.loadActivity(
            IsolatedFullActivity::class.java,
            ViewImageVideoFragment::class.java
        ).addBundle(
            ViewImageVideoFragment.createBundle(
                isVideo = isVideo, itemMediaPath = link, isDrawable = isDrawable
            )
        ).start()
    }

    private fun clickListener() = with(binding) {
        imageViewHeart.setOnClickListener(this@RealEstateDetailFragmentREU)
        imageViewNext.setOnClickListener(this@RealEstateDetailFragmentREU)
        imageViewPrevious.setOnClickListener(this@RealEstateDetailFragmentREU)
        constraintLayoutOpenHouse.setOnClickListener(this@RealEstateDetailFragmentREU)
        imageViewMapNavigatorBg.setOnClickListener(this@RealEstateDetailFragmentREU)
        imageViewMapNavigator.setOnClickListener(this@RealEstateDetailFragmentREU)
        buttonShare.setOnClickListener(this@RealEstateDetailFragmentREU)
        buttonChat.setOnClickListener(this@RealEstateDetailFragmentREU)
        callBackFromSelectDateAndTimeBottomsheet()
        onCLickOfDownloadPdf()
    }

    override fun onClick(v: View) = with(binding) {
        when (v) {
            imageViewHeart -> {
                imageViewHeart.isSelected = !imageViewHeart.isSelected
            }
            imageViewNext -> {
                val currentItem = viewPager.currentItem
                if (currentItem < DataUtils.realEstateDetailsImages().size - 1)
                    viewPager.currentItem = currentItem + 1
            }
            imageViewPrevious -> {
                val currentItem = viewPager.currentItem
                if (currentItem > 0) viewPager.currentItem = currentItem - 1
            }
            constraintLayoutOpenHouse -> {
                /*navigator.loadActivity(
                    IsolatedFullActivity::class.java,
                    ScheduleOpenHouseTimingFragment::class.java
                ).start()*/
                selectDateAndTimeDialog.show(
                    childFragmentManager,
                    SelectDateAndTimeBottomsheet::class.java.simpleName
                )
                selectDateAndTimeDialog.sourceScreen =
                    RealEstateDetailFragmentREU::class.java.simpleName
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
                    ChatListFragment::class.java
                ).start()
            }
        }
    }

    private fun callBackFromSelectDateAndTimeBottomsheet() {
        selectDateAndTimeDialog.setOnNextClick {
            selectDateAndTimeDialog.dismiss()
            dialogAppointmentReqSuccess.show(
                childFragmentManager,
                DialogAppointmentReqSuccess::class.java.simpleName
            )
            /*navigator.loadActivity(IsolatedFullActivity::class.java, RequestFragmentREU::class.java)
                .start()*/
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

    companion object {
        private const val DATA = "DATA"
        private const val CURRENT_TAB = "CURRENT_TAB"
        fun createBundle(
            data: RealEstateHomeListItem,
            currentTab: TabType
        ) =
            bundleOf(
                DATA to data,
                CURRENT_TAB to currentTab,
            )
    }
}
