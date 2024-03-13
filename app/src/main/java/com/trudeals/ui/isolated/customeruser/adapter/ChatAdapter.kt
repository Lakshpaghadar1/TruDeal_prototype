package com.trudeals.ui.isolated.customeruser.adapter

import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.trudeals.databinding.ItemChatBinding
import com.trudeals.ui.base.adavancedrecyclerview.AdvanceRecycleViewAdapter
import com.trudeals.ui.base.adavancedrecyclerview.BaseHolder
import com.trudeals.ui.isolated.dummydata.ChatData
import com.trudeals.utils.OnClick
import com.trudeals.utils.extension.isVisible
import com.trudeals.utils.extension.toBinding

class ChatAdapter : AdvanceRecycleViewAdapter<ChatAdapter.ViewHolder, ChatData>() {

    private var onClickOfView: ((item: ChatData, position: Int, onClick: OnClick) -> Unit)? =
        null

    inner class ViewHolder(val binding: ItemChatBinding) : BaseHolder<ChatData>(binding.root) {
        override fun bind(item: ChatData) = with(binding) {
            shapeableImageViewProfile.setImageResource(item.profileImage)
            textViewUserName.text = item.userName
            textViewTime.text = item.time
            textViewLastMsg.text = item.lastMsg
            textViewBadge.isVisible(item.unReadMsgCount != null)
            textViewBadge.text = item.unReadMsgCount

            textViewLastMsg.setTextColor(ContextCompat.getColor(context, item.lastMsgTextColor))

            textViewBlock.setOnClickListener {
                onClickOfView?.invoke(item, bindingAdapterPosition, OnClick.BLOCK)
            }

            textViewDelete.setOnClickListener {
                onClickOfView?.invoke(item, bindingAdapterPosition, OnClick.DELETE)
            }

            constraintLayoutChat.setOnClickListener {
                onClickPositionListener?.invoke(item, bindingAdapterPosition)
            }
        }
    }

    override fun createDataHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parent.toBinding())
    }

    fun setClickOnView(onClickOfView: ((item: ChatData, position: Int, onClick: OnClick) -> Unit)) {
        this.onClickOfView = onClickOfView
    }
}