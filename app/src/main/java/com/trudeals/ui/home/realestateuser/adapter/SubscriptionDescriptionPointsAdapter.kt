package com.trudeals.ui.home.realestateuser.adapter

import android.view.ViewGroup
import com.trudeals.databinding.ItemSubscriptionDescriptionPointsBinding
import com.trudeals.ui.base.adavancedrecyclerview.AdvanceRecycleViewAdapter
import com.trudeals.ui.base.adavancedrecyclerview.BaseHolder
import com.trudeals.utils.extension.toBinding

class SubscriptionDescriptionPointsAdapter :
    AdvanceRecycleViewAdapter<SubscriptionDescriptionPointsAdapter.ViewHolder, String>() {

    inner class ViewHolder(val binding: ItemSubscriptionDescriptionPointsBinding) :
        BaseHolder<String>(binding.root) {
        override fun bind(item: String) = with(binding) {
            textViewDescription.text = item
        }
    }

    override fun createDataHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parent.toBinding())
    }
}