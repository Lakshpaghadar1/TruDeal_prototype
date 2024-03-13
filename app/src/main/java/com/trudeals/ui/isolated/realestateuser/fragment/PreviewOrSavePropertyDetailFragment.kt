package com.trudeals.ui.isolated.realestateuser.fragment

import android.app.Activity
import android.content.Intent
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.core.os.bundleOf
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.trudeals.R
import com.trudeals.databinding.PreviewSavePropertyDetailFragmentBinding
import com.trudeals.di.component.FragmentComponent
import com.trudeals.ui.base.BaseFragment
import com.trudeals.ui.common.isolated.IsolatedFullActivity
import com.trudeals.ui.isolated.customeruser.adapter.DocumentAdapter
import com.trudeals.ui.isolated.customeruser.adapter.HorizontalSliderAdapter
import com.trudeals.ui.isolated.customeruser.adapter.PropertyDetailsAdapter
import com.trudeals.ui.isolated.customeruser.bottomsheet.SelectDateAndTimeBottomsheet
import com.trudeals.ui.isolated.customeruser.fragment.ChatListFragment
import com.trudeals.ui.isolated.customeruser.fragment.ViewImageVideoFragment
import com.trudeals.ui.isolated.customeruser.fragment.ViewImageVideoFragment.Companion.IS_VIDEO
import com.trudeals.ui.isolated.dummydata.RealEstatePropertyList
import com.trudeals.utils.DataUtils
import com.trudeals.utils.PropertyListTag
import com.trudeals.utils.extension.trimmedText
import com.trudeals.utils.hideView
import com.trudeals.utils.showView
import com.trudeals.utils.textdecorator.TextDecorator
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class PreviewOrSavePropertyDetailFragment :
    BaseFragment<PreviewSavePropertyDetailFragmentBinding>(),
    View.OnClickListener {

    private val selectDateAndTimeDialog by lazy { SelectDateAndTimeBottomsheet() }
    private val horizontalSliderAdapter by lazy { HorizontalSliderAdapter() }
    private val propertyDetailsAdapter by lazy { PropertyDetailsAdapter() }
    private val documentAdapter by lazy { DocumentAdapter() }

    val currentTab by lazy { arguments?.getParcelable<PropertyListTag>(CURRENT_TAB) }
    val data by lazy { arguments?.getParcelable<RealEstatePropertyList>(DATA) }

    override fun inject(fragmentComponent: FragmentComponent) {
        fragmentComponent.inject(this)
    }

    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        attachToRoot: Boolean
    ): PreviewSavePropertyDetailFragmentBinding {
        return PreviewSavePropertyDetailFragmentBinding.inflate(layoutInflater)
    }

    override fun bindData() {
        init()
        clickListeners()
    }

    override fun setUpToolbar() = with(toolbar) {
        showToolbar(true)
            .showBackButton(true)
            .setToolbarTitle(getString(R.string.label_details))
            .setToolbarElevation(R.dimen._1sdp).build()
    }

    private fun init() = with(binding) {
        setButtonVisibility()
        setData()
        setViewPager()
        setRecyclerView()
    }

    private fun setButtonVisibility() = with(binding) {
        when (arguments?.getString(SOURCE_SCREEN)) {
            AddOrEditPropertyDetailsFragment::class.java.simpleName -> {
                showView(buttonEditListing, buttonPlaceLive)
                hideView(buttonShare, buttonChat)
            }

            RealEstatePropertyListFragment::class.java.simpleName -> {
                hideView(buttonEditListing, buttonPlaceLive)
                showView(buttonShare, buttonChat)
            }
        }
    }

    private fun setData() = with(binding) {
        statusVisibility(currentTab ?: PropertyListTag.HOME_FOR_SALE)

        //manage views visibility based on view data

        //get bundle data and set - api time
        layoutAmenities.textViewBuiltYear.text =
            getString(R.string.label_built_in_year_x, "2015")
        layoutAmenities.textViewNoOfBeds.text =
            getString(R.string.label_x_beds, "2")
        layoutAmenities.textViewNoOfBath.text =
            getString(R.string.label_x_bath, "1")
        layoutAmenities.textViewNoOfGarage.text =
            getString(R.string.label_x_garage, "1")
        layoutAmenities.textViewNoOfSqFt.text =
            getString(R.string.label_x_sq_ft, "1200")

        textViewLocation.text = getString(R.string.dummy_address)
        textViewTitle.text = getString(R.string.dummy_property_title)
        textViewAreaType.text =
            getString(R.string.label_area_x, getString(R.string.dummy_label_residential_area))
        textViewPrice.text = getString(R.string.label_price_x, "550,000")
        textViewRentDuration.text =
            getString(R.string.label_rent_duration_from_x_to_y, "1 Jan 2023", "1 jan 2027")
        textViewAddress.text =
            getString(R.string.label_address_x, "SY 104, Corona Circle, CA, USA")

        setSpannable()
        showMoreLess()
    }

    private fun statusVisibility(currentTab: PropertyListTag) =
        with(binding.layoutStatus) {
            when (currentTab) {
                PropertyListTag.HOME_FOR_SALE -> {
                    showView(textViewForSale)
                    hideView(binding.textViewRentDuration)
                    textViewForSale.text = getString(R.string.label_for_sale)
                }
                PropertyListTag.HOME_FOR_RENT -> {
                    showView(textViewForSale, binding.textViewRentDuration)
                    textViewForSale.text = getString(R.string.label_for_rent)
                }
                PropertyListTag.VACATION_RENTAL -> {
                    showView(textViewForSale, binding.textViewRentDuration)
                    textViewForSale.text = getString(R.string.label_for_vacation_rent)
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

    private fun clickListeners() = with(binding) {
        imageViewNext.setOnClickListener(this@PreviewOrSavePropertyDetailFragment)
        imageViewPrevious.setOnClickListener(this@PreviewOrSavePropertyDetailFragment)
        imageViewMapNavigatorBg.setOnClickListener(this@PreviewOrSavePropertyDetailFragment)
        imageViewMapNavigator.setOnClickListener(this@PreviewOrSavePropertyDetailFragment)
        buttonShare.setOnClickListener(this@PreviewOrSavePropertyDetailFragment)
        buttonEditListing.setOnClickListener(this@PreviewOrSavePropertyDetailFragment)
        buttonPlaceLive.setOnClickListener(this@PreviewOrSavePropertyDetailFragment)
        buttonChat.setOnClickListener(this@PreviewOrSavePropertyDetailFragment)
        callBackFromSelectDateAndTimeBottomsheet()
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
            imageViewMapNavigator, imageViewMapNavigatorBg -> {
                navigator.loadActivity(IsolatedFullActivity::class.java, MapFragment::class.java)
                    .start()
            }
            buttonEditListing -> {
                if(currentTab != PropertyListTag.HOME_FOR_SALE) {
                    val intent = Intent()
                    intent.putExtra("IS_EDIT", true)
                    intent.putExtra("DATA", data)
                    intent.putExtra("CURRENT_TAB", currentTab as Parcelable)
                    requireActivity().setResult(Activity.RESULT_OK, intent)
                }
                navigator.goBack()
                /*when(currentTab) {
                    PropertyListTag.HOME_FOR_SALE -> {
                        navigator.goBack()
                    }
                    else -> {
                        val intent = Intent()
                        intent.putExtra("IS_EDIT", true)
                        intent.putExtra("DATA", data)
                        intent.putExtra("CURRENT_TAB", currentTab as Parcelable)
                        requireActivity().setResult(Activity.RESULT_OK, intent)
                        navigator.goBack()
                    }
                }*/
            }
            buttonPlaceLive -> {
                val intent = Intent()
                intent.putExtra("DATA", data)
                intent.putExtra("CURRENT_TAB", currentTab as Parcelable)
                requireActivity().setResult(Activity.RESULT_OK, intent)
                navigator.goBack()

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
            navigator.loadActivity(IsolatedFullActivity::class.java, RequestFragmentREU::class.java)
                .start()
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

        TextDecorator.decorate(textViewRentDuration, textViewRentDuration.trimmedText)
            .setTypeface(
                ResourcesCompat.getFont(
                    textViewRentDuration.context,
                    R.font.cerebri_sans_regular
                ), getString(R.string.label_rent_duration_dash)
            )
            .setTextColor(R.color.C_808080, getString(R.string.label_rent_duration_dash))
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
        private const val SOURCE_SCREEN = "SOURCE_SCREEN"
        private const val DATA = "DATA"
        private const val CURRENT_TAB = "CURRENT_TAB"
        fun createBundle(
            sourceScreen: String,
            data: RealEstatePropertyList,
            currentTab: PropertyListTag?
        ) =
            bundleOf(
                SOURCE_SCREEN to sourceScreen,
                DATA to data,
                CURRENT_TAB to currentTab
            )
    }
}
