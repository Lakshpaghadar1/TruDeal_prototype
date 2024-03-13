package com.trudeals.ui.isolated.realestateuser.adapter

import android.view.ViewGroup
import com.trudeals.R
import com.trudeals.databinding.ItemRealEstatePropertyDetailsRowBinding
import com.trudeals.ui.base.adavancedrecyclerview.AdvanceRecycleViewAdapter
import com.trudeals.ui.base.adavancedrecyclerview.BaseHolder
import com.trudeals.ui.isolated.dummydata.RealEstatePropertyList
import com.trudeals.utils.*
import com.trudeals.utils.extension.isVisible
import com.trudeals.utils.extension.toBinding

class RealEstatePropertyDetailsAdapter :
    AdvanceRecycleViewAdapter<RealEstatePropertyDetailsAdapter.ViewHolder, RealEstatePropertyList>() {

    private var onCLickOfView: ((item: RealEstatePropertyList, position: Int, onClick: OnClick) -> Unit)? =
        null

    inner class ViewHolder(val binding: ItemRealEstatePropertyDetailsRowBinding) :
        BaseHolder<RealEstatePropertyList>(binding.root) {
        override fun bind(item: RealEstatePropertyList) = with(binding) {
            //imageView.loadImageFromServerAny(item.listOfImages[0])
            imageView.setImageResource(item.image)
            textViewTitle.text = item.title
            textViewLocation.text = item.address
            setUpAmenities(item)
            setStatus(item)
            textViewAmount.text = context.getString(R.string.label_dollar_x, item.price)

            root.setOnClickListener {
                onClickPositionListener?.invoke(item, bindingAdapterPosition)
            }
            imageViewEdit.setOnClickListener {
                onCLickOfView?.invoke(item, bindingAdapterPosition, OnClick.EDIT)
            }
            imageViewDelete.setOnClickListener {
                onCLickOfView?.invoke(item, bindingAdapterPosition, OnClick.DELETE)
            }
        }

        private fun setUpAmenities(item: RealEstatePropertyList) = with(binding.layoutAmenities) {
            item.amenities.apply {
                textViewNoOfBeds.isVisible(numberOfBeds != null)
                textViewNoOfBath.isVisible(numberOfBath != null)
                //textViewNoOfGarage.isVisible(numberOfGarage != null)
                textViewNoOfSqFt.isVisible(numberOfSqFt != null)
                //textViewBuiltYear.isVisible(item.builtYear.isNotEmpty())
                hideView(textViewNoOfGarage, textViewBuiltYear)

                textViewNoOfBeds.text =
                    context.getString(R.string.label_x_beds, numberOfBeds)
                textViewNoOfBath.text =
                    context.getString(R.string.label_x_bath, numberOfBath)
                textViewNoOfGarage.text =
                    context.getString(R.string.label_x_garage, numberOfGarage)
                textViewNoOfSqFt.text =
                    context.getString(R.string.label_x_sq_ft, numberOfSqFt)
                textViewBuiltYear.text =
                    context.getString(R.string.label_built_in_year_x, item.builtYear)
            }
        }

        private fun setStatus(item: RealEstatePropertyList) = with(binding) {
            when (item.status) {
                StatusType.FEATURED -> {
                    hideView(layoutStatus.textViewForSale)
                    showView(layoutStatus.textViewFeatured)
                }
                StatusType.FOR_SALE -> {
                    showView(layoutStatus.textViewForSale)
                    layoutStatus.textViewForSale.text =
                        getString(R.string.label_for_sale)
                }
                StatusType.FOR_RENT -> {
                    showView(layoutStatus.textViewForSale)
                    layoutStatus.textViewForSale.text =
                        getString(R.string.label_for_rent)
                }
                StatusType.DISCOUNT -> {
                    hideView(layoutStatus.textViewForSale)
                    showView(layoutStatus.textViewDiscount)
                }
                StatusType.FEATURED_AND_FORSALE -> {
                    showView(layoutStatus.textViewFeatured, layoutStatus.textViewForSale)
                    layoutStatus.textViewForSale.text =
                        getString(R.string.label_for_sale)
                }
                StatusType.FOR_VACATION_RENT -> {
                    showView(layoutStatus.textViewForSale)
                    layoutStatus.textViewForSale.text =
                        getString(R.string.label_for_vacation_rent)
                }
                StatusType.NONE -> {
                    hideView(layoutStatus.linearLayoutStatus)
                }
            }
        }
    }

    override fun createDataHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parent.toBinding())
    }

    fun setOnClickOfView(onCLickOfView: (item: RealEstatePropertyList, position: Int, onClick: OnClick) -> Unit) {
        this.onCLickOfView = onCLickOfView
    }
}