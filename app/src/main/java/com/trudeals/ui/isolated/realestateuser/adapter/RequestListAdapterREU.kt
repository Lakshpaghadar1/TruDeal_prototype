package com.trudeals.ui.isolated.realestateuser.adapter

import android.view.ViewGroup
import com.trudeals.R
import com.trudeals.databinding.ItemRealEstateRequestRowBinding
import com.trudeals.ui.base.adavancedrecyclerview.AdvanceRecycleViewAdapter
import com.trudeals.ui.base.adavancedrecyclerview.BaseHolder
import com.trudeals.ui.isolated.dummydata.RealEstateRequestList
import com.trudeals.utils.*
import com.trudeals.utils.extension.toBinding

class RequestListAdapterREU :
    AdvanceRecycleViewAdapter<RequestListAdapterREU.ViewHolder, RealEstateRequestList>() {

    private var onClickOfItemView: ((item: RealEstateRequestList, position: Int, onClick: OnClick) -> Unit)? =
        null

    inner class ViewHolder(val binding: ItemRealEstateRequestRowBinding) :
        BaseHolder<RealEstateRequestList>(binding.root) {
        override fun bind(item: RealEstateRequestList) = with(binding) {
            setData(item)
            setStatus(item)
            setButtonStatus(item)
            clickListener(item)
        }

        private fun setData(item: RealEstateRequestList) = with(binding) {
            textViewName.text = item.userName
            hideView(textViewEmail)
            //textViewEmail.text = item.userEmail
            textViewPhoneNumber.text = item.userPhoneNumber
            textViewDateAndTime.text =
                context.getString(R.string.label_date_x_coma_time_x, item.date, item.time)

            item.notes?.let {
                showView(imageViewNotes, textViewNotes)
                textViewNotes.text = it
            } ?: hideView(imageViewNotes, textViewNotes)

            imageViewPropertyImage.setImageResource(item.propertyImage)
            textViewPropertyType.text = item.propertyType
            textViewPropertyLocation.text = item.propertyLocation
        }

        private fun setStatus(item: RealEstateRequestList) = with(binding.layoutStatus) {
            when (item.status) {
                StatusType.FOR_SALE -> {
                    showView(textViewForSale)
                    textViewForSale.text = getString(R.string.label_for_sale)
                }
                StatusType.FEATURED -> {
                    showView(textViewFeatured)
                }
                StatusType.FOR_RENT -> {
                    showView(textViewForSale)
                    textViewForSale.text = getString(R.string.label_for_rent)
                }
                StatusType.DISCOUNT -> {
                    showView(textViewDiscount)
                }
                StatusType.FEATURED_AND_FORSALE -> {
                    showView(textViewFeatured, textViewForSale)
                    textViewForSale.text = getString(R.string.label_for_sale)
                }
                StatusType.FOR_VACATION_RENT -> {
                    showView(textViewForSale)
                    textViewForSale.text = getString(R.string.label_for_vacation_rent)
                }
                StatusType.NONE -> {
                    hideView(this.root)
                }
            }
        }

        private fun setButtonStatus(item: RealEstateRequestList) = with(binding) {
            when (item.buttonStatus) {
                ButtonStatus.REJECTED -> {
                    defaultHideEachButton()
                    showView(buttonRejected)
                }
                ButtonStatus.ACCEPTED -> {
                    defaultHideEachButton()
                    showView(buttonAccepted)
                }
                else -> {}
            }
        }

        private fun defaultHideEachButton() = with(binding) {
            hideView(buttonNegative, buttonPositive, buttonRejected, buttonAccepted)
        }

        private fun clickListener(item: RealEstateRequestList) = with(binding) {
            cardView.setOnClickListener {
                onClickPositionListener?.invoke(item, bindingAdapterPosition)
            }
            buttonNegative.setOnClickListener {
                onClickOfItemView?.invoke(item, bindingAdapterPosition, OnClick.REJECT)
            }
            buttonPositive.setOnClickListener {
                onClickOfItemView?.invoke(item, bindingAdapterPosition, OnClick.ACCEPT)
            }
        }
    }

    override fun createDataHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parent.toBinding())
    }

    fun setOnClickOfItemView(onClickOfItemView: (item: RealEstateRequestList, position: Int, onClick: OnClick) -> Unit) {
        this.onClickOfItemView = onClickOfItemView
    }
}