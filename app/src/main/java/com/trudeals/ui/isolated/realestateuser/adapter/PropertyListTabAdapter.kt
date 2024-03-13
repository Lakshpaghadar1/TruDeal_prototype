package com.trudeals.ui.isolated.realestateuser.adapter

import android.annotation.SuppressLint
import android.view.ViewGroup
import com.trudeals.databinding.ItemPropertyListTabBinding
import com.trudeals.ui.base.adavancedrecyclerview.AdvanceRecycleViewAdapter
import com.trudeals.ui.base.adavancedrecyclerview.BaseHolder
import com.trudeals.ui.isolated.dummydata.PropertyListTab
import com.trudeals.utils.extension.toBinding

class PropertyListTabAdapter :
    AdvanceRecycleViewAdapter<PropertyListTabAdapter.ViewHolder, PropertyListTab>() {

    inner class ViewHolder(val binding: ItemPropertyListTabBinding) :
        BaseHolder<PropertyListTab>(binding.root) {
        override fun bind(item: PropertyListTab) = with(binding) {
            buttonTab.text = item.tab
            buttonTab.isSelected = item.isSelected

            buttonTab.setOnClickListener {
                buttonTab.isSelected = !buttonTab.isSelected
                onClickPositionListener?.invoke(item, bindingAdapterPosition)
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