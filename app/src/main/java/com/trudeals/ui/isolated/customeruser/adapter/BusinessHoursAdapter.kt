package com.trudeals.ui.isolated.customeruser.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.trudeals.databinding.ItemBusinessHoursBinding
import com.trudeals.ui.base.adavancedrecyclerview.AdvanceRecycleViewAdapter
import com.trudeals.ui.base.adavancedrecyclerview.BaseHolder
import com.trudeals.ui.isolated.dummydata.BusinessHours
import com.trudeals.utils.extension.isVisible
import com.trudeals.utils.extension.toBinding
import com.trudeals.utils.hideView
import com.trudeals.utils.showView

class BusinessHoursAdapter :
    AdvanceRecycleViewAdapter<BusinessHoursAdapter.ViewHolder, BusinessHours>() {

    inner class ViewHolder(val binding: ItemBusinessHoursBinding) :
        BaseHolder<BusinessHours>(binding.root) {

        private val businessTimeSlotsAdapter by lazy { BusinessHoursTimeSlotsAdapter() }

        override fun bind(item: BusinessHours) = with(binding) {
            textViewDay.text = item.day.plus(":")
            recyclerViewTimeSlots.isVisible(!item.isClosed)
            textViewIsClosed.isVisible(item.isClosed)
            setRecyclerView(item)

            if (item.isHrsDiffer) {
                showView(textViewHoursMightDiffer, textViewReasonForDifferHrs)
                textViewReasonForDifferHrs.text = item.reasonForDifferHrs
            } else {
                hideView(textViewHoursMightDiffer, textViewReasonForDifferHrs)
            }
        }

        private fun setRecyclerView(item: BusinessHours) = with(binding.recyclerViewTimeSlots) {
            item.listOfTimeSlots?.let { businessTimeSlotsAdapter.setItems(it,1) }
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            adapter = businessTimeSlotsAdapter
        }
    }

    override fun createDataHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parent.toBinding())
    }
}