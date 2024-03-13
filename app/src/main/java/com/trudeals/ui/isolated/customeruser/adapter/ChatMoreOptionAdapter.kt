package com.trudeals.ui.isolated.customeruser.adapter

import android.view.ViewGroup
import com.trudeals.databinding.ItemChatMoreOptionBinding
import com.trudeals.ui.base.adavancedrecyclerview.AdvanceRecycleViewAdapter
import com.trudeals.ui.base.adavancedrecyclerview.BaseHolder
import com.trudeals.ui.isolated.dummydata.ChatMoreOption
import com.trudeals.utils.extension.isVisible
import com.trudeals.utils.extension.setIcon
import com.trudeals.utils.extension.toBinding

class ChatMoreOptionAdapter :
    AdvanceRecycleViewAdapter<ChatMoreOptionAdapter.ViewHolder, ChatMoreOption>() {

    inner class ViewHolder(val binding: ItemChatMoreOptionBinding) :
        BaseHolder<ChatMoreOption>(binding.root) {
        override fun bind(item: ChatMoreOption) = with(binding) {
            textViewOption.text = item.option
            textViewOption.setIcon(startIcon = item.icon)
            viewDivider.isVisible(adapterPosition != itemCount - 1)

            root.setOnClickListener {
                onClickPositionListener?.invoke(item, adapterPosition)
            }
        }
    }

    override fun createDataHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parent.toBinding())
    }
}