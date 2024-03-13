package com.trudeals.ui.isolated.businessuser.adapter

import android.view.ViewGroup
import com.trudeals.databinding.ItemBusinessDealButtonsBinding
import com.trudeals.ui.base.adavancedrecyclerview.AdvanceRecycleViewAdapter
import com.trudeals.ui.base.adavancedrecyclerview.BaseHolder
import com.trudeals.ui.isolated.dummydata.BusinessDealButton
import com.trudeals.ui.isolated.dummydata.SelectTimeSlots
import com.trudeals.utils.OnClick
import com.trudeals.utils.extension.toBinding

class BusinessDealButtonsAdapter :
    AdvanceRecycleViewAdapter<BusinessDealButtonsAdapter.ViewHolder, BusinessDealButton>() {

    private var onCLickOfView: ((item: SelectTimeSlots, subPosition: Int, onClick: OnClick) -> Unit)? =
        null

    inner class ViewHolder(private val binding: ItemBusinessDealButtonsBinding) :
        BaseHolder<BusinessDealButton>(binding.root) {

        override fun bind(item: BusinessDealButton) = with(binding) {
            imageViewIcon.setImageResource(item.icon)
            textViewTitle.text = item.title

            root.setOnClickListener {
                onClickPositionListener?.invoke(item, bindingAdapterPosition)
            }
        }
    }

    override fun createDataHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parent.toBinding())
    }

    fun setOnClickOfView(onCLickOfView: (item: SelectTimeSlots, subPosition: Int, onClick: OnClick) -> Unit) {
        this.onCLickOfView = onCLickOfView
    }
}