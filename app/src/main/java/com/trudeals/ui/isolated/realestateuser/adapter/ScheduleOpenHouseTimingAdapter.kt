package com.trudeals.ui.isolated.realestateuser.adapter

import android.annotation.SuppressLint
import android.view.ViewGroup
import com.trudeals.R
import com.trudeals.databinding.ItemOpenHouseTimingBinding
import com.trudeals.ui.base.adavancedrecyclerview.AdvanceRecycleViewAdapter
import com.trudeals.ui.base.adavancedrecyclerview.BaseHolder
import com.trudeals.ui.isolated.dummydata.ScheduleOpenHouseTiming
import com.trudeals.utils.extension.toBinding

class ScheduleOpenHouseTimingAdapter :
    AdvanceRecycleViewAdapter<ScheduleOpenHouseTimingAdapter.ViewHolder, ScheduleOpenHouseTiming>() {

    inner class ViewHolder(val binding: ItemOpenHouseTimingBinding) :
        BaseHolder<ScheduleOpenHouseTiming>(binding.root) {
        override fun bind(item: ScheduleOpenHouseTiming) = with(binding) {
            textViewTiming.text = item.timeSlot
            textViewTiming.isSelected = item.isSelected

            textViewTiming.setOnClickListener {
                item.isSelected = !item.isSelected
                onClickPositionListener?.invoke(item, bindingAdapterPosition)
            }

            /*if (item.isDealClosed) textViewTiming.setTextColor(getColor(R.color.C_8F8F8F))
            else textViewTiming.setTextColor(getColor(R.color.black))*/

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