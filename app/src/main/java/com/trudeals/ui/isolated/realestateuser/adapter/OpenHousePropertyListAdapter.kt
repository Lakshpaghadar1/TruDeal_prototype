package com.trudeals.ui.isolated.realestateuser.adapter

import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import com.trudeals.R
import com.trudeals.databinding.ItemBookingRequestRowBinding
import com.trudeals.ui.base.adavancedrecyclerview.AdvanceRecycleViewAdapter
import com.trudeals.ui.base.adavancedrecyclerview.BaseHolder
import com.trudeals.ui.isolated.dummydata.RequestList
import com.trudeals.utils.OnClick
import com.trudeals.utils.StatusType
import com.trudeals.utils.extension.setIcon
import com.trudeals.utils.extension.toBinding
import com.trudeals.utils.hideView
import com.trudeals.utils.showView

class OpenHousePropertyListAdapter :
    AdvanceRecycleViewAdapter<OpenHousePropertyListAdapter.ViewHolder, RequestList>() {

    private var onClickOfItemView: ((item: RequestList, position: Int, onClick: OnClick) -> Unit)? = null
    inner class ViewHolder(val binding: ItemBookingRequestRowBinding) :
        BaseHolder<RequestList>(binding.root) {
        override fun bind(item: RequestList) = with(binding) {
            setData(item)
            setStatus(item)
            setCloseDealView(item)
            clickListener(item)
            setButtons(item)
        }

        private fun setData(item: RequestList) = with(binding) {
            textViewName.text = item.userName
            textViewEmail.text = item.userEmail
            textViewPhoneNumber.text = item.userPhoneNumber
            textViewDateAndTime.text =
                context.getString(R.string.label_time_x, item.time)

            item.notes?.let {
                showView(imageViewNotes, textViewNotes)
                textViewNotes.text = it
            } ?: hideView(imageViewNotes, textViewNotes)

            imageViewPropertyImage.setImageResource(item.propertyImage)
            textViewPropertyType.text = item.propertyType
            textViewPropertyLocation.text = item.propertyLocation
        }

        private fun setStatus(item: RequestList) = with(binding.layoutStatus) {
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

        private fun setButtons(item: RequestList) = with(binding) {
            hideView(buttonModifyRequest, buttonNegative, buttonPositive, buttonChat)
            if (item.isScheduled) {
                hideView(buttonSchedule)
                showView(buttonEditSchedule)
            } else {
                hideView(buttonEditSchedule)
                showView(buttonSchedule)
            }
        }

        private fun setCloseDealView(item: RequestList) = with(binding) {
            if (item.isDealClosed) {
                layoutStatus.textViewForSale.background = ResourcesCompat.getDrawable(
                    context.resources,
                    R.drawable.bg_grey_round_corner_status, null
                )
                textViewName.setIcon(R.drawable.ic_user_grey)
                textViewEmail.setIcon(R.drawable.ic_email_grey)
                textViewPhoneNumber.setIcon(R.drawable.ic_phone_grey)
                textViewDateAndTime.setIcon(R.drawable.ic_clock_grey)
                imageViewNotes.setImageResource(R.drawable.ic_notes_grey)
            } else {
                layoutStatus.textViewForSale.background = ResourcesCompat.getDrawable(
                    context.resources,
                    R.drawable.bg_orange_round_corner_status, null
                )
                textViewName.setIcon(R.drawable.ic_user)
                textViewEmail.setIcon(R.drawable.ic_email)
                textViewPhoneNumber.setIcon(R.drawable.ic_phone)
                textViewDateAndTime.setIcon(R.drawable.ic_clock_orange)
                imageViewNotes.setImageResource(R.drawable.ic_notes)
            }
        }

        private fun clickListener(item: RequestList) = with(binding) {
            cardView.setOnClickListener {
                onClickPositionListener?.invoke(item, bindingAdapterPosition)
            }

            buttonSchedule.setOnClickListener {
                onClickOfItemView?.invoke(item, bindingAdapterPosition, OnClick.SCHEDULE)
            }

            buttonEditSchedule.setOnClickListener {
                onClickOfItemView?.invoke(item, bindingAdapterPosition, OnClick.EDIT_SCHEDULE)
            }
        }
    }

    override fun createDataHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parent.toBinding())
    }

    fun setOnClickOfItemView(onClickOfItemView: (item: RequestList, position: Int, onClick: OnClick) -> Unit) {
        this.onClickOfItemView = onClickOfItemView
    }
}