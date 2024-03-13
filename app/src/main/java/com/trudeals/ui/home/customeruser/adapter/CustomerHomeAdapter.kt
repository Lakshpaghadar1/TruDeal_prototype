package com.trudeals.ui.home.customeruser.adapter

import android.view.ViewGroup
import com.trudeals.R
import com.trudeals.databinding.ItemCustomerHomeRowBinding
import com.trudeals.ui.base.adavancedrecyclerview.AdvanceRecycleViewAdapter
import com.trudeals.ui.base.adavancedrecyclerview.BaseHolder
import com.trudeals.ui.home.dummydata.CustomerHomeListItem
import com.trudeals.utils.*
import com.trudeals.utils.extension.toBinding

class CustomerHomeAdapter(
    private val currentTab: TabType,
    private val mainCategory: MainCategoryType
) : AdvanceRecycleViewAdapter<CustomerHomeAdapter.ViewHolder, CustomerHomeListItem>() {

    var subCategory = SubCategoryType.NONE
    private var onClickOfView: ((item: CustomerHomeListItem, position: Int, onClick: OnClick) -> Unit)? =
        null

    inner class ViewHolder(private val binding: ItemCustomerHomeRowBinding) :
        BaseHolder<CustomerHomeListItem>(binding.root) {

        override fun bind(item: CustomerHomeListItem): Unit = with(binding) {
            setData(item)

            setUpAmenities(item)
            setViewsVisibility()
            setStatus(item)
            setUpCLickListener(item)
        }

        private fun setData(item: CustomerHomeListItem) = with(binding) {
            item.image?.let { imageView.setImageResource(it) }
            textViewTitle.text = item.title
            textViewLocation.text = item.location
            textViewAmount.text = "$".plus(item.amount)
            imageViewHeart.isSelected = item.isLiked

        }

        private fun setUpAmenities(item: CustomerHomeListItem) = with(binding.layoutAmenities) {
            item.numberOfAmenities?.apply {
                textViewNoOfBeds.text =
                    context.getString(R.string.label_x_beds, numberOfBeds)
                textViewNoOfBath.text =
                    context.getString(R.string.label_x_bath, numberOfBath)
                textViewNoOfGarage.text =
                    context.getString(R.string.label_x_garage, numberOfGarage)
                textViewNoOfSqFt.text =
                    context.getString(R.string.label_x_sq_ft, numberOfSqFt)
                hideView(binding.textViewDescription)
                showView(binding.layoutAmenities.root)
            } ?: run {
                binding.textViewDescription.text = item.description
                hideView(binding.layoutAmenities.root)
                showView(binding.textViewDescription)
            }
        }

        private fun setUpCLickListener(item: CustomerHomeListItem) = with(binding) {
            cardView.setOnClickListener {
                onClickPositionListener?.invoke(item, bindingAdapterPosition)
            }
            imageViewHeart.setOnClickListener {
                imageViewHeart.isSelected = !imageViewHeart.isSelected
                item.isLiked = imageViewHeart.isSelected
                onClickOfView?.invoke(item, bindingAdapterPosition, OnClick.HEART)
            }

            textViewContact.setOnClickListener {
                onClickOfView?.invoke(item, bindingAdapterPosition, OnClick.CONTACT)
            }
        }

        private fun setStatus(item: CustomerHomeListItem) = with(binding) {
            when (item.status) {
                StatusType.FEATURED -> {
                    hideView(layoutStatus.textViewForSale)
                    showView(layoutStatus.textViewFeatured)
                }
                StatusType.FOR_SALE -> {
                    showView(layoutStatus.textViewForSale)
                    layoutStatus.textViewForSale.text = getString(R.string.label_for_sale)
                }
                StatusType.FOR_RENT -> {
                    showView(layoutStatus.textViewForSale)
                    layoutStatus.textViewForSale.text = getString(R.string.label_for_rent)
                }
                StatusType.DISCOUNT -> {
                    hideView(layoutStatus.textViewForSale)
                    showView(layoutStatus.textViewDiscount)
                }
                StatusType.FEATURED_AND_FORSALE -> {
                    showView(layoutStatus.textViewFeatured, layoutStatus.textViewForSale)
                    layoutStatus.textViewForSale.text = getString(R.string.label_for_sale)
                }
                StatusType.FOR_VACATION_RENT -> {
                    showView(layoutStatus.textViewForSale)
                    layoutStatus.textViewForSale.text = getString(R.string.label_for_vacation_rent)
                }
                StatusType.NONE -> {
                    hideView(layoutStatus.linearLayoutStatus)
                }
                else -> {}
            }
        }

        private fun setViewsVisibility() = with(binding) {
            when (mainCategory) {
                MainCategoryType.REAL_ESTATE -> {
                    showView(layoutAmenities.root)
                    hideView(textViewDescription)
                }
                MainCategoryType.LOCAL_DEALS -> {
                    showView(layoutStatus.linearLayoutStatus, textViewDescription)
                    hideView(layoutAmenities.root)
                }
                MainCategoryType.BOTH -> {
                    when (subCategory) {
                        SubCategoryType.DEALS_AND_REAL_ESTATE -> {
                            showView(
                                layoutStatus.linearLayoutStatus,
                                textViewDescription,
                                layoutAmenities.root
                            )
                        }
                        SubCategoryType.REAL_ESTATE -> {
                            showView(layoutAmenities.root)
                            hideView(textViewDescription)
                        }
                        SubCategoryType.DEALS -> {
                            showView(layoutStatus.linearLayoutStatus, textViewDescription)
                            hideView(layoutAmenities.root)
                        }
                        else -> {}
                    }

                }
            }

            when (currentTab) {
                TabType.ALL -> {
                    showView(layoutStatus.linearLayoutStatus)
                }
                TabType.HOME_FOR_SALE -> {
                    hideView(layoutStatus.linearLayoutStatus)
                }
                TabType.RENTALS -> {
                    hideView(layoutStatus.linearLayoutStatus)
                }
                TabType.VACATION_RENTALS -> {
                    hideView(layoutStatus.linearLayoutStatus)
                }
                TabType.AUTOMOTIVE -> {

                }
                TabType.BEAUTY_AND_SPA -> {

                }
                TabType.FOOD_AND_DRINK -> {

                }
                TabType.HEALTH_AND_FITNESS -> {

                }
                TabType.HOME_AND_GARDEN -> {

                }
                TabType.MEDICAL_SERVICE -> {

                }
                TabType.SERVICES -> {

                }
                TabType.THINGS_TO_DO -> {

                }
                TabType.TRAVEL -> {

                }
                TabType.SALON -> {

                }
                TabType.NONE -> {

                }
            }
        }
    }

    override fun createDataHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parent.toBinding())
    }

    fun setOnClickOfView(onClickOfView: (item: CustomerHomeListItem, position: Int, onClick: OnClick) -> Unit) {
        this.onClickOfView = onClickOfView
    }
}