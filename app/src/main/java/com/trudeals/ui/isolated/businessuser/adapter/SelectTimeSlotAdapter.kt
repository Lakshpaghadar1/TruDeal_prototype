package com.trudeals.ui.isolated.businessuser.adapter

import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.trudeals.R
import com.trudeals.databinding.ItemSelectTimeSlotBinding
import com.trudeals.ui.base.adavancedrecyclerview.AdvanceRecycleViewAdapter
import com.trudeals.ui.base.adavancedrecyclerview.BaseHolder
import com.trudeals.ui.isolated.dummydata.SelectTimeSlots
import com.trudeals.utils.OnClick
import com.trudeals.utils.extension.isVisible
import com.trudeals.utils.extension.toBinding

class SelectTimeSlotAdapter :
    AdvanceRecycleViewAdapter<SelectTimeSlotAdapter.ViewHolder, SelectTimeSlots>() {

    private var onCLickOfView: ((item: SelectTimeSlots, subPosition: Int, onClick: OnClick) -> Unit)? =
        null

    inner class ViewHolder(val binding: ItemSelectTimeSlotBinding) :
        BaseHolder<SelectTimeSlots>(binding.root) {
        override fun bind(item: SelectTimeSlots) = with(binding) {
            textViewStartTime.text = item.startTime
            textViewEndTime.text = item.endTime
            imageViewCancelIcon.isVisible(bindingAdapterPosition != itemCount-1)
            imageViewAddIcon.isVisible(bindingAdapterPosition == itemCount-1)

            val grayTextColor = ContextCompat.getColor(context, R.color.C_7D7D7D)
            val redTextColor = ContextCompat.getColor(context, R.color.C_ED1D26)
            when (textViewStartTime.text) {
                getString(R.string.dummy_00_00_am), getString(R.string.dummy_00_00_pm) -> {
                    textViewStartTime.setTextColor(grayTextColor)
                }
                else -> {
                    textViewStartTime.setTextColor(redTextColor)
                }
            }

            when (textViewEndTime.text) {
                getString(R.string.dummy_00_00_am), getString(R.string.dummy_00_00_pm) -> {
                    textViewEndTime.setTextColor(grayTextColor)
                }
                else -> {
                    textViewEndTime.setTextColor(redTextColor)
                }
            }

            imageViewAddIcon.setOnClickListener {
                onCLickOfView?.invoke(item, bindingAdapterPosition, OnClick.ADD_SLOT)
            }

            imageViewCancelIcon.setOnClickListener {
                onCLickOfView?.invoke(item, bindingAdapterPosition, OnClick.CANCEL)
            }

            textViewStartTime.setOnClickListener {
                onCLickOfView?.invoke(item, bindingAdapterPosition, OnClick.START_TIME)
            }

            textViewEndTime.setOnClickListener {
                onCLickOfView?.invoke(item, bindingAdapterPosition, OnClick.END_TIME)
            }
        }
    }

    override fun createDataHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parent.toBinding())
    }

    fun setOnClickOfView(onCLickOfView: (item: SelectTimeSlots, subPosition: Int, onClick: OnClick) -> Unit) {
        this.onCLickOfView = onCLickOfView
    }
}