package com.trudeals.ui.home.customeruser.adapter

import android.view.ViewGroup
import com.trudeals.databinding.ItemFeaturesBinding
import com.trudeals.databinding.ItemPropertyImagesBinding
import com.trudeals.ui.base.adavancedrecyclerview.AdvanceRecycleViewAdapter
import com.trudeals.ui.base.adavancedrecyclerview.BaseHolder
import com.trudeals.utils.extension.toBinding

class PropertyImagesAdapter : AdvanceRecycleViewAdapter<PropertyImagesAdapter.ViewHolder, Int>() {
    inner class ViewHolder(val binding: ItemPropertyImagesBinding) : BaseHolder<Int>(binding.root) {
        override fun bind(item: Int) = with(binding) {
            shapeableImage.setImageResource(item)
        }
    }

    override fun createDataHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parent.toBinding())
    }
}