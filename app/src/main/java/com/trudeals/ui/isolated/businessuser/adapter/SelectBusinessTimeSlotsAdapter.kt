package com.trudeals.ui.isolated.businessuser.adapter

import android.annotation.SuppressLint
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.trudeals.databinding.ItemSelectBusinessAvailabilityTimeSlotsBinding
import com.trudeals.ui.base.adavancedrecyclerview.AdvanceRecycleViewAdapter
import com.trudeals.ui.base.adavancedrecyclerview.BaseHolder
import com.trudeals.ui.isolated.dummydata.SelectBusinessTimeSlots
import com.trudeals.ui.isolated.dummydata.SelectTimeSlots
import com.trudeals.utils.DataUtils
import com.trudeals.utils.OnClick
import com.trudeals.utils.extension.isVisible
import com.trudeals.utils.extension.toBinding

class SelectBusinessTimeSlotsAdapter :
    AdvanceRecycleViewAdapter<SelectBusinessTimeSlotsAdapter.ViewHolder, SelectBusinessTimeSlots>() {

    private var onCLickOfView: ((item: SelectTimeSlots, subPosition: Int, position: Int, onClick: OnClick) -> Unit)? =
        null

    inner class ViewHolder(val binding: ItemSelectBusinessAvailabilityTimeSlotsBinding) :
        BaseHolder<SelectBusinessTimeSlots>(binding.root) {

        private val selectTimeSlotAdapter by lazy { SelectTimeSlotAdapter() }
        override fun bind(item: SelectBusinessTimeSlots) = with(binding) {
            switchOnOff.isChecked = item.isChecked
            item.isClosed = !item.isChecked
            textViewWeekDay.text = item.weekDay
            setRecyclerView(item)
            viewDivider.isVisible(bindingAdapterPosition != itemCount - 1)

            textViewClosed.isVisible(item.isClosed)
            recyclerViewTimeSlots.isVisible(!item.isClosed)

            switchOnOff.setOnClickListener {
                item.isClosed = !item.isChecked
                item.isChecked = item.isChecked
                switchOnOff.isChecked = !switchOnOff.isChecked
                changeSelection(bindingAdapterPosition, false)
            }

            selectTimeSlotAdapter.setOnClickOfView { item, subPosition, onClick ->
                onCLickOfView?.invoke(item, subPosition, bindingAdapterPosition, onClick)
            }
        }

        private fun setRecyclerView(item: SelectBusinessTimeSlots) =
            with(binding.recyclerViewTimeSlots) {
                selectTimeSlotAdapter.setItems(item.listOfTimeSlots, 1)
                layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
                adapter = selectTimeSlotAdapter
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
                    items!![position].isChecked = true
                } else {
                    items!![position].isChecked =
                        !items!![position].isChecked
                }
            } else {
                if (isSingleSelected) {
                    items!![i].isChecked = false
                }
            }
        }
        if (isSingleSelected) {
            notifyDataSetChanged()
        } else {
            notifyItemChanged(position)
        }
    }

    fun setOnClickOfView(onCLickOfView: (item: SelectTimeSlots, subPosition: Int, position: Int, onClick: OnClick) -> Unit) {
        this.onCLickOfView = onCLickOfView
    }

    fun addChildItem(parentItemPosition: Int) {
        items?.get(parentItemPosition)?.listOfTimeSlots?.add(DataUtils.setDefaultTimeSlots())
        notifyItemChanged(parentItemPosition)
    }

    fun removeChildItem(parentItemPosition: Int, childItemPosition: Int) {
        items?.get(parentItemPosition)?.listOfTimeSlots?.removeAt(childItemPosition)
        notifyItemChanged(parentItemPosition)
    }

    fun updateChildIStartItem(parentItemPosition: Int, childItemPosition: Int, startTime: String) {
        getItem(parentItemPosition).listOfTimeSlots[childItemPosition].startTime = startTime
        notifyItemChanged(parentItemPosition)
    }

    fun updateChildIEndItem(parentItemPosition: Int, childItemPosition: Int, endTime: String) {
        getItem(parentItemPosition).listOfTimeSlots[childItemPosition].endTime = endTime
        notifyItemChanged(parentItemPosition)
    }
}