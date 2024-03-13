package com.trudeals.ui.isolated.customeruser.adapter

import android.annotation.SuppressLint
import android.view.ViewGroup
import com.trudeals.databinding.ItemLivingStatusBinding
import com.trudeals.ui.base.adavancedrecyclerview.AdvanceRecycleViewAdapter
import com.trudeals.ui.base.adavancedrecyclerview.BaseHolder
import com.trudeals.ui.isolated.dummydata.LivingStatus
import com.trudeals.utils.extension.toBinding

class LivingStatusAdapter :
    AdvanceRecycleViewAdapter<LivingStatusAdapter.ViewHolder, LivingStatus>() {

    inner class ViewHolder(val binding: ItemLivingStatusBinding) :
        BaseHolder<LivingStatus>(binding.root) {
        override fun bind(item: LivingStatus) = with(binding) {
            textView.text = item.status
            imageViewCheckbox.isSelected = item.isSelected

            constraintLayoutCheckbox.setOnClickListener {
                imageViewCheckbox.isSelected = !imageViewCheckbox.isSelected
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