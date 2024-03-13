package com.trudeals.ui.isolated.customeruser.adapter

import android.view.ViewGroup
import com.trudeals.databinding.SubItemNotificationBinding
import com.trudeals.ui.base.adavancedrecyclerview.AdvanceRecycleViewAdapter
import com.trudeals.ui.base.adavancedrecyclerview.BaseHolder
import com.trudeals.ui.isolated.dummydata.SubNotification
import com.trudeals.utils.extension.toBinding

class NotificationSubAdapter :
    AdvanceRecycleViewAdapter<NotificationSubAdapter.ViewHolder, SubNotification>() {

    inner class ViewHolder(val binding: SubItemNotificationBinding) :
        BaseHolder<SubNotification>(binding.root) {
        override fun bind(item: SubNotification) = with(binding) {
            textViewTime.text = item.time
            textViewTitle.text = item.title
            textViewDescription.text = item.description
        }
    }

    override fun createDataHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parent.toBinding())
    }
}