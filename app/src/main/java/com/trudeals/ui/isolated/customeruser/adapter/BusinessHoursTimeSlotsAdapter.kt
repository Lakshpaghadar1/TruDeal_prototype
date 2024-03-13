package com.trudeals.ui.isolated.customeruser.adapter

import android.view.ViewGroup
import com.trudeals.databinding.ItemBusinessHoursTimeSlotsBinding
import com.trudeals.ui.base.adavancedrecyclerview.AdvanceRecycleViewAdapter
import com.trudeals.ui.base.adavancedrecyclerview.BaseHolder
import com.trudeals.utils.extension.toBinding

class BusinessHoursTimeSlotsAdapter :
    AdvanceRecycleViewAdapter<BusinessHoursTimeSlotsAdapter.ViewHolder, String>() {

    inner class ViewHolder(val binding: ItemBusinessHoursTimeSlotsBinding) :
        BaseHolder<String>(binding.root) {
        override fun bind(item: String) = with(binding) {
            textViewBusinessHours.text = item
        }
    }

    override fun createDataHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parent.toBinding())
    }
}