package com.trudeals.ui.isolated.optionsbottomsheet

import android.annotation.SuppressLint
import android.view.ViewGroup
import com.trudeals.databinding.OptionsBottomSheetRowBinding
import com.trudeals.ui.base.adavancedrecyclerview.AdvanceRecycleViewAdapter
import com.trudeals.ui.base.adavancedrecyclerview.BaseHolder
import com.trudeals.utils.extension.toBinding

class OptionsAdapter<E : Option> : AdvanceRecycleViewAdapter<OptionsAdapter<E>.ViewHolder, E>() {

    private var allowSelection: Boolean = true

    inner class ViewHolder(private val binding: OptionsBottomSheetRowBinding) :
        BaseHolder<E>(binding.root) {

        override fun bind(item: E) = with(binding) {
            textViewOption.text = item.option
            imageViewCheckbox.isSelected = item.isSelected

            constraintLayoutCheckbox.setOnClickListener {
                imageViewCheckbox.isSelected = !imageViewCheckbox.isSelected
                onClickPositionListener?.invoke(item, adapterPosition)
            }
        }
    }

    fun allowSelection(allowSelection: Boolean) {
        this.allowSelection = allowSelection
    }

    override fun createDataHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        lastItemSelectedPos = getItemIndex(items!!.find { it.isSelected })

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