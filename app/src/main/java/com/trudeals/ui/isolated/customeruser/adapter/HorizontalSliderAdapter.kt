package com.trudeals.ui.isolated.customeruser.adapter

import android.view.ViewGroup
import com.trudeals.databinding.ItemHorizontalSliderBinding
import com.trudeals.ui.base.adavancedrecyclerview.AdvanceRecycleViewAdapter
import com.trudeals.ui.base.adavancedrecyclerview.BaseHolder
import com.trudeals.utils.extension.toBinding

class HorizontalSliderAdapter :
    AdvanceRecycleViewAdapter<HorizontalSliderAdapter.ViewHolder, Int>() {

    inner class ViewHolder(private val binding: ItemHorizontalSliderBinding) :
        BaseHolder<Int>(binding.root) {
        override fun bind(item: Int) = with(binding) {
            imageView.setImageResource(item)
        }
    }

    override fun createDataHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parent.toBinding())
    }
}