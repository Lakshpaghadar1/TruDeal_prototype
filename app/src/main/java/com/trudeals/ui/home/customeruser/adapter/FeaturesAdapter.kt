package com.trudeals.ui.home.customeruser.adapter

import android.view.ViewGroup
import com.trudeals.databinding.ItemFeaturesBinding
import com.trudeals.ui.base.adavancedrecyclerview.AdvanceRecycleViewAdapter
import com.trudeals.ui.base.adavancedrecyclerview.BaseHolder
import com.trudeals.utils.extension.toBinding

class FeaturesAdapter : AdvanceRecycleViewAdapter<FeaturesAdapter.ViewHolder, String>() {
    inner class ViewHolder(val binding: ItemFeaturesBinding) : BaseHolder<String>(binding.root) {
        override fun bind(item: String) = with(binding) {
            textViewFeature.text = item
        }
    }

    override fun createDataHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parent.toBinding())
    }
}