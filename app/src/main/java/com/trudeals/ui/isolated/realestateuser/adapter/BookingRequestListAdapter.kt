package com.trudeals.ui.isolated.realestateuser.adapter

import android.view.ViewGroup
import com.trudeals.R
import com.trudeals.databinding.ItemBookingRequestRowBinding
import com.trudeals.ui.base.adavancedrecyclerview.AdvanceRecycleViewAdapter
import com.trudeals.ui.base.adavancedrecyclerview.BaseHolder
import com.trudeals.ui.isolated.dummydata.RequestList
import com.trudeals.utils.*
import com.trudeals.utils.extension.isVisible
import com.trudeals.utils.extension.toBinding

class BookingRequestListAdapter :
    AdvanceRecycleViewAdapter<BookingRequestListAdapter.ViewHolder, RequestList>() {

    private var onClickOfItemView: ((item: RequestList, position: Int, onClick: OnClick) -> Unit)? = null
    private var selectedRequestType: RequestCategoryType = RequestCategoryType.REQUESTED

    inner class ViewHolder(val binding: ItemBookingRequestRowBinding) :
        BaseHolder<RequestList>(binding.root) {
        override fun bind(item: RequestList) = with(binding) {
            setData(item)
            setStatus(item)
            setVisibility(selectedRequestType)
            clickListener(item)
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

        private fun setVisibility(selectedRequest: RequestCategoryType) = with(binding) {
            when(selectedRequest) {
                RequestCategoryType.REQUESTED -> {
                    setButtons(isChatButtonVisible = false)
                }
                RequestCategoryType.ACCEPTED -> {
                    setButtons(isChatButtonVisible = true)
                }
                RequestCategoryType.COMPLETED -> {
                    hideView(constraintLayoutNavButtons)
                }
            }
        }

        private fun setButtons(isChatButtonVisible: Boolean = false) = with(binding) {
            showView(constraintLayoutNavButtons)
            buttonNegative.isVisible(!isChatButtonVisible)
            buttonPositive.isVisible(!isChatButtonVisible)
            buttonModifyRequest.isVisible(!isChatButtonVisible)
            buttonSchedule.isVisible(false)
            buttonEditSchedule.isVisible(false)
            buttonChat.isVisible(isChatButtonVisible)
        }

        private fun clickListener(item: RequestList) = with(binding) {
            cardView.setOnClickListener {
                onClickPositionListener?.invoke(item, bindingAdapterPosition)
            }
            buttonModifyRequest.setOnClickListener {
                onClickOfItemView?.invoke(item, bindingAdapterPosition, OnClick.MODIFY_REQUEST)
            }
            buttonNegative.setOnClickListener {
                onClickOfItemView?.invoke(item, bindingAdapterPosition, OnClick.CANCEL)
            }
            buttonPositive.setOnClickListener {
                onClickOfItemView?.invoke(item, bindingAdapterPosition, OnClick.ACCEPT)
            }
            buttonChat.setOnClickListener {
                onClickOfItemView?.invoke(item, bindingAdapterPosition, OnClick.CHAT)
            }
        }
    }

    override fun createDataHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parent.toBinding())
    }

    fun getSelectedRequestType(selectedRequestType: RequestCategoryType) {
        this.selectedRequestType = selectedRequestType
    }

    fun setOnClickOfItemView(onClickOfItemView: (item: RequestList, position: Int, onClick: OnClick) -> Unit) {
        this.onClickOfItemView = onClickOfItemView
    }
}