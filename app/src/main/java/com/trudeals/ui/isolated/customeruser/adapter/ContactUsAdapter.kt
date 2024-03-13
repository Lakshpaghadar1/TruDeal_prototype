package com.trudeals.ui.isolated.customeruser.adapter

import android.view.ViewGroup
import com.trudeals.databinding.ItemContactUsBinding
import com.trudeals.ui.base.adavancedrecyclerview.AdvanceRecycleViewAdapter
import com.trudeals.ui.base.adavancedrecyclerview.BaseHolder
import com.trudeals.ui.isolated.dummydata.ContactUs
import com.trudeals.utils.extension.isVisible
import com.trudeals.utils.extension.toBinding

class ContactUsAdapter : AdvanceRecycleViewAdapter<ContactUsAdapter.ViewHolder, ContactUs>() {

    inner class ViewHolder(private val binding: ItemContactUsBinding) :
        BaseHolder<ContactUs>(binding.root) {
        override fun bind(item: ContactUs) = with(binding) {
            imageViewIcon.setImageResource(item.icon)
            textViewDetail.text = item.detail
            textViewDetailPhone.text = item.detail

            textViewDetailPhone.isVisible(item.isTextUnderlineSpan)
            textViewDetail.isVisible(!item.isTextUnderlineSpan)

            constraintLayout.setOnClickListener {
                onClickPositionListener?.invoke(item, bindingAdapterPosition)
            }
        }
    }

    override fun createDataHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parent.toBinding())
    }

}