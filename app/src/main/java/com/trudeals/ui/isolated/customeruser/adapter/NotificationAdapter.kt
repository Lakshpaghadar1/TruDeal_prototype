package com.trudeals.ui.isolated.customeruser.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.trudeals.databinding.ItemNotificationBinding
import com.trudeals.ui.base.adavancedrecyclerview.AdvanceRecycleViewAdapter
import com.trudeals.ui.base.adavancedrecyclerview.BaseHolder
import com.trudeals.ui.isolated.dummydata.Notification
import com.trudeals.utils.extension.toBinding

class NotificationAdapter :
    AdvanceRecycleViewAdapter<NotificationAdapter.ViewHolder, Notification>() {

    inner class ViewHolder(val binding: ItemNotificationBinding) :
        BaseHolder<Notification>(binding.root) {

        private val subNotificationSubAdapter by lazy {
            NotificationSubAdapter()
        }
        override fun bind(item: Notification): Unit = with(binding) {
            textViewDate.text = item.date

            subRecyclerViewNotification.apply {
                subNotificationSubAdapter.setItems(item.subData, 1)
                layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
                adapter = subNotificationSubAdapter
            }
        }
    }

    override fun createDataHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parent.toBinding())
    }
}