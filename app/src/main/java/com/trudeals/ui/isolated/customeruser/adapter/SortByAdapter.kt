package com.trudeals.ui.isolated.customeruser.adapter

import android.annotation.SuppressLint
import android.view.ViewGroup
import com.trudeals.databinding.ItemSortByBinding
import com.trudeals.ui.base.adavancedrecyclerview.AdvanceRecycleViewAdapter
import com.trudeals.ui.base.adavancedrecyclerview.BaseHolder
import com.trudeals.ui.isolated.dummydata.SortBy
import com.trudeals.utils.extension.toBinding

class SortByAdapter: AdvanceRecycleViewAdapter<SortByAdapter.ViewHolder, SortBy>() {

    inner class ViewHolder(val binding: ItemSortByBinding) :
        BaseHolder<SortBy>(binding.root) {
        override fun bind(item: SortBy) = with(binding) {
            textView.text = item.option
            textView.isSelected = item.isSelected

            textView.setOnClickListener {
                textView.isSelected = !textView.isSelected
                onClickPositionListener?.invoke(item, adapterPosition)
            }
        }
    }

    override fun createDataHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parent.toBinding())
    }

    @SuppressLint("NotifyDataSetChanged")
    fun changeSelection(position: Int, isSingleSelected: Boolean) {
        for (i in items!!.indices) {
            if (i == position) {
                if (isSingleSelected) {
                    items!![position].isSelected = true
                } else {
                    items!![position].isSelected =
                        !items!![position].isSelected
                }
            } else {
                if (isSingleSelected) {
                    items!![i].isSelected = false
                }
            }
        }
        if (isSingleSelected) {
            notifyDataSetChanged()
        } else {
            notifyItemChanged(position)
        }
    }
}