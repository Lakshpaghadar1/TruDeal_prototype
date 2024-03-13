package com.trudeals.ui.isolated.customeruser.adapter

import android.view.ViewGroup
import com.trudeals.databinding.ItemDocumentBinding
import com.trudeals.ui.base.adavancedrecyclerview.AdvanceRecycleViewAdapter
import com.trudeals.ui.base.adavancedrecyclerview.BaseHolder
import com.trudeals.utils.extension.isVisible
import com.trudeals.utils.extension.toBinding

class DocumentAdapter : AdvanceRecycleViewAdapter<DocumentAdapter.ViewHolder, String>() {

    inner class ViewHolder(val binding: ItemDocumentBinding) : BaseHolder<String>(binding.root) {
        override fun bind(item: String) = with(binding) {
            textViewOption.text = item
            viewDivider.isVisible(adapterPosition != itemCount - 1)

            textViewDownload.setOnClickListener {
                onClickPositionListener?.invoke(item, adapterPosition)
            }
        }
    }

    override fun createDataHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parent.toBinding())
    }
}