package com.trudeals.ui.home.realestateuser.adapter

import android.annotation.SuppressLint
import android.view.ViewGroup
import com.trudeals.R
import com.trudeals.databinding.ItemRealEstateTabsRowBinding
import com.trudeals.ui.base.adavancedrecyclerview.AdvanceRecycleViewAdapter
import com.trudeals.ui.base.adavancedrecyclerview.BaseHolder
import com.trudeals.ui.home.dummydata.RealEstateTabREU
import com.trudeals.utils.extension.isVisible
import com.trudeals.utils.extension.toBinding
import com.trudeals.utils.extension.trimmedText
import com.trudeals.utils.textdecorator.TextDecorator

class RealEstateTabsAdapter :
    AdvanceRecycleViewAdapter<RealEstateTabsAdapter.ViewHolder, RealEstateTabREU>() {

    inner class ViewHolder(val binding: ItemRealEstateTabsRowBinding) :
        BaseHolder<RealEstateTabREU>(binding.root) {
        override fun bind(item: RealEstateTabREU) = with(binding) {
            textViewTab.text = item.title
            textViewTab.isSelected = item.isSelected

            if (textViewTab.isSelected) {
                TextDecorator.decorate(textViewTab, textViewTab.trimmedText)
                  .setTextAppearance(R.style.Bold_8ssp_C_ED1D26, textViewTab.trimmedText)
                    .build()
            }
             else {
                TextDecorator.decorate(textViewTab, textViewTab.trimmedText)
                    .setTextAppearance(R.style.Regular_9ssp_C_9D9D9D, textViewTab.trimmedText)
                    .build()
            }

            root.setOnClickListener {
                item.isSelected = !item.isSelected
                onClickPositionListener?.invoke(item, bindingAdapterPosition)
            }
            view.isVisible(item.isSelected)
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