package com.trudeals.ui.isolated.realestateuser.adapter

import android.view.ViewGroup
import com.trudeals.R
import com.trudeals.databinding.ItemRealEstateHomeRowBinding
import com.trudeals.ui.base.adavancedrecyclerview.AdvanceRecycleViewAdapter
import com.trudeals.ui.base.adavancedrecyclerview.BaseHolder
import com.trudeals.ui.home.dummydata.RealEstateHomeListItem
import com.trudeals.utils.*
import com.trudeals.utils.extension.toBinding

class MyFavListAdapterREU() :
    AdvanceRecycleViewAdapter<MyFavListAdapterREU.ViewHolder, RealEstateHomeListItem>() {

    private var onClickOfView: ((item: RealEstateHomeListItem, position: Int, onClick: OnClick) -> Unit)? =
        null

    inner class ViewHolder(private val binding: ItemRealEstateHomeRowBinding) :
        BaseHolder<RealEstateHomeListItem>(binding.root) {

        override fun bind(item: RealEstateHomeListItem): Unit = with(binding) {
            setData(item)
            setUpAmenities(item)
            setStatus(item)
            setUpCLickListener(item)
        }

        private fun setData(item: RealEstateHomeListItem) = with(binding) {
            item.image?.let { imageView.setImageResource(it) }
            textViewTitle.text = item.title
            textViewLocation.text = item.location
            textViewAmount.text = "$".plus(item.amount)
            imageViewHeart.isSelected = item.isLiked
        }

        private fun setUpAmenities(item: RealEstateHomeListItem) = with(binding.layoutAmenities) {
            item.numberOfAmenities?.apply {
                textViewNoOfBeds.text =
                    context.getString(R.string.label_x_beds, numberOfBeds)
                textViewNoOfBath.text =
                    context.getString(R.string.label_x_bath, numberOfBath)
                textViewNoOfGarage.text =
                    context.getString(R.string.label_x_garage, numberOfGarage)
                textViewNoOfSqFt.text =
                    context.getString(R.string.label_x_sq_ft, numberOfSqFt)
            }
        }

        private fun setUpCLickListener(item: RealEstateHomeListItem) = with(binding) {
            cardView.setOnClickListener {
                onClickPositionListener?.invoke(item, bindingAdapterPosition)
            }

            imageViewHeart.setOnClickListener {
                imageViewHeart.isSelected = !imageViewHeart.isSelected
                item.isLiked = imageViewHeart.isSelected
                onClickOfView?.invoke(item, bindingAdapterPosition, OnClick.HEART)
            }
        }

        private fun setStatus(item: RealEstateHomeListItem) = with(binding.layoutStatus) {
            when (item.status) {
                StatusType.FOR_SALE -> {
                    showView(textViewForSale)
                    textViewForSale.text = getString(R.string.label_for_sale)
                }
                StatusType.FOR_RENT -> {
                    showView(textViewForSale)
                    textViewForSale.text = getString(R.string.label_for_rent)
                }
                StatusType.FOR_VACATION_RENT -> {
                    showView(textViewForSale)
                    textViewForSale.text = getString(R.string.label_for_vacation_rent)
                }
                StatusType.NONE -> {
                    hideView(linearLayoutStatus)
                }
                else -> {}
            }
        }
    }

    override fun createDataHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parent.toBinding())
    }

    fun setOnClickOfView(onClickOfView: (item: RealEstateHomeListItem, position: Int, onClick: OnClick) -> Unit) {
        this.onClickOfView = onClickOfView
    }
}