package com.trudeals.ui.isolated.customeruser.adapter

import android.annotation.SuppressLint
import android.view.ViewGroup
import com.trudeals.databinding.ItemPropertyTypeChipBinding
import com.trudeals.ui.base.adavancedrecyclerview.AdvanceRecycleViewAdapter
import com.trudeals.ui.base.adavancedrecyclerview.BaseHolder
import com.trudeals.ui.isolated.dummydata.PropertyTypeChip
import com.trudeals.utils.extension.toBinding

class PropertyTypeAdapter : AdvanceRecycleViewAdapter<PropertyTypeAdapter.ViewHolder, PropertyTypeChip>() {

    inner class ViewHolder(private val binding: ItemPropertyTypeChipBinding) :
        BaseHolder<PropertyTypeChip>(binding.root) {
        override fun bind(item: PropertyTypeChip) = with(binding) {
            textView.text = item.chip
            textView.isSelected = item.isSelected

            textView.setOnClickListener {
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