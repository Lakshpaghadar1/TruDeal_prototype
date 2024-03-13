package com.trudeals.ui.isolated.realestateuser.adapter

import android.annotation.SuppressLint
import android.view.ViewGroup
import com.trudeals.databinding.ItemStepBinding
import com.trudeals.ui.base.adavancedrecyclerview.AdvanceRecycleViewAdapter
import com.trudeals.ui.base.adavancedrecyclerview.BaseHolder
import com.trudeals.ui.isolated.dummydata.Steps
import com.trudeals.utils.extension.isVisible
import com.trudeals.utils.extension.toBinding

class StepsAdapter : AdvanceRecycleViewAdapter<StepsAdapter.ViewHolder, Steps>() {

    inner class ViewHolder(val binding: ItemStepBinding) : BaseHolder<Steps>(binding.root) {
        override fun bind(item: Steps) = with(binding) {
            textViewStepCount.text = item.count
            textViewStepCount.isSelected = item.isSelected
            view.isVisible(bindingAdapterPosition != itemCount-1)

            textViewStepCount.setOnClickListener {
                textViewStepCount.isSelected = textViewStepCount.isSelected
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