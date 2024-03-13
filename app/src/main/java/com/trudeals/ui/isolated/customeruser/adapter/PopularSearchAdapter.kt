package com.trudeals.ui.isolated.customeruser.adapter

import android.view.ViewGroup
import com.trudeals.databinding.ItemPopularSearchChipBinding
import com.trudeals.ui.base.adavancedrecyclerview.AdvanceRecycleViewAdapter
import com.trudeals.ui.base.adavancedrecyclerview.BaseHolder
import com.trudeals.utils.extension.toBinding

class PopularSearchAdapter : AdvanceRecycleViewAdapter<PopularSearchAdapter.ViewHolder, String>() {

    inner class ViewHolder(private val binding: ItemPopularSearchChipBinding) :
        BaseHolder<String>(binding.root) {
        override fun bind(item: String) = with(binding) {
            textView.text = item

            textView.setOnClickListener {
                onClickPositionListener?.invoke(item, adapterPosition)
            }
        }
    }

    override fun createDataHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parent.toBinding())
    }
}